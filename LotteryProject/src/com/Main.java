package com;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JFrame {
	private Lottery lottery;
    private JPanel currentPanel;
    private Category category;
    private Chat chat;
    private int userNo;

    public Main(int userNo) {
        this.userNo = userNo;
        initializeUI();
    }

    public Main() {
        initializeUI();
    }

    private void initializeUI() {
        category = new Category();
        setTitle("가슴속에 복권 한장");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLayout(new BorderLayout());
        add("North", category.panel);

        JButton loginBtn = category.login;
        JButton chatBtn = category.chat;

        Lsistener listener = new Lsistener();

        loginBtn.addActionListener(listener);
        chatBtn.addActionListener(listener);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class Lsistener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton but = (JButton) e.getSource();
            if (but == category.lottery) {
            	System.out.println("aaaaa");
                removeCurrentPanel();
                Lottery lotteryPanel = new Lottery(Main.this);
                add("Center", lotteryPanel);
                currentPanel = lotteryPanel;
            } else if (but == category.login) {
                removeCurrentPanel();
                Login loginPanel = new Login(Main.this); // 로그인 패널 생성
                add("Center", loginPanel); // 로그인 패널을 메인 프레임의 중앙에 추가
                currentPanel = loginPanel; // currentPanel을 로그인 패널로 설정
            } else if (but == category.chat) {
                removeCurrentPanel();
                chat = new Chat(userNo);
                add("Center", chat);
                currentPanel = chat;
            }
            revalidate();
            repaint();
        }
    }

    private void removeCurrentPanel() {
        if (currentPanel != null) {
            remove(currentPanel);
        }
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}