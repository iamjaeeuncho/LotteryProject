package com;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Categoty extends JFrame {
    JButton login;
	
	
	JPanel panel = new JPanel();
	
	public Categoty() {

	JLabel label = new JLabel("이미지 넣기");
	label.setPreferredSize(new Dimension(448, 42));
	JButton main = new JButton("번호 생성");
	main.setPreferredSize(new Dimension(113, 42));
	JButton chat = new JButton("채팅");
	chat.setPreferredSize(new Dimension(113, 42));
	JButton myPage = new JButton("내 정보");
	myPage.setPreferredSize(new Dimension(113, 42));
	
	//로그인 검사
    login = new JButton("로그인");
	login.setPreferredSize(new Dimension(113, 42));

	// BorderLayout은 하나의 영역에 하나의 컴포넌트만 올 수 있다.
	// 패널에 적용해서 패널을 넣어야 됨

	panel.setLayout(new FlowLayout());
	panel.add(label);
	panel.add(main);
	panel.add(myPage);
	panel.add(chat);
	panel.add(login);

	setVisible(true);
	
	}
}
