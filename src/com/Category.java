package com;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Category extends JPanel {
   private int userNo = 0;
   private JButton lottery;
   private JButton chat;
   private JButton myPage;
   private JButton loginout;
   private JPanel mainPanel = new JPanel();

   public Category() {
       initializeUI();
   }
   
   public Category(int userNo) {
      this.userNo = userNo;
       initializeUI();
   }

   private void initializeUI() {
       setLottery(new JButton("번호 생성"));
       getLottery().setPreferredSize(new Dimension(230, 60));
       getLottery().setFont(new Font("SansSerif", Font.BOLD, 13));

       chat= new JButton("채팅");
       getChat().setPreferredSize(new Dimension(230, 60));
       getChat().setFont(new Font("SansSerif", Font.BOLD, 13));

       myPage = new JButton("내 정보");
       myPage.setFont(new Font("SansSerif", Font.BOLD, 13));
       myPage.setPreferredSize(new Dimension(230, 60));

       getMainPanel().setLayout(new FlowLayout());
       getMainPanel().add(getLottery());
       getMainPanel().add(myPage);
       getMainPanel().add(chat);

       updateLoginButton(); // 초기 로그인 상태에 따라 버튼 설정

       add(getMainPanel()); // mainPanel을 Category 패널에 추가

       // 패널을 새로고침하여 변경된 내용을 보여줍니다.
       revalidate();
       repaint();
   }

   public void updateLoginButton() {
       // 기존에 추가된 로그인/로그아웃 버튼이 있다면 제거합니다.
       if (loginout != null) {
           getMainPanel().remove(loginout);
       }

       if (userNo > 0) {
           // 로그인된 상태에서 로그아웃 버튼으로 변경합니다.
           if (loginout == null) {
               loginout = new JButton("로그아웃");
               loginout.setFont(new Font("SansSerif", Font.BOLD, 13));
               loginout.setPreferredSize(new Dimension(230, 60));
           } else {
               loginout.setText("로그아웃");
           }
       } else {
           // 로그아웃된 상태에서 로그인 버튼으로 변경합니다.
           if (loginout == null) {
               loginout = new JButton("로그인");
               loginout.setFont(new Font("SansSerif", Font.BOLD, 13));
               loginout.setPreferredSize(new Dimension(230, 60));
           } else {
               loginout.setText("로그인");
           }
       }

       // 생성한 버튼을 현재 패널에 추가합니다.
       getMainPanel().add(loginout);
       revalidate(); // 변경된 패널 다시 그리기
       repaint();
   }

   public int getUserNo() {
       return userNo;
   }

   public void setUserNo(int userNo) {
       this.userNo = userNo;
       updateLoginButton(); // 사용자 번호가 업데이트되면 버튼을 다시 설정합니다.
   }

   public JButton getLoginout() {
       return loginout;
   }

   public void setLoginout(JButton loginout) {
       this.loginout = loginout;
   }

   public JButton getChat() {
       return chat;
   }

   public void setChat(JButton chat) {
       this.chat = chat;
   }

   public JButton getLottery() {
       return lottery;
   }

   public void setLottery(JButton lottery) {
       this.lottery = lottery;
   }

public JPanel getMainPanel() {
	return mainPanel;
}

public void setMainPanel(JPanel mainPanel) {
	this.mainPanel = mainPanel;
}

public JButton getMyPage() {
	return myPage;
}

public void setMyPage(JButton myPage) {
	this.myPage = myPage;
}
}