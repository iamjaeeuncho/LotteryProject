package com;

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
    
    public Client(int userNo,String userName,int chatNo) {
        this.userNo = userNo;
        this.userName=userName;
        this.chatNo = chatNo;
        initializeUI();
    }

    public Client() {
        initializeUI();
    }

    public void initializeUI() {
        setBounds(100, 100, 678, 535);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // JScrollPane을 생성하여 JTextArea를 감싸기
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(54, 39, 579, 382);
        contentPane.add(scrollPane);
        
        // JTextArea를 JScrollPane에 추가
        textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 20)); // 글꼴 크기 설정
        scrollPane.setViewportView(textArea);
        
        List<MessageVO> measList = cdao.mesgList(chatNo);
        for (int i = 0; i < measList.size(); i++) {
            textArea.append(measList.get(i).userName + ":" + measList.get(i).content + "\n");
        }
        
        JButton btnNewButton = new JButton("전송");
        btnNewButton.setBounds(538, 431, 95, 39);
        contentPane.add(btnNewButton);
        textField = new JTextField();
        textField.setBounds(54, 431, 480, 39);
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
    }

    private void setupNetworking() {
        try {
            socket = new Socket("192.168.230.39", 1000);
            OutputStream os = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));
            prinWrite = new PrintWriter(bw, true);
            System.out.println("소켓"+userName);
            prinWrite.println(userName);

            InputStream is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            Thread receiveThread = new Thread(new Runnable() {
                public void run() {
                    try {
                        String message;
                        while ((message = br.readLine()) != null) {
                            final String msg = message;
                            EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                    textArea.append(msg + "\n");
                                    textArea.setCaretPosition(textArea.getDocument().getLength());
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            receiveThread.start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "네트워크 연결 실패: " + e.getMessage(), "연결 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        if (!textField.getText().isEmpty()) {
            cdao.saveChat(userNo, textField.getText(), chatNo);
            prinWrite.println(userName + ":" + textField.getText());
            textField.setText("");
        }
    }
}
