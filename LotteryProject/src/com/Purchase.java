package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Purchase extends JFrame {
    private JPanel[] categoryPanels;
    private boolean[] categoryCellStates;
    private final int PANELS_COUNT = 3;
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
        setSize(1100, 600);
        setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // 1. 카테고리: 자동, 반자동, 수동
        categoryPanels = new JPanel[PANELS_COUNT];
        categoryCellStates = new boolean[PANELS_COUNT];

        JPanel categoryPanel = new JPanel(new GridLayout(1, PANELS_COUNT));
        categoryPanel.setPreferredSize(new Dimension(400, 50));
        add(categoryPanel);

        for (int i = 0; i < PANELS_COUNT; i++) {
            categoryPanels[i] = new JPanel();
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
        numberPanel.setPreferredSize(new Dimension(400, 400));

        int value = 1;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                numberPanels[i][j] = new JPanel();
                numberPanels[i][j].setBackground(Color.WHITE);
                numberPanels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                numberCellValues[i][j] = value++;

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

                numberPanel.add(numberPanels[i][j]);
            }
        }

        // 3. 복권 번호 선택하기
        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmPanel.setPreferredSize(new Dimension(400, 50));
        confirmPanel.setOpaque(true);
        confirmPanel.setBackground(Color.WHITE); // 배경색 변경
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
        inputPanel.setPreferredSize(new Dimension(400, 500));
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
        JPanel resultPanel = new JPanel(new GridLayout(5, 3));
        resultPanel.setPreferredSize(new Dimension(700, 400));
        resultLabels = new JLabel[5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                resultLabels[i][j] = new JLabel("  ", SwingConstants.CENTER);
                resultLabels[i][j].setBackground(Color.WHITE);
                resultLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                resultLabels[i][j].setOpaque(true);
                resultPanel.add(resultLabels[i][j]);
            }
        }

        // 3. 저장하기
        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setPreferredSize(new Dimension(700, 50));
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
        outputPanel.setPreferredSize(new Dimension(700, 500));
        outputPanel.add(menuPanel, BorderLayout.NORTH);
        outputPanel.add(resultPanel, BorderLayout.CENTER);
        outputPanel.add(registerPanel, BorderLayout.SOUTH);

        // 메인 배치
        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(outputPanel, BorderLayout.CENTER);
    }

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
            categoryPanels[index].setBackground(Color.BLACK);
            categoryPanels[index].setForeground(Color.WHITE);
            categoryCellStates[index] = true;
            selectedIndex = index;
        }
    }

    // 숫자 선택 효과
    private void toggleNumber(int row, int col) {
        // If already selected 6 numbers, return
        if (selectedNumbers.size() >= ARRAY_SIZE && !numberCellStates[row][col]) {
            return;
        }

        numberCellStates[row][col] = !numberCellStates[row][col];
        Color bg = numberCellStates[row][col] ? Color.BLACK : Color.WHITE;
        Color fg = numberCellStates[row][col] ? Color.RED : Color.BLACK;
        numberPanels[row][col].setBackground(bg);
        numberPanels[row][col].setForeground(fg);

        // Update selectedNumbers list
        int value = numberCellValues[row][col];
        if (numberCellStates[row][col]) {
            if (!selectedNumbers.contains(value)) {
                selectedNumbers.add(value);
            }
        } else {
            selectedNumbers.remove(Integer.valueOf(value));
        }
    }

    // 선택된 카테고리와 숫자 출력
    private void selectLottery() {
        if (selectedNumbers.size() != ARRAY_SIZE || selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "카테고리와 " + ARRAY_SIZE + "개 숫자를 선택해주세요");
            return;
        }

        if (clickCnt > 4) {
            JOptionPane.showMessageDialog(null, "복권 번호는 한번에 최대 5개까지만 선택 가능합니다");
            return;
        }

        // Create a map to store selected category and numbers
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

        // 초기화
        resetSelection();
    }

    // 선택 초기화
    private void resetSelection() {
        // 카테고리 초기화
        if (selectedIndex != -1) {
            categoryPanels[selectedIndex].setBackground(Color.WHITE);
            categoryPanels[selectedIndex].setForeground(Color.BLACK);
            categoryCellStates[selectedIndex] = false;
            selectedIndex = -1;
        }

        // 숫자 초기화
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                numberCellStates[i][j] = false;
                numberPanels[i][j].setBackground(Color.WHITE);
                numberPanels[i][j].setForeground(Color.BLACK);
            }
        }

        // 선택된 숫자 리스트 초기화
        selectedNumbers.clear();
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

    // 실행
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Purchase();
        });
    }
}
