package com;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Category extends JPanel {
    int userNo;
    JButton lottery;
    JButton myPage;    
    JButton chat;
    JButton login;
    JPanel mainPanel = new JPanel();
    
    public Category() {

        lottery = new JButton("번호 생성"); 
        lottery.setPreferredSize(new Dimension(230, 60));
        lottery.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
        
        chat = new JButton("채팅");
        chat.setPreferredSize(new Dimension(230, 60));
        chat.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
        
        myPage = new JButton("내 정보");
        myPage.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
        myPage.setPreferredSize(new Dimension(230, 60));
        
        //////////////////////////////////ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ
        if(userNo>0) {
            login = new JButton("로그아웃");
            login.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
            login.setPreferredSize(new Dimension(230, 60));
        }else {
            login = new JButton("로그인");
            login.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
            login.setPreferredSize(new Dimension(230, 60));
        }
        
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(lottery);
        mainPanel.add(myPage);
        mainPanel.add(chat);
        mainPanel.add(login);
    }
    public int getUserNo() {
        return userNo;
    }
    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }
}
