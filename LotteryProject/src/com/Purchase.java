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

    private JLabel[][] resultLabels; // Moved to class level

    public Purchase() {
        setTitle("Purchase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel); // Set mainPanel as the content pane

        // 1. 카테고리: 자동, 반자동, 수동
        categoryPanels = new JPanel[PANELS_COUNT];
        categoryCellStates = new boolean[PANELS_COUNT];

        JPanel categoryPanel = new JPanel(new GridLayout(1, PANELS_COUNT));
        add(categoryPanel);

        for (int i = 0; i < PANELS_COUNT; i++) {
            categoryPanels[i] = new JPanel();
            categoryPanels[i].setBackground(Color.WHITE);
            categoryPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (i == 0) {
                JLabel label = new JLabel("자동", SwingConstants.CENTER);
                categoryPanels[i].add(label);
            } else if (i == 1) {
                JLabel label = new JLabel("반자동", SwingConstants.CENTER);
                categoryPanels[i].add(label);
            } else if (i == 2) {
                JLabel label = new JLabel("수동", SwingConstants.CENTER);
                categoryPanels[i].add(label);
            }

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

        // 3. 저장하기
        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel confirmLabel = new JLabel("저장하기", SwingConstants.CENTER);
        confirmLabel.setPreferredSize(new Dimension(400, 50));
        confirmLabel.setOpaque(true);
        confirmLabel.setBackground(Color.WHITE); // 배경색 변경
        confirmLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        confirmLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                printSelectedNumbers();
            }
        });
        confirmPanel.add(confirmLabel);

        // Input 왼쪽 사이드
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new Dimension(400, 500));
        inputPanel.add(categoryPanel, BorderLayout.NORTH);
        inputPanel.add(numberPanel, BorderLayout.CENTER);
        inputPanel.add(confirmPanel, BorderLayout.SOUTH);

        mainPanel.add(inputPanel, BorderLayout.WEST);

        // ----------------- OUTPUT -----------------
        JPanel outputPanel = new JPanel(new GridLayout(5, 9));
        resultLabels = new JLabel[5][9];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                resultLabels[i][j] = new JLabel("", SwingConstants.CENTER); // Initialize with empty text
                resultLabels[i][j].setBackground(Color.WHITE);
                resultLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                outputPanel.add(resultLabels[i][j]);
            }
        }
        mainPanel.add(outputPanel, BorderLayout.EAST);

        setVisible(true);
    }

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

    private void toggleNumber(int row, int col) {
        // If already selected 6 numbers, return
        if (selectedNumbers.size() >= ARRAY_SIZE && !numberCellStates[row][col]) {
            return;
        }

        numberCellStates[row][col] = !numberCellStates[row][col];
        Color bg = numberCellStates[row][col] ? Color.BLACK : Color.WHITE;
        Color fg = numberCellStates[row][col] ? Color.WHITE : Color.BLACK;
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

    private void printSelectedNumbers() {
        if (selectedNumbers.size() != ARRAY_SIZE || selectedIndex == -1) {
            System.out.println("Please select exactly " + ARRAY_SIZE + " numbers and a category.");
            return;
        }

        // Create a map to store selected category and numbers
        Map<String, Object> selectionMap = new HashMap<>();
        if (selectedIndex != -1) {
            String selectedCategory = getCategoryName(selectedIndex);
            selectionMap.put("category", selectedCategory);
        }

        selectionMap.put("numbers", selectedNumbers);

        // Print the selection map
        System.out.println("Selected Category and Numbers:");
        for (Map.Entry<String, Object> entry : selectionMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Purchase();
        });
    }
}
