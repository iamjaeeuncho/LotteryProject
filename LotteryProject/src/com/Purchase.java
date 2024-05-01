package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class Purchase {
    private JLabel selectedCategoryLabel;
    private JLabel category;

    public Purchase() {
        JFrame frame = new JFrame("Purchase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ----------------- INPUT -----------------
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new Dimension(400, 500));

        JPanel categoryPanel = new JPanel(new GridLayout(1, 3));
        categoryPanel.setPreferredSize(new Dimension(400, 50));
        
        Map<String, String> categories = new HashMap<>();
        categories.put("categoryAuto", "자동");
        categories.put("categoryHalfAuto", "반자동");
        categories.put("categoryManual", "수동");

        for (String key : categories.keySet()) {
            String value = categories.get(key);

            JLabel category = new JLabel(value, SwingConstants.CENTER);
            category.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 추가
            category.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedCategoryLabel != null) {
                        selectedCategoryLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 선택 해제
                    }
                    JLabel selectedLabel = (JLabel) e.getSource();
                    String value = (selectedLabel.getText());
//                    selectCategory(value);
//                    category = value;

                    selectedLabel.setBorder(BorderFactory.createLineBorder(Color.RED)); // 선택 표시
                    selectedCategoryLabel = selectedLabel; // 현재 선택된 카테고리로 설정
                }
            });
            categoryPanel.add(category);
        }

        JPanel numberPanel = new JPanel(new GridLayout(7, 7));
        numberPanel.setPreferredSize(new Dimension(400, 400));
        for (int i = 1; i < 46; i++) {
            JLabel number = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            number.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 추가
            number.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String value = ((JLabel) e.getSource()).getText();
                    selectNumber(value);
                }
            });
            numberPanel.add(number);
        }

        JPanel registerPanel = new JPanel(new GridLayout(1, 3));
        registerPanel.setPreferredSize(new Dimension(400, 50));
        JLabel labelQuantMenu = new JLabel("수량", SwingConstants.CENTER);
        labelQuantMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 추가
        JLabel labelQuantity = new JLabel("1", SwingConstants.CENTER);
        labelQuantity.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 추가
        JLabel labelRegister = new JLabel("확인", SwingConstants.CENTER);
        labelRegister.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 추가
        labelRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String labelQuantity = ((JLabel) e.getSource()).getText();
//                selectCategory(labelQuantity);
            }
        });
        registerPanel.add(labelQuantMenu);
        registerPanel.add(labelQuantity);
        registerPanel.add(labelRegister);

        inputPanel.add(categoryPanel, BorderLayout.NORTH);
        inputPanel.add(numberPanel, BorderLayout.CENTER);
        inputPanel.add(registerPanel, BorderLayout.SOUTH);

        frame.add(inputPanel, BorderLayout.CENTER);

        // ----------------- OUTPUT -----------------
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setPreferredSize(new Dimension(400, 500));

        JLabel menu = new JLabel("선택 번호 확인", SwingConstants.CENTER);
        menu.setPreferredSize(new Dimension(400, 50));
        menu.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 추가

        JPanel resultTable = new JPanel(new GridLayout(5, 4));
        resultTable.setPreferredSize(new Dimension(400, 400));

        // 라벨 배열 생성
        JLabel[][] labels = new JLabel[5][4];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                int labelNumber = i * 4 + j + 1; // 라벨의 번호 계산
                if (labelNumber % 4 == 1) {
                    labels[i][j] = new JLabel("카테고리", SwingConstants.CENTER);
                    resultTable.add(labels[i][j]);
                } else if (labelNumber % 4 == 2) {
                    labels[i][j] = new JLabel("번호", SwingConstants.CENTER);
                    resultTable.add(labels[i][j]);
                } else if (labelNumber % 4 == 3) {
                    labels[i][j] = new JLabel("수정", SwingConstants.CENTER);
                    resultTable.add(labels[i][j]);
                } else if (labelNumber % 4 == 0) {
                    labels[i][j] = new JLabel("삭제", SwingConstants.CENTER);
                    resultTable.add(labels[i][j]);
                } 
            }
        }
        
        outputPanel.add(menu, BorderLayout.NORTH);
        outputPanel.add(resultTable, BorderLayout.CENTER);

        frame.add(outputPanel, BorderLayout.EAST);
        frame.setSize(800, 500); // 프레임 크기 설정
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

//    private static void selectCategory(String category) {
//        System.out.println(category);
//    }

    private static void selectNumber(String number) {
        System.out.println("number" + number);
    }

    public static void main(String[] args) {
    	Purchase purchase = new Purchase();
    }
}
