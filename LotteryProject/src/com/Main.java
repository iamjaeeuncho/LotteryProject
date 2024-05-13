package com;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main extends JFrame {
    private JPanel currentPanel;
    private Category category;
    private Chat chat;
    private MyPage myPage;
    private Main main;
    private int userNo;

    public Main() {
        this.main = this; // main 멤버 변수에 현재 인스턴스 할당
        initializeUI();
    }

    private void initializeUI() {
        category = new Category();
        setTitle("가슴속에 복권 한장");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add("North", category.getMainPanel());

        JButton loginoutBtn = category.getLoginout(); 
        JButton chatBtn = category.getChat();
        JButton lotteryBtn = category.getLottery();
        JButton myPageBtn = category.getMyPage();

        Listener listener = new Listener();

        loginoutBtn.addActionListener(listener);
        chatBtn.addActionListener(listener);
        lotteryBtn.addActionListener(listener);
        myPageBtn.addActionListener(listener);

        setLocationRelativeTo(null);
        
        // 맨 처음 화면을 복권 패널로 설정
        Lottery_test lotteryPanel = new Lottery_test(userNo);
        add("Center", lotteryPanel);
        currentPanel = lotteryPanel;
        
        setVisible(true);
    }
    
    public void logininit() {
        removeCurrentPanel(); // 기존 패널 제거
        currentPanel = new Lottery_test(userNo); // 새로운 Lottery 패널 생성
        add("Center", currentPanel); // 새로운 패널 추가
        category.updateLoginButton(); // 로그인 성공 후에 카테고리의 로그인 버튼을 업데이트
        revalidate(); // 변경된 패널 다시 그리기
        repaint();
    }
    
    public void logout() {
        setUserNo(0);
        removeCurrentPanel();
        Login loginPanel = new Login(Main.this);
        add("Center", loginPanel);
        currentPanel = loginPanel;
        revalidate();
        repaint();
    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            
            if (btn == category.getLottery()) {
                removeCurrentPanel();
                Lottery_test lotteryPanel = new Lottery_test(userNo);
                add("Center", lotteryPanel);
                currentPanel = lotteryPanel;
            } else if (btn == category.getLoginout()) {
                // 로그인 버튼 클릭 시
	           if(category.getLoginout().getText().equals("로그인")) {
	                removeCurrentPanel();
	                Login loginPanel = new Login(Main.this); // 로그인 패널 생성
	                add("Center", loginPanel); // 로그인 패널을 메인 프레임의 중앙에 추가
	                currentPanel = loginPanel; // currentPanel을 로그인 패널로 설정
	           } else if(category.getLoginout().getText().equals("로그아웃")) {
	                int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "Confirmation",
	                        JOptionPane.YES_NO_OPTION);
	               if (result == JOptionPane.YES_OPTION) {
	                   main.setUserNo(0);
	                    removeCurrentPanel();
	                    Login loginPanel = new Login(Main.this); // 로그인 패널 생성
	                    add("Center", loginPanel); // 로그인 패널을 메인 프레임의 중앙에 추가
	                    currentPanel = loginPanel; // currentPanel을 로그인 패널로 설정
	               }
	           }
            } else if (btn == category.getMyPage()) {
                removeCurrentPanel();
                myPage = new MyPage(userNo);
                add("Center", myPage);
                currentPanel = myPage;
            } else if (btn == category.getChat()) {
                removeCurrentPanel();
                chat = new Chat(userNo);
                add("Center", chat);
                currentPanel = chat;
            }
            // 로그인 상태에 따라 로그인/로그아웃 버튼 업데이트
            category.updateLoginButton();
            revalidate();
            repaint();

        }
    }

    private void removeCurrentPanel() {
        if (currentPanel != null) {
            // 패널에 추가된 모든 컴포넌트 제거
            Component[] components = currentPanel.getComponents();
            for (Component component : components) {
                currentPanel.remove(component);
            }
            // 메인 프레임에서 현재 패널 제거
            remove(currentPanel);
        }
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
        category.setUserNo(userNo);
        category.updateLoginButton();
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}