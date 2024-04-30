package com;

//import com.dao.UserDAO;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dao.UserDAO;

public class Login extends JPanel implements ActionListener {
	private JTextField useridField;
	private JPasswordField passwordField;
	private JButton SignInButton;
	private JButton SignUpButton;
	private JTextField name;
	private JTextField email;
	private JTextField passwd;
	private JTextField userId;
	
	public Login() {
		// JPanel initialization
		setLayout(new GridLayout(3, 2)); // Set layout for the JPanel

		JLabel useridLabel = new JLabel("아이디:");
		useridField = new JTextField();
		add(useridLabel);
		add(useridField);

		JLabel passwordLabel = new JLabel("비밀번호:");
		passwordField = new JPasswordField();
		add(passwordLabel);
		add(passwordField);

		SignInButton = new JButton("로그인");
		SignInButton.addActionListener(this);
		add(SignInButton);

		SignUpButton = new JButton("회원가입");
		SignUpButton.addActionListener(this);
		add(SignUpButton);
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SignInButton) {
        	
        } else if (e.getSource() == SignUpButton) {
        	System.out.println("회원가입 버튼");
        	JFrame frame=new JFrame();
        	frame.setTitle("회원가입");
        	frame.setSize(600, 200); // 프레임 크기 설정	
        	frame.setLayout(new GridLayout(5, 2));
        	
            // 컴포넌트 추가
            JLabel nameLabel = new JLabel("이름:");
            name = new JTextField();
            JLabel userIdLabel = new JLabel("아이디:");
            userId = new JTextField();
            JLabel emailLabel = new JLabel("이메일:");
            email = new JTextField();
            JLabel passwdLabel = new JLabel("비밀번호:");
            passwd= new JPasswordField();
            JButton signUp = new JButton("회원가입");

            // 컴포넌트를 프레임에 추가
            frame.add(nameLabel);
            frame.add(name);
            frame.add(userIdLabel);
            frame.add(userId);
            frame.add(emailLabel);
            frame.add(email);
            frame.add(passwdLabel);
            frame.add(passwd);
            frame.add(signUp);
            
            frame.setVisible(true); // 프레임을 보이도록 설정
            signUp.addActionListener(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				System.out.println("회원 가입 버튼 클릭");
    				revalidate(); // 변경된 내용을 다시 그리도록 갱신
    				UserDAO udao=new UserDAO();
    				String name1=name.getText();
    				String name2=userId.getText();
    				String name3=email.getText();
    				String name4=passwd.getText();
    				String a=udao.singUp(name1,name2,name3,name4);
    				System.out.println(a);
    			}
    		});//event
        }
    }

}
