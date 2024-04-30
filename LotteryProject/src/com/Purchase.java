package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Purchase {
    
    public Purchase() {
        
        JFrame frame = new JFrame("Number Grid Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 3x1 그리드 레이아웃의 패널 생성
        JPanel categoryPanel = new JPanel(new GridLayout(1,3));
        JLabel categoryAuto = new JLabel("자동", SwingConstants.CENTER);
        JLabel categoryHalfAuto = new JLabel("반자동", SwingConstants.CENTER);
        JLabel categoryManual = new JLabel("수동", SwingConstants.CENTER);
        
        categoryPanel.add(categoryAuto);
        categoryPanel.add(categoryHalfAuto);
        categoryPanel.add(categoryManual);

        // 7x7 그리드 레이아웃의 패널 생성
        JPanel numberPanel = new JPanel(new GridLayout(7, 7));

        // 패널에 각 셀에 해당하는 JLabel들을 추가
        for (int i = 1; i < 46; i++) {
            JLabel number = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            number.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // 테두리 추가
            number.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 해당 라벨(셀)의 값을 가져와 다른 페이지로 이동하는 코드 작성
                    String value = ((JLabel) e.getSource()).getText();
                    navigateToOtherPage(value);
                }
            });
            numberPanel.add(number);
        }
        
        // 3x1 그리드 레이아웃의 패널 생성
        JPanel registerPanel = new JPanel(new GridLayout(1,3));
        JLabel labelQuantMenu = new JLabel("수량", SwingConstants.CENTER);
        JLabel labelQuantity = new JLabel("1", SwingConstants.CENTER);
        JLabel labelRegister = new JLabel("확인", SwingConstants.CENTER);
        
        registerPanel.add(labelQuantMenu);
        registerPanel.add(labelQuantity);
        registerPanel.add(labelRegister);

        // 프레임에 패널들 추가
        frame.getContentPane().add(categoryPanel, BorderLayout.NORTH);
        frame.getContentPane().add(numberPanel, BorderLayout.CENTER);
        frame.getContentPane().add(registerPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null); // 화면 가운데 정렬
        frame.setVisible(true);
    }

    // 다른 페이지로 이동하는 메서드
    private static void navigateToOtherPage(String value) {
        JFrame newFrame = new JFrame("New Page");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel("You clicked on value: " + value, SwingConstants.CENTER);
        newFrame.getContentPane().add(label);
        newFrame.pack();
        newFrame.setLocationRelativeTo(null); // 화면 가운데 정렬
        newFrame.setVisible(true);
    }
    
    // 테스트용
    public static void main(String[] args) {
        Purchase purchase = new Purchase();
    }
}
