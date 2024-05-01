package com;

//import com.dao.UserDAO;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.dao.UserDAO;

public class Login extends JPanel implements ActionListener {
	private JTextField userId;
	private JTextField passwd;
	private JButton SignInButton;
	private JButton SignUpButton;
	private JTextField name;
	private JTextField email;
	
	UserDAO udao=new UserDAO();
	public Login() {
		// JPanel initialization
		setLayout(new GridLayout(3, 2)); // Set layout for the JPanel

		JLabel useridLabel = new JLabel("아이디:");
		userId = new JTextField();
		add(useridLabel);
		add(userId);

		JLabel passwordLabel = new JLabel("비밀번호:");
		passwd = new JPasswordField();
		add(passwordLabel);
		add(passwd);

		SignInButton = new JButton("로그인");
		SignInButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userid=userId.getText(); //여기가 에러야
				String password=passwd.getText();
				Object obj= udao.signIn(userid, password);
				int userNo = ((BigDecimal)obj).intValue();
				System.out.println(userNo);
				if(userNo>0) {
					System.out.println("로그인 성공");
				}else{
					JOptionPane.showMessageDialog(null,"아이디와 비밀번호를 확인하세요");
					//null인 경우 다이얼로그가 화면 중앙에 표시
				}
			}
			
		});
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
    				String name1=name.getText();
    				String name2=userId.getText();
    				String name3=email.getText();
    				String name4=passwd.getText();
    				String mesg=udao.signUp(name1,name2,name3,name4);				
    				if(mesg.equals("회원가입이 완료되었습니다.")) {
    					JOptionPane.showMessageDialog(null,mesg);
    					frame.dispose();
    				}else {
    					JOptionPane.showMessageDialog(null,mesg);
    				}
    			}
    		});//event
        }
    }

}
