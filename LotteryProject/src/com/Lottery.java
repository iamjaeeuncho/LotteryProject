package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.dao.LotteryDAO;
import com.dto.LotteryVO;

public class Lottery extends JPanel {
	
	LotteryDAO lotteryDao = new LotteryDAO();
	LotteryVO lotteryVo = new LotteryVO();
	
    private JPanel[] categoryPanels;
    private boolean[] categoryCellStates;
    private int selectedIndex = -1;

    private JPanel[][] numberPanels;
    private int[][] numberCellValues;
    private boolean[][] numberCellStates;
    private ArrayList<Integer> selectedNumbers;
    private ArrayList<Integer> savedNumbers;
    private final int NUMBER_ROWS = 7;
    private final int NUMBER_COLS = 7;
    private final int NUMBER_ARRAY_SIZE = 6;

    private JPanel[][] resultPanels;
    private String[][] resultCellValues;
    private final int RESULT_ROWS = 5;
    private final int RESULT_COLS = 3;
    
    private JLabel[][] resultLabels;

    public Lottery() {
        setBackground(Color.WHITE);
        setSize(700, 500);
        setVisible(true);
        JPanel mainPanel = new JPanel(new BorderLayout());

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
        numberPanels = new JPanel[NUMBER_ROWS][NUMBER_COLS];
        numberCellValues = new int[NUMBER_ROWS][NUMBER_COLS];
        numberCellStates = new boolean[NUMBER_ROWS][NUMBER_COLS];
        selectedNumbers = new ArrayList<Integer>();

        JPanel numberPanel = new JPanel(new GridLayout(NUMBER_ROWS, NUMBER_COLS));
        numberPanel.setPreferredSize(new Dimension(400, 300));
        
        int num = 1;
        for (int i = 0; i < NUMBER_ROWS; i++) {
            for (int j = 0; j < NUMBER_COLS; j++) {
                numberPanels[i][j] = new JPanel(new GridBagLayout());
                numberPanels[i][j].setBackground(Color.WHITE);
                numberPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                
                if (num <= 45) {
                    numberCellValues[i][j] = num;
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
                num++;
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
        menuPanel.setPreferredSize(new Dimension(700, 50));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menuPanel.add(menuLabel);

        // 2. 결과 출력
        resultPanels = new JPanel[RESULT_ROWS][RESULT_COLS];
        resultCellValues = new String[RESULT_ROWS][RESULT_COLS];
        savedNumbers = new ArrayList<Integer>();

        JPanel resultPanel = new JPanel(new GridLayout(RESULT_ROWS, RESULT_COLS));
        resultPanel.setPreferredSize(new Dimension(700, 500));

        String value = "";
        resultLabels = new JLabel[RESULT_ROWS][RESULT_COLS];

        for (int i = 0; i < RESULT_ROWS; i++) {
            for (int j = 0; j < RESULT_COLS; j++) {
                resultPanels[i][j] = new JPanel(new GridBagLayout());
                resultPanels[i][j].setBackground(Color.WHITE);
                resultPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                resultCellValues[i][j] = value;
                resultLabels[i][j] = new JLabel(String.valueOf(resultCellValues[i][j]));
                resultPanels[i][j].add(resultLabels[i][j]);

                if (j == 2) {
                    JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

                    int rowIndex = i;

                    // 수정 버튼
                    JLabel modifyLabel = new JLabel("수정", SwingConstants.CENTER);
                    modifyLabel.setPreferredSize(new Dimension(60, 30));
                    modifyLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            modifyLottery(rowIndex);
                        }
                    });

                    // 삭제 버튼
                    JLabel deleteLabel = new JLabel("삭제", SwingConstants.CENTER);
                    deleteLabel.setPreferredSize(new Dimension(60, 30));
                    deleteLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            deleteLottery(rowIndex);
                        }
                    });

                    buttonPanel.add(modifyLabel);
                    buttonPanel.add(deleteLabel);
                    buttonPanel.setBackground(Color.WHITE);

                    resultPanels[i][j].setLayout(new BorderLayout());
                    resultPanels[i][j].add(buttonPanel, BorderLayout.CENTER);
                }
                resultPanel.add(resultPanels[i][j]);
            }
        }

        // 3. 저장하기
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setPreferredSize(new Dimension(700, 50));
        registerPanel.setOpaque(true);
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel registerLabel = new JLabel("저장하기", SwingConstants.CENTER);
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lotteryDao.saveLottery(saveLottery());
            }
        });
        registerPanel.add(registerLabel);

        // 오른쪽 사이드
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setPreferredSize(new Dimension(700, 400));
        outputPanel.add(menuPanel, BorderLayout.NORTH);
        outputPanel.add(resultPanel, BorderLayout.CENTER);
        outputPanel.add(registerPanel, BorderLayout.SOUTH);

        // 메인 배치
        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
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
        if (selectedNumbers.size() >= NUMBER_ARRAY_SIZE && !numberCellStates[row][col]) {
            return;
        }
        
        // 자동 카테고리가 선택된 경우
        if (selectedIndex == 0) { 
            JOptionPane.showMessageDialog(null, "자동 카테고리에서는 숫자를 선택할 수 없습니다.");
            return;
        // 반자동 카테고리가 선택된 경우
        } else if (selectedIndex == 1 && selectedNumbers.size() >= NUMBER_ARRAY_SIZE - 1 && !numberCellStates[row][col]) { 
        	JOptionPane.showMessageDialog(null, "반자동일 경우 수동 번호는 최대 5개까지 선택 가능합니다");
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
                savedNumbers.add(value);
            }
        } else {
            selectedNumbers.remove(Integer.valueOf(value));
            savedNumbers.remove(Integer.valueOf(value));
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
        for (int i = 0; i < NUMBER_ROWS; i++) {
            for (int j = 0; j < NUMBER_COLS; j++) {
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
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        for (int i = 1; i <= 45; i++) {
            randomNumbers.add(i);
        }
        
//		if (!resultLabels[RESULT_ROWS][0].getText().equals("")) {
//			JOptionPane.showMessageDialog(null, "복권 번호는 한번에 최소 1개가 있어야 발급 가능합니다");
//		}

        // 카테고리 미선택시
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "카테고리를 선택해주세요");
            return;
        }

        // 자동 카테고리: 무작위로 6개의 숫자 선택
        if (selectedIndex == 0) {
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                int index = random.nextInt(randomNumbers.size());
                selectedNumbers.add(randomNumbers.get(index));
                randomNumbers.remove(index); // 선택한 숫자는 리스트에서 제거하여 중복 선택 방지
            }
        // 반자동 카테고리: 선택된 숫자 이외 숫자 자동 선택
        } else if (selectedIndex == 1) {
            int selectedlength = selectedNumbers.size();
            if (selectedlength == 0) {
                JOptionPane.showMessageDialog(null, "반자동일 경우 수동 번호는 최소 1개는 선택해주세요");
                return;
            } else {
                Random random = new Random();
                for (int i = 0; i < 6 - selectedlength; i++) {
                    int index = random.nextInt(randomNumbers.size());
                    selectedNumbers.add(randomNumbers.get(index));
                    randomNumbers.remove(index); // 선택한 숫자는 리스트에서 제거하여 중복 선택 방지
                }
            }
        // 수동 카테고리
        } else if (selectedIndex == 2) {
            if (selectedNumbers.size() != NUMBER_ARRAY_SIZE || selectedIndex == -1) {
                JOptionPane.showMessageDialog(null, "카테고리와 " + NUMBER_ARRAY_SIZE + "개 숫자를 선택해주세요");
                return;
            }
        }

        Collections.sort(selectedNumbers);

        // 카테고리와 숫자를 맵형식으로 저장
        Map<String, Object> selectionMap = new HashMap<>();
        if (selectedIndex != -1) {
            String selectedCategory = getCategoryName(selectedIndex);
            selectionMap.put("category", selectedCategory);
        }

        selectionMap.put("numbers", selectedNumbers);

        // 비어 있는 행에 결과 입력
        for (int i = 0; i < RESULT_ROWS; i++) {
            if (resultLabels[i][0].getText().isEmpty()) {
                for (Map.Entry<String, Object> entry : selectionMap.entrySet()) {
                    if (entry.getKey().equals("category")) {
                        resultLabels[i][0].setText(entry.getValue().toString());
                    } else if (entry.getKey().equals("numbers")) {
                        resultLabels[i][1].setText(entry.getValue().toString());
                    }
                }
                break;
            }
        }

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
    
    private int[] getSavedNumbers() {
        // 수동으로 선택된 숫자만 배열로 반환
        int[] result = new int[savedNumbers.size()];
        int index = 0;
        for (int num : savedNumbers) {
            result[index++] = num;
        }
        return result;
    }
    
    private void WeightedRandom() {
    	
    }
    
    private void modifyLottery(int rowIndex) {
    	String category = resultLabels[rowIndex][0].getText();
    	if (category.equals("자동")) {
    		toggleCategory(0);
    	} else if (category.equals("반자동")) {
    		toggleCategory(1);
    	} else if (category.equals("수동")) {
    		toggleCategory(2);
    	}
    	
    	selectedNumbers.clear();
    	
    	for (int i = 0; i < NUMBER_ROWS; i++) {
            for (int j = 0; j < NUMBER_COLS; j++) {
                int value = numberCellValues[i][j];
                if (savedNumbers.contains(value)) {
                    toggleNumber(i, j);
                }
            }
        }
        deleteLottery(rowIndex);
    }
    
    private void deleteLottery(int rowIndex) {
    	for (int i = 0; i < 2; i++) {    		
    		resultLabels[rowIndex][i].setText("");
    	}
    }
    
    private Map<Integer, Object> saveLottery() {    	
    	// 카테고리와 숫자를 맵형식으로 저장
    	Map<Integer, Object> lotteryMap = new HashMap<>();

        for (int i = 1; i <= RESULT_ROWS; i++) {
        	int index = i;
            String category = resultLabels[i - 1][0].getText();
            String numbers = resultLabels[i - 1][1].getText();

            lotteryMap.put(index, new String[]{category, numbers});
            System.out.println(index + category + numbers);
        }
        return lotteryMap;
    }
}