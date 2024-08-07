package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.dao.ChatDAO;
import com.dao.UserDAO;
import com.dto.ChatVO;

public class Chat extends JPanel {

    private int userNo;
    private int chatUser;
    private String chatRoom;
    private int chatRoomNum;
    private JPanel tablePanel;
    private JScrollPane tableScrollPane;

    private Category category = new Category(userNo);
    private ChatDAO cdao = new ChatDAO();
    private UserDAO udao = new UserDAO();

    public Chat(int userNo) {
        this.userNo = userNo;
//        setLayout(null);
        refreshChatList();
    }

    // 방 목록을 새로 고침
    private void refreshChatList() {
        removeAll();
        revalidate();
        repaint();
        setSize(1000, 500);
        setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // 1. 메뉴바
        JPanel menuPanel = new JPanel(new GridBagLayout());
        JLabel menuLabel = new JLabel("채팅방 내역", SwingConstants.CENTER);
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menuPanel.setPreferredSize(new Dimension(1000, 50));
        menuPanel.add(menuLabel);

        // 2. 채팅방 생성 내역 테이블
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(0, 1));
        tablePanel.setBackground(Color.WHITE);

        tableScrollPane = new JScrollPane(tablePanel);
        tableScrollPane.setPreferredSize(new Dimension(900, 400));
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        if (userNo == 0) {
            JPanel logoutPanel = new JPanel();
            logoutPanel.setPreferredSize(new Dimension(1000, 50));

            JLabel logoutLabel = new JLabel("로그인 한 사용자만 채팅에 참여할 수 있습니다", SwingConstants.CENTER);
            logoutPanel.add(logoutLabel);

            // 메인 배치
            mainPanel.add(logoutPanel, BorderLayout.CENTER);
            mainPanel.setVisible(true);
        } else {
            List<ChatVO> chatList = cdao.chatList();

            for (int i = 0; i < chatList.size(); i++) {
                JPanel entryPanel = new JPanel(new GridLayout(1, 2)); // 메세지, 삭제를 한 줄에 표시
                entryPanel.setPreferredSize(new Dimension(1000, 50));
                entryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

                chatUser = chatList.get(i).getUserNo();
//                chatRoom = 
                chatRoomNum = chatList.get(i).getChatNo();

                // 채팅방 메세지
                JPanel ChatPanel = new JPanel();
                
                JLabel ChatLabel = new JLabel(chatList.get(i).getChatName(), SwingConstants.CENTER);

                addRoomMouseListener(ChatLabel, chatRoomNum);

                ChatPanel.setBackground(Color.WHITE);
                ChatPanel.add(ChatLabel);
                entryPanel.add(ChatPanel);

                // 삭제 라벨
                JPanel deletePanel = new JPanel();
                deletePanel.setBackground(Color.WHITE);

                if (userNo == chatUser) {
                    JLabel chatRoomDel = new JLabel("삭제", SwingConstants.CENTER);
                    deletePanel.add(chatRoomDel);
                    addDeleteMouseListener(chatRoomDel, chatList.get(i).getChatNo());
                    
                } else if (userNo != chatUser) {
                    JLabel emptyLabel = new JLabel("", SwingConstants.CENTER);
                    deletePanel.add(emptyLabel);
                    
                }

                entryPanel.add(deletePanel);
                tablePanel.add(entryPanel); // 테이블 패널에 각각의 항목 패널 추가
                
            }

            // 3. 채팅방 생성 버튼
            JPanel buttonPanel = new JPanel();
            JLabel makeRoomLabel = new JLabel("채팅방 생성", SwingConstants.CENTER);
            // 채팅방 생성 메서드
            makeRoomLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (userNo > 0) {
                        String title = JOptionPane.showInputDialog("채팅방 제목을 입력하세요");
                        try {
                            cdao.addChat(userNo, title);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        refreshChatList();

                    } else {
                        JOptionPane.showMessageDialog(null, "로그인이 필요합니다.");
                    }
                }
            });

//            addRoomMouseListener(makeRoomLabel, chatRoomNum);

            buttonPanel.setPreferredSize(new Dimension(700, 50));
            buttonPanel.add(makeRoomLabel);

            // 메인 배치
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(menuPanel, BorderLayout.NORTH);
            mainPanel.add(tableScrollPane, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            mainPanel.setVisible(true);
        }
        add(mainPanel, BorderLayout.CENTER);
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
                    String userName = udao.userName(userNo);
                    int result = JOptionPane.showConfirmDialog(null, "방에 참여하시겠습니까?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    System.out.println("채팅방 확인1"+userNo+ userName+ chatNo);
                    if (result == JOptionPane.YES_OPTION) {
                    // 채팅방에 바로 참여
                    Client client = new Client(userNo, userName, chatNo);
                    client.setLocationRelativeTo(null); // 창을 화면 중앙에 위치시킴
                    client.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "로그인이 필요합니다.");
                }
            }
        });
    }


    
    private void removeEntryPanel(JPanel entryPanel) {
        tablePanel.remove(entryPanel);
        int panelCount = tablePanel.getComponentCount();
        int newHeight = panelCount * 60;
        tablePanel.setPreferredSize(new Dimension(900, newHeight));
        tableScrollPane.revalidate();
        tableScrollPane.repaint();
    }
}