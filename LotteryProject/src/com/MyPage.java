package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.dao.LotteryDAO;
import com.dto.LotteryVO;

public class MyPage extends JPanel {
	
	LotteryDAO lotteryDao = new LotteryDAO();
	LotteryVO lotteryVo = new LotteryVO();
	
	private final int ROWS = 5;
    private final int COLS = 3;
    
    private int lotteryNo;
    private String[] lotteryInfo;
    private String userNo;
    private String createdAt;
    private String category;
    private String numbers;
    
    private Map<Integer, String[]> lotteryResults;
    
    public MyPage() {
    	setSize(700, 500);
        setVisible(true);
        JPanel mainPanel = new JPanel(new BorderLayout());

        userNo = "1";
        lotteryResults = lotteryDao.showLottery(userNo);
        // 1. 메뉴바
        JPanel menuPanel = new JPanel();        
        JLabel menuLabel = new JLabel("번호 생성 내역", SwingConstants.CENTER);
        menuPanel.setPreferredSize(new Dimension(700, 50));
        menuPanel.add(menuLabel);
        
        // 2. 번호 생성 내역 테이블
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(700, 400));
        
        for (Map.Entry<Integer, String[]> entry : lotteryResults.entrySet()) {
        	JPanel entryPanel = new JPanel(new GridLayout(1, 5)); // 카테고리, 번호, 삭제를 한 줄에 표시
        	entryPanel.setPreferredSize(new Dimension(700, 30));
        	entryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        	
        	lotteryNo = entry.getKey();
            lotteryInfo = entry.getValue();
            userNo = lotteryInfo[0];
            category = lotteryInfo[2];
            numbers = lotteryInfo[3];
            createdAt = lotteryInfo[1];
            createdAt = createdAt.substring(0, createdAt.length() - 7);

            // 복권 고유 번호 
            JPanel lotteryNoPanel = new JPanel();
            JLabel lotteryNoLabel = new JLabel(String.valueOf(lotteryNo), SwingConstants.CENTER);
            lotteryNoPanel.setPreferredSize(new Dimension(10, 10));
            lotteryNoPanel.add(lotteryNoLabel);
            entryPanel.add(lotteryNoPanel);
            
            // 복권 생성일
            JPanel createdPanel = new JPanel();
            JLabel createdLabel = new JLabel(createdAt, SwingConstants.CENTER);
            createdPanel.setPreferredSize(new Dimension(10, 10));
            createdPanel.add(createdLabel);
            entryPanel.add(createdPanel);
            
            // 카테고리
            JPanel categoryPanel = new JPanel();
            JLabel categoryLabel = new JLabel(category, SwingConstants.CENTER);
            categoryPanel.setPreferredSize(new Dimension(30, 10));
            categoryPanel.add(categoryLabel);
            entryPanel.add(categoryPanel);
            
            // 번호
            JPanel numbersPanel = new JPanel();
            JLabel numbersLabel = new JLabel(numbers, SwingConstants.CENTER);
            numbersPanel.setPreferredSize(new Dimension(50, 10));
            numbersPanel.add(numbersLabel);
            entryPanel.add(numbersPanel);
            
            // 삭제 라벨
            JPanel deletePanel = new JPanel();
            JLabel deleteLabel = new JLabel("삭제", SwingConstants.CENTER);
            deleteLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 해당 복권 번호 가져오기
                    JPanel parentPanel = (JPanel) deleteLabel.getParent().getParent();
                    // entryPanel의 첫 번째 자식 컴포넌트인 lotteryNoPanel에서 JLabel을 가져옴
                    JPanel entryPanel = (JPanel) parentPanel.getComponent(0);
                    JLabel lotteryNoLabel = (JLabel) entryPanel.getComponent(0);
                    String lotteryNoText = lotteryNoLabel.getText();
                    int lotteryNo = Integer.parseInt(lotteryNoText);

                    // DB에서 해당 복권 번호로 삭제
                    lotteryDao.deleteLottery(lotteryNo);

                    // entryPanel 제거
                    parentPanel.getParent().remove(parentPanel);
                    // 컴포넌트 제거 후 다시 그리기
                    revalidate();
                    repaint();
                }
            });            
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
        
        deleteLabel.setPreferredSize(new Dimension(700, 50));
        buttonPanel.setPreferredSize(new Dimension(700, 50));
        buttonPanel.add(deleteLabel);

        // 메인 배치
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.setVisible(true);
        
        add(mainPanel, BorderLayout.CENTER);
    }
}
