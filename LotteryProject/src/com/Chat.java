package com;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
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
    UserDAO udao = new UserDAO();

    public Chat(int userNo) {
        this.userNo = userNo;
        setLayout(null);
        refreshChatList();
    }

    // 방 목록을 새로 고칩니다.
    private void refreshChatList() {
        removeAll();
        revalidate();
        repaint();
        
        JButton mChat = new JButton("채팅방 생성");
        add(mChat);

        mChat.setBounds(750, 40, 150, 40);
        
        // 채팅방 생성 메서드
        mChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	        	System.out.println("여깅");
	            if (userNo > 0) {
	                String title = JOptionPane.showInputDialog("채팅방 제목을 입력하세요");
	                System.out.println("여깅"+userNo);
	                cdao.addChat(userNo, title);
	                refreshChatList();
	            } else {
	                JOptionPane.showMessageDialog(null, "로그인이 필요합니다.");
	            }
	        }
		});

        List<ChatVO> chatList = cdao.chatList();
        int yOffset = 90;
        for (int i = 0; i < chatList.size(); i++) {
            JLabel chatRoom = new JLabel(chatList.get(i).getChatName());
            chatRoom.setBounds(150, yOffset, 700, 70);
            chatRoom.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            add(chatRoom);
            if (userNo == chatList.get(i).getUserNo()) {
                JLabel chatRoomDel = new JLabel("삭제");
                chatRoomDel.setBounds(870, yOffset, 70, 70);
                add(chatRoomDel);
                addDeleteMouseListener(chatRoomDel, chatList.get(i).getChatNo());
            }
            addRoomMouseListener(chatRoom, chatList.get(i).getChatNo());
            yOffset += 80;
        }
    }

    // 채팅방 삭제 이벤트 설정
    private void addDeleteMouseListener(JLabel label, int chatNo) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    cdao.chatRoomDel(chatNo);
                    refreshChatList();
                }
            }
        });
    }

    // 채팅방 클릭 이벤트 설정
    private void addRoomMouseListener(JLabel label, int chatNo) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (userNo > 0) {
                    int result = JOptionPane.showConfirmDialog(null, "채팅방에 참여하시겠습니까?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String userName = udao.userName(userNo);
                        System.out.println("클라이언트 유저네임"+userName);
                        Client client = new Client(userNo, userName, chatNo);
                        System.out.println("클라이언트 유저네임"+userNo+userName);
                        client.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "로그인이 필요합니다.");
                }
            }
        });
    }
}
