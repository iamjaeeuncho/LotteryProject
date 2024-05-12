package com;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.ImageIcon;
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
    private UserDAO udao = new UserDAO();
    private Main main;
    private JFrame jframe;

    public Login(Main main) {
        this.main = main;
        setLayout(null);

        JLabel img = new JLabel();
        ImageIcon bsImg = new ImageIcon(Login.class.getResource("/img/icon.png"));
        Image scaledImage = bsImg.getImage().getScaledInstance(bsImg.getIconWidth() / 2, bsImg.getIconHeight() / 2, Image.SCALE_SMOOTH);
        bsImg = new ImageIcon(scaledImage);
        img.setIcon(bsImg);

        // JLabel을 패널에 추가합니다.
        add(img);

        // JLabel의 위치를 오른쪽으로 조정합니다.
        int x = 30; // x 좌표
        int y = 20; // y 좌표
        int width = bsImg.getIconWidth(); // 이미지의 폭
        int height = bsImg.getIconHeight(); // 이미지의 높이
        img.setBounds(x + 350, y + 35, width, height); // 오른쪽으로 50만큼 이동
        //여기까지 이미지 위치 조정

        JLabel useridLabel = new JLabel(" 아이디");
        useridLabel.setBounds(229, 300, 195, 64);
        useridLabel.setFont(new Font("SansSerif", Font.BOLD, 18)); // 글꼴 크기 설정
        add(useridLabel);

        userId = new JTextField();
        userId.setBounds(483, 300, 304, 64);
        userId.setColumns(10);
        add(userId);

        JLabel passwordLabel = new JLabel(" 비밀번호");
        passwordLabel.setBounds(229, 370, 195, 64);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 18)); // 글꼴 크기 설정
        add(passwordLabel);

        passwd = new JPasswordField();
        passwd.setColumns(10);
        passwd.setBounds(483, 370, 304, 64);
        add(passwd);

        SignInButton = new JButton("로그인");
        SignInButton.setBounds(209, 490, 237, 64);
        SignInButton.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
        SignInButton.addActionListener(new ActionListener() {
        	 @Override
        	    public void actionPerformed(ActionEvent e) {
        	        if (e.getSource() == SignInButton) {
        	            String userid = userId.getText();
        	            String password = passwd.getText();
        	            Object obj = udao.signIn(userid, password);
        	            int userNo = ((BigDecimal) obj).intValue();
        	            if (userNo > 0) {
        	                JOptionPane.showMessageDialog(null, "안녕하세요 반갑습니다 :)");
        	                main.setUserNo(userNo); // 사용자 번호 설정
        	                main.logininit(); // 메인 클래스의 logininit() 메서드 호출하여 중앙 패널 변경
        	            } else {
        	                JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인하세요");
        	            }
        	        } else if (e.getSource() == SignUpButton) {
        	            // 회원가입 처리
        	        }
        	 }
        });
        add(SignInButton);

        SignUpButton = new JButton("회원가입");
        SignUpButton.setFont(new Font("SansSerif", Font.BOLD, 13)); // 글꼴 크기 설정
        SignUpButton.setBounds(493, 490, 304, 64);
        SignUpButton.addActionListener(this);
        add(SignUpButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SignInButton) {

        } else if (e.getSource() == SignUpButton) {
            JFrame frame = new JFrame();
            frame.setTitle("회원가입");
            frame.setSize(850, 600); // 크기 조정
            frame.getContentPane().setLayout(null);

            name = new JTextField();
            name.setBounds(413, 107, 304, 44);
            frame.getContentPane().add(name);
            name.setColumns(10);

            JLabel nameLabel = new JLabel("이름:");
            nameLabel.setBounds(206, 106, 195, 44);
            frame.getContentPane().add(nameLabel);

            JButton signUp = new JButton("회원가입");
            signUp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });

            JLabel userIdLabel = new JLabel("아이디:");
            userIdLabel.setBounds(206, 195, 195, 44);
            frame.getContentPane().add(userIdLabel);

            JLabel emailLabel = new JLabel("이메일:");
            emailLabel.setBounds(206, 278, 195, 44);
            frame.getContentPane().add(emailLabel);

            JLabel passwdLabel = new JLabel("비밀번호:");
            passwdLabel.setBounds(206, 365, 195, 44);
            frame.getContentPane().add(passwdLabel);

            signUp.setBounds(238, 454, 414, 64);
            frame.getContentPane().add(signUp);

            userId = new JTextField();
            userId.setColumns(10);
            userId.setBounds(413, 195, 304, 44);
            frame.getContentPane().add(userId);

            email = new JTextField();
            email.setColumns(10);
            email.setBounds(413, 278, 304, 44);
            frame.getContentPane().add(email);

            passwd = new JPasswordField();
            passwd.setColumns(10);
            passwd.setBounds(413, 366, 304, 44);
            frame.getContentPane().add(passwd);
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
            frame.setLocationRelativeTo(null); // 화면 중앙에 표시

            signUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name1 = name.getText();
                    String name2 = userId.getText();
                    String name3 = email.getText();
                    String name4 = passwd.getText();
                    String mesg = null;
                    if (name1.isEmpty() || name2.isEmpty() || name3.isEmpty() || name4.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "모든 정보를 입력해주세요.");
                    } else {
                        try {
                            mesg = udao.signUp(name1, name2, name3, name4);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        if (mesg.equals("회원가입이 완료되었습니다.")) {
                            JOptionPane.showMessageDialog(null, mesg);
                            frame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, mesg);
                        }
                    }
                }
            });//event
        }
    }
}