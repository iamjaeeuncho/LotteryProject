package com;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private Thread receiveThread;

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
        initializeUI();
    }

    private void initializeUI() {
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(100, 50, 600, 400);
        contentPane.add(scrollPane);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 20));
        scrollPane.setViewportView(textArea);

        JButton btnNewButton = new JButton("전송");
        btnNewButton.setBounds(650, 480, 100, 39);
        contentPane.add(btnNewButton);

        textField = new JTextField();
        textField.setBounds(100, 480, 540, 39);
        textField.setColumns(10);
        contentPane.add(textField);

        setVisible(true);

        setupNetworking();

        btnNewButton.addActionListener(new ActionListener() {
            @Override
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

        JButton userLabel = new JButton("접속 중인 사용자");
        Dimension labelSize = userLabel.getPreferredSize();
        userLabel.setBounds(570, 10, labelSize.width, labelSize.height);
        contentPane.add(userLabel);

        userLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prinWrite.println("online_request");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                prinWrite.println("exit");
                System.out.println("채팅방이 닫혔습니다.");
            }
        });
    }

    private void setupNetworking() {
        try {
            if (socket == null || socket.isClosed()) {
                socket = new Socket("192.168.230.38", 9999);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                prinWrite = new PrintWriter(bw, true);
                prinWrite.println(userName);
                prinWrite.println(chatNo);
                startReceiving(); // 메시지를 받는 쓰레드 시작
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "네트워크 연결 실패: " + e.getMessage(), "연결 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void startReceiving() {
        receiveThread = new Thread(new Runnable() {
            public void run() {
                try {
                    String message;
                    while (!socket.isClosed() && (message = br.readLine()) != null) {
                        final String msg = message;
                        EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                textArea.append(msg + "\n");
                            }
                        });
                    }
                } catch (IOException e) {
                    if (!socket.isClosed()) {
                        e.printStackTrace();
                    }
                } 
            }
        });
        receiveThread.start();
    }

    private void loadMessages() {
        textArea.setText("");
        List<MessageVO> mesgList = cdao.mesgList(chatNo);
        for (MessageVO messageVO : mesgList) {
            textArea.append(messageVO.getUserName() + ": " + messageVO.getContent() + "\n");
        }
    }

    private void sendMessage() {
        if (!textField.getText().isEmpty()) {
            String message = textField.getText();
            if (message.equals("online")) {
                prinWrite.println("online_request");
            } else {
                cdao.saveChat(userNo, message, chatNo);
                prinWrite.println(userName + ": " + message);
            }
            textField.setText("");
        }
    }
}