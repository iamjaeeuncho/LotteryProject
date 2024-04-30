package com;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainCategoty extends JFrame {
	
	JPanel panel = new JPanel();
	
	public MainCategoty() {

	JLabel label = new JLabel("이미지 넣기");
	label.setPreferredSize(new Dimension(448, 42));
	JButton main = new JButton("번호 생성");
	main.setPreferredSize(new Dimension(113, 42));
	JButton chat = new JButton("채팅");
	chat.setPreferredSize(new Dimension(113, 42));
	JButton myPage = new JButton("내 정보");
	myPage.setPreferredSize(new Dimension(113, 42));
	
	//로그인 검사
	JButton logout = new JButton("로그아웃");
	logout.setPreferredSize(new Dimension(113, 42));

	// BorderLayout은 하나의 영역에 하나의 컴포넌트만 올 수 있다.
	// 패널에 적용해서 패널을 넣어야 됨

	panel.setLayout(new FlowLayout());
	panel.add(label);
	panel.add(main);
	panel.add(myPage);
	panel.add(chat);
	panel.add(logout);
	System.out.println("duddu");

	setVisible(true);
	}
}
