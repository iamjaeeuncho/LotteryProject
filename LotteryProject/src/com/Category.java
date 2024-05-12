package com;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Category extends JPanel {
    int userNo = 0;
    JButton lottery;
    JButton chat;
    JButton loginout;
    JPanel mainPanel = new JPanel();

    public Category(int userNo) {
        this.userNo = userNo;

        lottery = new JButton("번호 생성");
        lottery.setPreferredSize(new Dimension(230, 60));
        lottery.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정

        chat = new JButton("채팅");
        chat.setPreferredSize(new Dimension(230, 60));
        chat.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정

        JButton myPage = new JButton("내 정보");
        myPage.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
        myPage.setPreferredSize(new Dimension(230, 60));
        System.out.println("카테" + userNo);

        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(lottery);
        mainPanel.add(myPage);
        mainPanel.add(chat);

        if (userNo > 0) {
            // 로그아웃 버튼 생성
            loginout = new JButton("로그아웃");
            loginout.setFont(new Font("SansSerif", Font.BOLD, 13));
            loginout.setPreferredSize(new Dimension(230, 60));
            System.out.println("로그아웃" + userNo);
        } else {
            loginout = new JButton("로그인");
            loginout.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
            loginout.setPreferredSize(new Dimension(230, 60));
            System.out.println("카테233" + userNo);
        }

        // 로그인/로그아웃 버튼 패널에 추가
        mainPanel.add(loginout);

        // 패널을 새로고침하여 변경된 내용을 보여줌
        revalidate();
        repaint();
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }
    
    private void removeCurrentPanel() {
        if (loginout != null) {
            remove(loginout);
        }
    }
}

