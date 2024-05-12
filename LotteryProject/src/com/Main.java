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
    }

    public Main() {
        initializeUI();
    }

    private void initializeUI() {
        category = new Category(userNo);
        setTitle("가슴속에 복권 한장");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLayout(new BorderLayout());
        add("North", category.mainPanel);

        JButton loginBtn = category.loginout;
        JButton chatBtn = category.chat;
        JButton lotteryBtn = category.lottery;

        Listener listener = new Listener();

        loginBtn.addActionListener(listener);
        chatBtn.addActionListener(listener);
        lotteryBtn.addActionListener(listener);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void logininit() {
        removeCurrentPanel(); // 기존 패널 제거
        currentPanel = new Lottery(); // 새로운 Lottery 패널 생성
        add("Center", currentPanel); // 새로운 패널 추가
//        category.setUserNo(userNo);
        System.out.println("메인메서 유저"+userNo);
        new Category(userNo);
        revalidate(); // 변경된 패널 다시 그리기
        repaint();
        System.out.println("메인메서 유저2"+userNo);
    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton but = (JButton) e.getSource();
            
            if (but == category.lottery) {
                removeCurrentPanel();
                Lottery lotteryPanel = new Lottery();
                add("Center", lotteryPanel);
                currentPanel = lotteryPanel;
                    
            } else if (but == category.loginout) {
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
