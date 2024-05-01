package com;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//로그인 되었을 때는 다른 메인 페이지

public class Main extends JFrame {

	public Main() {
		setTitle("가슴속에 복권 한장");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // JFrame닫을 때 JVM 종료
		setSize(1000, 900); // 프레임 크기 설정	
		setLayout(new BorderLayout()); //배치 관리자 설정	
		Categoty cate = new Categoty();	
		JPanel panel= new JPanel();
		Login login=new Login();
		panel.add(login);
		add("North", cate.panel);
		
		JButton loginbut =cate.login;
		
		//로그인 버튼 클릭
		loginbut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("로그인 버튼 클릭");
				add("Center", panel);
				revalidate(); // 변경된 내용을 다시 그리도록 갱신
			}
		});
		
		setVisible(true);
	}

	public static void main(String[] args) {
		Main main = new Main();
	}


}
