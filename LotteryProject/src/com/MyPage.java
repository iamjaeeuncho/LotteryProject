package com;
import javax.swing.*;

import com.dao.LotteryDAO;
import com.dto.LotteryVO;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

public class MyPage {
	
	LotteryDAO lotteryDao = new LotteryDAO();
	LotteryVO lotteryVo = new LotteryVO();
	
	private final int ROWS = 5;
    private final int COLS = 3;
    
    public MyPage() {
    	JFrame frame = new JFrame();
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 1. 메뉴바
        JPanel menuPanel = new JPanel();        
        JLabel menuLabel = new JLabel("번호 생성 내역", SwingConstants.CENTER);
        menuLabel.setPreferredSize(new Dimension(1000, 50));
        menuPanel.setPreferredSize(new Dimension(1000, 50));
        menuPanel.add(menuLabel);
        
        // 2. 번호 생성 내역 테이블
        JPanel tablePanel = new JPanel(new GridLayout(ROWS, COLS));
        tablePanel.setPreferredSize(new Dimension(1000, 400));
        
        Map<Integer, String[]> lotteryResults = lotteryDao.ShowLottery();

        for (Map.Entry<Integer, String[]> entry : lotteryResults.entrySet()) {
        	JPanel entryPanel = new JPanel(new GridLayout(1, 3)); // 카테고리, 번호, 삭제를 한 줄에 표시
        	
            Integer category = entry.getKey();
            String[] lotteryInfo = entry.getValue();
            
            // 카테고리 라벨
            JLabel categoryLabel = new JLabel(category.toString(), SwingConstants.CENTER);
            JPanel categoryPanel = new JPanel();
            categoryPanel.add(categoryLabel);
            entryPanel.add(categoryPanel);
            
            // 번호 라벨
            StringBuilder numbersString = new StringBuilder();
            for (String number : lotteryInfo) {
                numbersString.append(number).append(" ");
            }
            JLabel numbersLabel = new JLabel(numbersString.toString(), SwingConstants.CENTER);
            JPanel numbersPanel = new JPanel();
            numbersPanel.add(numbersLabel);
            entryPanel.add(numbersPanel);
            
            // 삭제 라벨
            JLabel deleteLabel = new JLabel("삭제", SwingConstants.CENTER);
            JPanel deletePanel = new JPanel();
            deletePanel.add(deleteLabel);
            entryPanel.add(deletePanel);
            
            tablePanel.add(entryPanel); // 테이블 패널에 각각의 항목 패널 추가
        }


        
        // 3. 회원 탈퇴 버튼
        JPanel buttonPanel = new JPanel();
        JLabel deleteLabel = new JLabel("회원 탈퇴", SwingConstants.CENTER);
        deleteLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//            	lotteryDao.ShowLottery();
            }
        });
        
        deleteLabel.setPreferredSize(new Dimension(1000, 50));
        buttonPanel.setPreferredSize(new Dimension(1000, 50));
        buttonPanel.add(deleteLabel);

        // 메인 배치
        frame.setLayout(new BorderLayout());
        frame.add(menuPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> {
            new MyPage();
        });
    }
}
