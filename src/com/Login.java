package com;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.Timer;

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

    public Login(Main main) {
        this.main = main;
        setLayout(null);

        JLabel img = new JLabel();
        ImageIcon bsImg = new ImageIcon(Login.class.getResource("/img/icon.png"));
        Image scaledImage = bsImg.getImage().getScaledInstance(bsImg.getIconWidth() / 2, bsImg.getIconHeight() / 2, Image.SCALE_SMOOTH);
        bsImg = new ImageIcon(scaledImage);
        
        img.addMouseListener(new MouseAdapter() {
            private Timer timer;
            private int deltaX = 2;

            @Override
            public void mouseEntered(MouseEvent e) {
                timer = new Timer(10, (event) -> {
                    img.setLocation(img.getX() + deltaX, img.getY());
                    if (img.getX() <= 320 || img.getX() >= 520) {
                        deltaX *= -1;
                    }
                });
                timer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                timer.stop();
                img.setLocation(420, img.getY());
            }
        });
        img.setIcon(bsImg);
        add(img);

        // 이미지 위치 조정
        int x = 420; // x 좌표
        int y = 20; // y 좌표
        int width = bsImg.getIconWidth(); // 이미지의 폭
        int height = bsImg.getIconHeight(); // 이미지의 높이
        img.setBounds(x, y, width, height);

        JLabel useridLabel = new JLabel("아이디");
        useridLabel.setBounds(325, 275, 100, 50);
        add(useridLabel);

        userId = new JTextField();
        userId.setBounds(500, 275, 250, 50);
        userId.setColumns(10);
        add(userId);

        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setBounds(325, 330, 100, 50);
        add(passwordLabel);

        passwd = new JPasswordField();
        passwd.setColumns(10);
        passwd.setBounds(500, 330, 250, 50);
        add(passwd);

        SignUpButton = new JButton("회원가입");
        SignUpButton.setBounds(325, 400, 200, 50);
        SignUpButton.addActionListener(this);
        add(SignUpButton);
        
        SignInButton = new JButton("로그인");
        SignInButton.setBounds(550, 400, 200, 50);
        SignInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userid = userId.getText();
                String password = passwd.getText();
                Object obj = udao.signIn(userid, password);
                int userNo = ((BigDecimal) obj).intValue();
                if (userNo > 0) {
                    // main 으로 변경
                    main.setUserNo(userNo); // 사용자 번호 설정
                    JOptionPane.showMessageDialog(null, "안녕하세요 반갑습니다 :)");
                    main.logininit(); 
                } else {
                    JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인하세요");
                }
            }
        });
        add(SignInButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	// 로그인 버튼이 클릭되었을 때
        if (e.getSource() == SignInButton) {
            String userid = userId.getText();
            String password = passwd.getText();
            Object obj = udao.signIn(userid, password);
            int userNo = ((BigDecimal) obj).intValue();
            if (userNo > 0) {
                JOptionPane.showMessageDialog(null, "안녕하세요 반갑습니다 :)");
                main.logininit(); 
                main.setUserNo(userNo); // 사용자 번호 설정
            } else {
                JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인하세요");
            }
        // 회원가입 버튼 클릭 시
        } else if (e.getSource() == SignUpButton) {
            JFrame frame = new JFrame();
            frame.setTitle("회원가입");
            frame.setSize(850, 600); // 크기 조정
            frame.getContentPane().setLayout(null);

            JLabel nameLabel = new JLabel("이름");
            nameLabel.setBounds(175, 100, 50, 50);
            frame.getContentPane().add(nameLabel);

            JLabel userIdLabel = new JLabel("아이디");
            userIdLabel.setBounds(175, 175, 50, 50);
            frame.getContentPane().add(userIdLabel);

            JLabel emailLabel = new JLabel("이메일");
            emailLabel.setBounds(175, 250, 50, 50);
            frame.getContentPane().add(emailLabel);

            JLabel passwdLabel = new JLabel("비밀번호");
            passwdLabel.setBounds(175, 325, 50, 50);
            frame.getContentPane().add(passwdLabel);

            
            name = new JTextField();
            name.setBounds(400, 100, 275, 50);
            frame.getContentPane().add(name);
            name.setColumns(10);

            userId = new JTextField();
            userId.setColumns(10);
            userId.setBounds(400, 175, 275, 50);
            frame.getContentPane().add(userId);

            email = new JTextField();
            email.setColumns(10);
            email.setBounds(400, 250, 275, 50);
            frame.getContentPane().add(email);

            passwd = new JPasswordField();
            passwd.setColumns(10);
            passwd.setBounds(400, 325, 275, 50);
            frame.getContentPane().add(passwd);
            
            JButton signUp = new JButton("회원가입");
            signUp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }
            });
            
            signUp.setBounds(200, 425, 450, 50);
            frame.getContentPane().add(signUp);

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
            });
        }
    }
}