package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.dao.LotteryDAO;
import com.dao.UserDAO;

public class MyPage extends JPanel {

    UserDAO userDao = new UserDAO();
    LotteryDAO lotteryDao = new LotteryDAO();

    private int lotteryNo;
    private String[] lotteryInfo;
    private String createdAt;
    private String category;
    private String numbers;

    private Main main;

    private Map<Integer, String[]> lotteryResults;

    private JPanel tablePanel;
    private JScrollPane tableScrollPane;

    public MyPage(int userNo) {
        setSize(1000, 500);
        setVisible(true);
        JPanel mainPanel = new JPanel(new BorderLayout());

        lotteryResults = lotteryDao.showLottery(userNo);

        // 1. 메뉴바
        JPanel menuPanel = new JPanel(new GridBagLayout());
        JLabel menuLabel = new JLabel("번호 생성 내역", SwingConstants.CENTER);
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menuPanel.setPreferredSize(new Dimension(1000, 50));
        menuPanel.add(menuLabel);

        // 2. 번호 생성 내역 테이블
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(0, 1));
        tablePanel.setBackground(Color.WHITE);

        tableScrollPane = new JScrollPane(tablePanel);
        tableScrollPane.setPreferredSize(new Dimension(900, 400));
        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        if (userNo == 0) {
            JPanel logoutPanel = new JPanel();
            logoutPanel.setPreferredSize(new Dimension(1000, 50));

            JLabel logoutLabel = new JLabel("로그인 한 사용자만 내 정보를 볼 수 있습니다", SwingConstants.CENTER);
            logoutPanel.add(logoutLabel);

            // 메인 배치
            mainPanel.add(logoutPanel, BorderLayout.CENTER);
            mainPanel.setVisible(true);
        } else {
            for (Map.Entry<Integer, String[]> entry : lotteryResults.entrySet()) {
                JPanel entryPanel = new JPanel(new GridLayout(1, 5)); // 카테고리, 번호, 삭제를 한 줄에 표시
                entryPanel.setPreferredSize(new Dimension(1000, 50));
                entryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

                lotteryNo = entry.getKey();
                lotteryInfo = entry.getValue();
                category = lotteryInfo[2];
                numbers = lotteryInfo[3];
                createdAt = lotteryInfo[1];
                createdAt = createdAt.substring(0, createdAt.length() - 7);

                // 복권 고유 번호
                JPanel lotteryNumPanel = new JPanel();
                JLabel lotteryNumLabel = new JLabel(String.valueOf(lotteryNo), SwingConstants.CENTER);
                lotteryNumPanel.setBackground(Color.WHITE);
                lotteryNumLabel.setForeground(Color.WHITE);
                lotteryNumPanel.add(lotteryNumLabel);
                entryPanel.add(lotteryNumPanel);

                // 복권 생성일
                JPanel createdPanel = new JPanel();
                JLabel createdLabel = new JLabel(createdAt, SwingConstants.CENTER);
                createdPanel.setBackground(Color.WHITE);
                createdPanel.add(createdLabel);
                entryPanel.add(createdPanel);

                // 카테고리
                JPanel categoryPanel = new JPanel();
                JLabel categoryLabel = new JLabel(category, SwingConstants.CENTER);
                categoryPanel.setBackground(Color.WHITE);
                categoryPanel.add(categoryLabel);
                entryPanel.add(categoryPanel);

                // 번호
                JPanel numbersPanel = new JPanel();
                JLabel numbersLabel = new JLabel(numbers, SwingConstants.CENTER);
                numbersPanel.setBackground(Color.WHITE);
                numbersPanel.add(numbersLabel);
                entryPanel.add(numbersPanel);

                // 삭제 라벨
                JPanel deletePanel = new JPanel();
                JLabel deleteLabel = new JLabel("삭제", SwingConstants.CENTER);
                deletePanel.setBackground(Color.WHITE);
                deleteLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        removeEntryPanel(entryPanel);
                    }
                });
                deletePanel.add(deleteLabel);
                entryPanel.add(deletePanel);

                // 빈 패널
                JPanel emptyPanel = new JPanel();
                JLabel emptyLabel = new JLabel("", SwingConstants.CENTER);
                emptyLabel.setForeground(Color.WHITE);
                emptyPanel.setBackground(Color.WHITE);
                emptyPanel.add(emptyLabel);
                entryPanel.add(emptyPanel);
                tablePanel.add(entryPanel);
            }

            // 3. 회원 탈퇴 버튼
            JPanel buttonPanel = new JPanel();
            JLabel deleteLabel = new JLabel("회원 탈퇴", SwingConstants.CENTER);
            deleteLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int result = JOptionPane.showConfirmDialog(null, "정말 탈퇴하시겠습니까?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        userDao.deleteUser(userNo);
                    } else {
                    	return;
                    }
                    main.logininit();
                }
            });

            deleteLabel.setPreferredSize(new Dimension(700, 50));
            buttonPanel.setPreferredSize(new Dimension(700, 50));
            buttonPanel.add(deleteLabel);

            // 메인 배치
            mainPanel.setLayout(new BorderLayout());
            mainPanel.add(menuPanel, BorderLayout.NORTH);
            mainPanel.add(tableScrollPane, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
            mainPanel.setVisible(true);
        }
        add(mainPanel, BorderLayout.CENTER);
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
