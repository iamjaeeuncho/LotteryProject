package com;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("가슴속에 복권 한장");
        setSize(1400, 600);
        setLayout(new BorderLayout());
        add("North", category.panel);

        JButton loginBtn = category.login;
        JButton chatBtn = category.chat;
        JButton lotteryBtn = category.lottery;
        JButton myPageBtn = category.myPage;

        Listener listener = new Listener();

        loginBtn.addActionListener(listener);
        chatBtn.addActionListener(listener);
        lotteryBtn.addActionListener(listener);
        myPageBtn.addActionListener(listener);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            
            if (btn == category.lottery) {
                removeCurrentPanel();
                Lottery lotteryPanel = new Lottery();
                add("Center", lotteryPanel);
                currentPanel = lotteryPanel;
            } else if (btn == category.myPage) {
                removeCurrentPanel();
                MyPage myPagePanel = new MyPage();
                add("Center", myPagePanel);
                currentPanel = myPagePanel;
            } else if (btn == category.login) {
                removeCurrentPanel();
                Login loginPanel = new Login(Main.this); // 로그인 패널 생성
                add("Center", loginPanel); // 로그인 패널을 메인 프레임의 중앙에 추가
                currentPanel = loginPanel; // currentPanel을 로그인 패널로 설정
            } else if (btn == category.chat) {
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