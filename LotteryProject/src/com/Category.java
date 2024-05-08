package com;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Category extends JFrame {
	JButton login;
	int userNo;
	JButton chat;
	JPanel panel = new JPanel();
	
	public Category() {

	JLabel label = new JLabel("이미지 넣기");
	label.setPreferredSize(new Dimension(448, 42));
	JButton main = new JButton("번호 생성");
	main.setPreferredSize(new Dimension(113, 42));
	chat = new JButton("채팅");
	chat.setPreferredSize(new Dimension(113, 42));
	JButton myPage = new JButton("내 정보");
	myPage.setPreferredSize(new Dimension(113, 42));
    login = new JButton("로그인");
	login.setPreferredSize(new Dimension(113, 42));

	panel.setLayout(new FlowLayout());
	panel.add(label);
	panel.add(main);
	panel.add(myPage);
	panel.add(chat);
	panel.add(login);

	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
}
