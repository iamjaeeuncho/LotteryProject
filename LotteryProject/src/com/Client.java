package com;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.dao.ChatDAO;
import com.dto.MessageVO;

public class Client extends JFrame {
    private Socket socket;
    private BufferedReader br;
    private BufferedWriter bw;
    private PrintWriter prinWrite;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JTextArea textArea;
    private ChatDAO cdao = new ChatDAO();

    private String userName;
    private int userNo;
    private int chatNo;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Client frame = new Client();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Client(int userNo, String userName, int chatNo) {
        this.userNo = userNo;
        this.userName = userName;
        this.chatNo = chatNo;
        initializeUI();
        loadMessages();
    }

    public Client() {
    }

    Thread receiveThread = new Thread(new Runnable() {
        public void run() {
            try {
                String message;
                while ((message = br.readLine()) != null) {
                    final String msg = message;
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            // 일반 메시지일 경우
                            textArea.append(msg + "\n");
                            textArea.setCaretPosition(textArea.getDocument().getLength());
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 클라이언트 종료 시 소켓 연결 및 스트림 닫기
                try {
                    if (socket != null) {
                        socket.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    });

    public void initializeUI() {
        setBounds(100, 100, 800, 600); // Increase the size of the main frame
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Increase the border size
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // JScrollPane을 생성하여 JTextArea를 감싸기
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 50, 600, 400); // Adjust the bounds of the scroll pane
        contentPane.add(scrollPane);

        // JTextArea를 JScrollPane에 추가
        textArea = new JTextArea();
        textArea.setEditable(false); // 수정 불가능하도록 설정
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 20)); // 글꼴 크기 설정
        scrollPane.setViewportView(textArea);

        JButton btnNewButton = new JButton("전송");
        btnNewButton.setBounds(650, 480, 100, 39); // Adjust the position of the button
        contentPane.add(btnNewButton);

        textField = new JTextField();
        textField.setBounds(100, 480, 540, 39); // Adjust the width of the text field
        textField.setColumns(10);
        contentPane.add(textField);

        setVisible(true);

        setupNetworking();

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // 접속 중인 사용자 라벨을 스크롤바 바깥 오른쪽 상단에 위치시키기
        JButton userLabel = new JButton("접속 중인 사용자");
        Dimension labelSize = userLabel.getPreferredSize();
        userLabel.setBounds(10, 10, labelSize.width, labelSize.height); // Adjust the position of the label
        contentPane.add(userLabel);

        userLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame newUserWindow = new JFrame("접속 중인 유저");
                newUserWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newUserWindow.setBounds(200, 200, 400, 300);
                JPanel newUserPanel = new JPanel();
                newUserWindow.setContentPane(newUserPanel);

                // 서버로 'online' 메시지 보내기
                prinWrite.println("online");

                // 서버로부터 온 응답 받기
                try {
                    String response;
                    while ((response = br.readLine()) != null) {
                        if (response.startsWith("현재 접속 중인 사용자 목록:")) {
                            // 유저 목록을 JTextArea에 추가
                            JTextArea userListArea = new JTextArea();
                            userListArea.setEditable(false); // 수정 불가능하도록 설정
                            userListArea.setFont(new Font("SansSerif", Font.PLAIN, 20)); // 글꼴 크기 설정
                            String[] userList = {};
                            if (response.startsWith("현재 접속 중인 사용자 목록:") && response.length() > 26) {
                                userList = response.substring(26).split("\n");
                            }
                            for (String user : userList) {
                                userListArea.append(user + "\n");
                            }
                            newUserPanel.add(new JScrollPane(userListArea)); // JTextArea를 JScrollPane에 추가
                            break; // 유저 목록을 추가한 후 반복문 종료
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                newUserWindow.setVisible(true);
            }
        });
    }

    private void setupNetworking() {
        try {
            socket = new Socket("192.168.230.38", 9999);
            OutputStream os = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));
            prinWrite = new PrintWriter(bw, true);
            prinWrite.println(userName);

            InputStream is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            receiveThread.start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "네트워크 연결 실패: " + e.getMessage(), "연결 오류",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadMessages() {
        textArea.setText(""); // 기존 메시지를 모두 지움
        List<MessageVO> mesgList = cdao.mesgList(chatNo);
        for (int i = 0; i < mesgList.size(); i++) {
            textArea.append(mesgList.get(i).userName + ": " + mesgList.get(i).content + "\n");
        }
    }

    private void sendMessage() {
        if (!textField.getText().isEmpty()) {
            String message = textField.getText();
            // 그 외의 경우에는 일반 메시지를 전송한다.
            cdao.saveChat(userNo, message, chatNo);
            prinWrite.println(userName + ": " + message);
            textField.setText("");
        }
    }
}
