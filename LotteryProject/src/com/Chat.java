package com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
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
        JButton mChat=new JButton("채팅방 생성");
        add(mChat);
        
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
                        String userName=udao.userName(userNo);
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
				if(userNo>0) {
					String title = JOptionPane.showInputDialog("채팅방 제목을 입력하세요");
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
