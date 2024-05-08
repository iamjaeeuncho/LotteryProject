package com;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dao.ChatDAO;
import com.dao.UserDAO;
import com.dto.ChatVO;

public class Chat extends JPanel {
	int userNo;
	Category cate = new Category();
	ChatDAO cdao = new ChatDAO();
    UserDAO udao= new UserDAO();
    
    public Chat(int userNo) {
    	this.userNo=userNo;
        setLayout( null ); 
        System.out.println("쳇에서 유저"+userNo);
        JLabel useridLabel = new JLabel("오픈 채팅");
        useridLabel.setBounds(10,40,150,23); //위치,너비 높이
        useridLabel.setFont(new Font("Sans-serif",Font.BOLD,21));
        JButton mChat=new JButton("채팅방 생성");
        add(mChat);
        add(useridLabel);
        
        mChat.setBounds(750,40,100,40);
        List<ChatVO> chatList = cdao.chatList();
        int yOffset = 90; 
        for (int i = 0; i < chatList.size(); i++) {
        	JButton chatRoom = new JButton(chatList.get(i).getChatName());
            chatRoom.setBounds(150, yOffset, 700, 70); 
            add(chatRoom);
            revalidate(); 
            yOffset += 80;

            int index =  i ; // 배열로 인덱스 값 전달

            chatRoom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (userNo > 0) {
                    	int chatNo=chatList.get(index).getChatNo();
                        JOptionPane.showMessageDialog(null, "채팅방에 참여하시겠습니까?");
                        System.out.println("여기까지는 된다."+chatNo);
                        String userName=udao.userName(userNo);
                        System.out.println(userName+chatNo);
                    	Client client=new Client(userNo,userName,chatNo);
                    	client.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "로그인이 필요합니다.");
                    }
                }
            });
        }
        
        mChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("이게 왜 실행?");
				if(userNo>0) {
					String title = JOptionPane.showInputDialog("채팅방 제목을 입력하세요");
					System.out.println("쳇,"+userNo+title);
					cdao.addChat(userNo, title);
			        List<ChatVO> chatList = cdao.chatList();
			        int yOffset = 90; // 버튼의 초기 y 좌표
			        for (int i = 0; i < chatList.size(); i++) {
			            JButton chatRoom = new JButton(chatList.get(i).getChatName());
			            chatRoom.setBounds(150, yOffset, 700, 70); // 버튼의 위치를 동적으로 계산
			            add(chatRoom);
			            revalidate(); // 변경된 내용을 다시 그리도록 갱신
			            yOffset += 80; // 다음 버튼의 y 좌표를 조정
			        }//

				}else {
					JOptionPane.showMessageDialog(null,"로그인이 필요합니다.");
				}
			}
		});
    }
}
