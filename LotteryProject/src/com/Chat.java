package com;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Chat extends JPanel {
    
    public Chat() {
        
        setLayout( null ); 
        
        JLabel useridLabel = new JLabel("오픈 채팅");
        useridLabel.setBounds(10,40,150,23); //위치,너비 높이
        useridLabel.setFont(new Font("Sans-serif",Font.BOLD,21));
        
        JButton mChat=new JButton("채팅방 생성");
        mChat.setBounds(750,40,100,40); //x,y,너비 높이
        mChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String answer = JOptionPane.showInputDialog("채팅방 제목을 입력하세요");
				System.out.println(answer);
				
			}
		});
        
        JButton chatRoom=new JButton("방1ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
        chatRoom.setBounds(150,90,700,70); //위치,너비 높이
        
        add(chatRoom);
        add(mChat);
        add(useridLabel);
    }
    
}
