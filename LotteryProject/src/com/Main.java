package com;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame {
    private JPanel currentPanel;
    private Category category;
    private Chat chat;
    private int userNo;

    public Main(int userNo) {
        this.userNo = userNo;
    }

    public Main() {
        initializeUI();
    }

    private void initializeUI() {
<<<<<<< HEAD
        category = new Category(userNo);
        setTitle("가슴속에 복권 한장");
=======
        category = new Category();
>>>>>>> branch 'main' of https://github.com/iamjaeeuncho/LotteryProject.git
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("가슴속에 복권 한장");
        setSize(1100, 650);
        setLayout(new BorderLayout());
        add("North", category.mainPanel);

        JButton loginBtn = category.loginout;
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
    
    public void logininit() {
        removeCurrentPanel(); // 기존 패널 제거
        currentPanel = new Lottery(); // 새로운 Lottery 패널 생성
        add("Center", currentPanel); // 새로운 패널 추가
        System.out.println("메인메서 유저"+userNo);
        category = new Category(userNo); // 새로운 Category 객체 생성 및 userNo 전달
        add("North", category.mainPanel); // 업데이트된 카테고리 패널 추가
        revalidate(); // 변경된 패널 다시 그리기
        repaint();
        System.out.println("메인메서 유저2"+userNo);
    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            
            if (btn == category.lottery) {
                removeCurrentPanel();
<<<<<<< HEAD
                Lottery lotteryPanel = new Lottery();
                add("Center", lotteryPanel);
                currentPanel = lotteryPanel;
                    
            } else if (but == category.loginout) {
=======
                Lottery lotteryPanel = new Lottery(userNo);
                add("Center", lotteryPanel);
                currentPanel = lotteryPanel;
            } else if (btn == category.myPage) {
                removeCurrentPanel();
                MyPage myPagePanel = new MyPage(userNo);
                add("Center", myPagePanel);
                currentPanel = myPagePanel;
            } else if (btn == category.login) {
>>>>>>> branch 'main' of https://github.com/iamjaeeuncho/LotteryProject.git
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

    void removeCurrentPanel() {
        if (currentPanel != null) {
        	System.out.println("여기 호출");
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
