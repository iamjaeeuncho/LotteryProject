package com;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main extends JFrame {

	public Main() {
		setTitle("가슴속에 복권 한장");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame닫을 때 JVM 종료
		setSize(1000, 900); // 프레임 크기 설정

		JLabel label = new JLabel("이미지 넣기");
		label.setPreferredSize(new Dimension(400, 40));
		JButton main = new JButton("메인 페이지");
		main.setPreferredSize(new Dimension(110, 40));
		JButton Purchase = new JButton("복권 구매");
		Purchase.setPreferredSize(new Dimension(110, 40));
		JButton myPage = new JButton("내 정보");
		myPage.setPreferredSize(new Dimension(110, 40));
		JButton chat = new JButton("채팅");
		chat.setPreferredSize(new Dimension(110, 40));
		JButton logout = new JButton("로그아웃");
		logout.setPreferredSize(new Dimension(110, 40));

		setLayout(new BorderLayout()); // 배치 관리자 설정

		// BorderLayout은 하나의 영역에 하나의 컴포넌트만 올 수 있다.
		// 패널에 적용해서 패널을 넣어야 됨
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(label);
		panel.add(main);
		panel.add(Purchase);
		panel.add(myPage);
		panel.add(chat);
		panel.add(logout);

		JPanel panel2 = new JPanel();
		panel2.add(new Login());

		add("Center", panel2);

		add("North", panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		Main layout = new Main();
	}

}
