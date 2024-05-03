package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Purchase extends JFrame {
    private JPanel[] categoryPanels;
    private boolean[] categoryCellStates;
    private int selectedIndex = -1;

    private JPanel[][] numberPanels;
    private int[][] numberCellValues;
    private boolean[][] numberCellStates;
    private final int ROWS = 7;
    private final int COLS = 7;
    private final int ARRAY_SIZE = 6;
    private ArrayList<Integer> selectedNumbers;

    private JLabel[][] resultLabels;
    private int clickCnt = 0;

    public Purchase() {
        setTitle("Purchase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        setSize(1000, 500);
        setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // 1. 카테고리: 자동, 반자동, 수동
        categoryPanels = new JPanel[3];
        categoryCellStates = new boolean[3];

        JPanel categoryPanel = new JPanel(new GridLayout(1, 3));
        categoryPanel.setPreferredSize(new Dimension(400, 50));
        add(categoryPanel);

        for (int i = 0; i < 3; i++) {
            categoryPanels[i] = new JPanel(new GridBagLayout());
            categoryPanels[i].setBackground(Color.WHITE);
            categoryPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel categoryLabel = new JLabel(getCategoryName(i));
            categoryPanels[i].add(categoryLabel);

            final int index = i;
            categoryPanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    toggleCategory(index);
                }
            });
            categoryPanel.add(categoryPanels[i]);
        }

        // 2. 숫자 6자리 입력
        numberPanels = new JPanel[ROWS][COLS];
        numberCellValues = new int[ROWS][COLS];
        numberCellStates = new boolean[ROWS][COLS];
        selectedNumbers = new ArrayList<>();

        JPanel numberPanel = new JPanel(new GridLayout(ROWS, COLS));
        numberPanel.setPreferredSize(new Dimension(400, 300));

        int value = 1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                numberPanels[i][j] = new JPanel(new GridBagLayout());
                numberPanels[i][j].setBackground(Color.WHITE);
                numberPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                
                if (value <= 45) {
                    numberCellValues[i][j] = value;
                    JLabel label = new JLabel(String.valueOf(numberCellValues[i][j]));
                    numberPanels[i][j].add(label);
                    
                    final int row = i;
                    final int col = j;
                    
                    numberPanels[i][j].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            toggleNumber(row, col);
                        }
                    });
                }
                else {
                    JLabel emptyLabel = new JLabel("");
                    numberPanels[i][j].add(emptyLabel);
                    numberPanels[i][j].setEnabled(false);
                }
                
                numberPanel.add(numberPanels[i][j]);
                value++;
            }
        }

        // 3. 복권 번호 선택하기
        JPanel confirmPanel = new JPanel(new GridBagLayout());
        confirmPanel.setPreferredSize(new Dimension(400, 50));
        confirmPanel.setOpaque(true);
        confirmPanel.setBackground(Color.WHITE);
        confirmPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel confirmLabel = new JLabel("선택하기", SwingConstants.CENTER);
        confirmLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectLottery();
            }
        });
        confirmPanel.add(confirmLabel);

        // 왼쪽 사이드
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new Dimension(400, 400));
        inputPanel.add(categoryPanel, BorderLayout.NORTH);
        inputPanel.add(numberPanel, BorderLayout.CENTER);
        inputPanel.add(confirmPanel, BorderLayout.SOUTH);

        // ----------------- OUTPUT -----------------
        // 1. 메뉴바
        JPanel menuPanel = new JPanel(new GridLayout(1, 1));
        JLabel menuLabel = new JLabel("선택 번호 확인", SwingConstants.CENTER);
        menuPanel.setPreferredSize(new Dimension(600, 50));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menuPanel.add(menuLabel);

        // 2. 결과 출력
        JPanel resultPanel = new JPanel(new GridLayout(5, 3));
        resultPanel.setPreferredSize(new Dimension(600, 300));
        resultLabels = new JLabel[5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                resultLabels[i][j] = new JLabel("  ", SwingConstants.CENTER);
                resultLabels[i][j].setOpaque(true);
                resultLabels[i][j].setBackground(Color.WHITE);
                resultLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (j == 2) {
                	JPanel buttonPanel = new JPanel(new GridBagLayout());
                	
                	int rowIndex = i;
                	
                	// 수정 버튼
                	JLabel modifyJLabel = new JLabel("수정", SwingConstants.CENTER);
                	modifyJLabel.setPreferredSize(new Dimension(60, 30));
                	modifyJLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            modifyLottery(rowIndex);
                        }
                    });
                	
                	// 삭제 버튼
                	JLabel deleteJLabel = new JLabel("삭제", SwingConstants.CENTER);
                	deleteJLabel.setPreferredSize(new Dimension(60, 30));
                	
                    buttonPanel.add(modifyJLabel);
                    buttonPanel.add(deleteJLabel);
                    
                    resultLabels[i][j].setLayout(new BorderLayout());
                    resultLabels[i][j].add(buttonPanel, BorderLayout.CENTER);
                    buttonPanel.setBackground(Color.WHITE);
                }
                resultPanel.add(resultLabels[i][j]);
            }
        }

        // 3. 저장하기
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setPreferredSize(new Dimension(600, 50));
        registerPanel.setOpaque(true);
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel registerLabel = new JLabel("저장하기", SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectLottery();
            }
        });
        registerPanel.add(registerLabel);

        // 오른쪽 사이드
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setPreferredSize(new Dimension(600, 400));
        outputPanel.add(menuPanel, BorderLayout.NORTH);
        outputPanel.add(resultPanel, BorderLayout.CENTER);
        outputPanel.add(registerPanel, BorderLayout.SOUTH);

        // 메인 배치
        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(outputPanel, BorderLayout.CENTER);
    }

    // ----------------- Methods -----------------
    // 카테고리 선택 효과
    private void toggleCategory(int index) {
        if (selectedIndex != -1) {
            categoryPanels[selectedIndex].setBackground(Color.WHITE);
            categoryPanels[selectedIndex].setForeground(Color.BLACK);
            categoryCellStates[selectedIndex] = false;
        }

        if (selectedIndex == index) {
            selectedIndex = -1;
        } else {
            resetNumbers(); // 다른 카테고리가 선택된 경우 숫자 패널 초기화
            categoryPanels[index].setBackground(Color.BLACK);
            categoryPanels[index].setForeground(Color.WHITE);
            categoryCellStates[index] = true;
            selectedIndex = index;
        }
    }

    // 숫자 선택 효과
    private void toggleNumber(int row, int col) {
        // 6개 이상 선택할 때
        if (selectedNumbers.size() >= ARRAY_SIZE && !numberCellStates[row][col]) {
            return;
        }
        
     // 자동 카테고리가 선택된 경우
        if (selectedIndex == 0) { 
            JOptionPane.showMessageDialog(null, "자동 카테고리에서는 숫자를 선택할 수 없습니다.");
            return;
        }
        
        numberCellStates[row][col] = !numberCellStates[row][col];
        Color bg = numberCellStates[row][col] ? Color.BLACK : Color.WHITE;
        Color fg = numberCellStates[row][col] ? Color.RED : Color.BLACK;
        numberPanels[row][col].setBackground(bg);
        numberPanels[row][col].setForeground(fg);

        // 선택 숫자 업데이트
        int value = numberCellValues[row][col];
        if (numberCellStates[row][col]) {
            if (!selectedNumbers.contains(value)) {
                selectedNumbers.add(value);
            }
        } else {
            selectedNumbers.remove(Integer.valueOf(value));
        }
    }

    // 선택 초기화
    private void resetCategory() {
        if (selectedIndex != -1) {
            categoryPanels[selectedIndex].setBackground(Color.WHITE);
            categoryPanels[selectedIndex].setForeground(Color.BLACK);
            categoryCellStates[selectedIndex] = false;
            selectedIndex = -1;
        }

        selectedNumbers.clear();         // 선택된 숫자 리스트 초기화
    }
    
    // 숫자 초기화
    private void resetNumbers() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                numberCellStates[i][j] = false;
                numberPanels[i][j].setBackground(Color.WHITE);
                numberPanels[i][j].setForeground(Color.BLACK);
                numberPanels[i][j].setEnabled(true); // 선택 가능하도록 설정
            }
        }
        
        selectedNumbers.clear();         // 선택된 숫자 리스트 초기화
    }

    // 선택된 카테고리와 숫자 출력
    private void selectLottery() {
    	// 1부터 45까지의 숫자가 담긴 리스트 생성
    	ArrayList<Integer> numbers = new ArrayList<>();
    	for (int i = 1; i <= 45; i++) {
    		numbers.add(i);
    	}
    	
    	// 자동 카테고리: 무작위로 6개의 숫자 선택
    	if (selectedIndex == 0) {
            // 
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                int index = random.nextInt(numbers.size());
                selectedNumbers.add(numbers.get(index));
                numbers.remove(index); // 선택한 숫자는 리스트에서 제거하여 중복 선택 방지
            }
        // 반자동 카테고리: 나머지 숫자 자동 선택
    	} else if (selectedIndex == 1){
    		int selectedlength = selectedNumbers.size();
    		
    		if (selectedlength == 0) {
    			JOptionPane.showMessageDialog(null, "반자동일 경우 수동 번호는 최소 1개는 선택해주세요");
        		return;
    		} else if (selectedlength > 5) {
        		JOptionPane.showMessageDialog(null, "반자동일 경우 수동 번호는 최대 5개까지 선택 가능합니다");
        		return;
    		} else {
                Random random = new Random();
                for (int i = 0; i < 6 - selectedlength; i++) {
                    int index = random.nextInt(numbers.size());
                    selectedNumbers.add(numbers.get(index));
                    numbers.remove(index); // 선택한 숫자는 리스트에서 제거하여 중복 선택 방지
                }
    		}
    		
    	// 수동 카테고리
        } else {
        	if (selectedNumbers.size() != ARRAY_SIZE || selectedIndex == -1) {
        		JOptionPane.showMessageDialog(null, "카테고리와 " + ARRAY_SIZE + "개 숫자를 선택해주세요");
        		return;
        	}
        	
        	if (clickCnt > 4) {
        		JOptionPane.showMessageDialog(null, "복권은 한번에 최대 5개까지만 발급 가능합니다");
        		return;
        	}
        	
        }
    	// 카테고리와 숫자를 맵형식으로 저장
    	Map<String, Object> selectionMap = new HashMap<>();
    	if (selectedIndex != -1) {
    		String selectedCategory = getCategoryName(selectedIndex);
    		selectionMap.put("category", selectedCategory);
    	}
    	
    	selectionMap.put("numbers", selectedNumbers);
    	
    	for (Map.Entry<String, Object> entry : selectionMap.entrySet()) {
    		if (entry.getKey().equals("category")) {
    			resultLabels[clickCnt][0].setText(entry.getValue().toString());
    		} else if (entry.getKey().equals("numbers")) {
    			resultLabels[clickCnt][1].setText(entry.getValue().toString());
    		}
    	}
    	
        clickCnt++;

        resetCategory(); // 초기화
        resetNumbers();
    }
    
    private String getCategoryName(int index) {
        if (index == 0) {
            return "자동";
        } else if (index == 1) {
            return "반자동";
        } else {
            return "수동";
        }
    }
    
    private void modifyLottery(int rowIndex) {
    	String value = resultLabels[rowIndex][1].getText();
    	System.out.println("수정" + rowIndex + value);
    }
    
    // 실행
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Purchase();
        });
    }
}
