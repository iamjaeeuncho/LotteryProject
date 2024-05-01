package com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Purchase extends JFrame {
    private JLabel[][] categoryLabels;
    private JLabel[][] resultLabels;
    
    private int resultRowIndex = 0;
    private int resultColumnIndex = 0;
    private int clickCount = 0;
    private boolean categoryselected = false;
    
    private String[] options = {"자동", "반자동", "수동"};

    public Purchase() {
        setTitle("Purchase");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // ----------------- INPUT -----------------
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setPreferredSize(new Dimension(400, 500));
        
        // 카테고리: 자동, 반자동, 수동
        JPanel categoryPanel = new JPanel(new GridLayout(1, 3));
        categoryPanel.setPreferredSize(new Dimension(400, 50));
        
        categoryLabels = new JLabel[1][3];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                categoryLabels[i][j] = new JLabel(options[j], SwingConstants.CENTER);
                categoryLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                categoryLabels[i][j].addMouseListener(new SelectCategory());
                categoryPanel.add(categoryLabels[i][j]);
            }
        }
        
        // 숫자 6자리 입력
        JPanel numberPanel = new JPanel(new GridLayout(7, 7));
        numberPanel.setPreferredSize(new Dimension(400, 400));
        for (int i = 1; i < 46; i++) {
            JLabel number = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            number.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            number.addMouseListener(new SelectNumber());
            numberPanel.add(number);
        }
        
        inputPanel.add(categoryPanel, BorderLayout.NORTH);
        inputPanel.add(numberPanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.WEST);
        
        // ----------------- OUTPUT -----------------
        JPanel outputPanel = new JPanel(new GridLayout(5, 9));
        resultLabels = new JLabel[5][9];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                resultLabels[i][j] = new JLabel("", SwingConstants.CENTER); // Initialize with empty text
                resultLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                outputPanel.add(resultLabels[i][j]);
            }
        }
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    class SelectCategory extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel clickedLabel = (JLabel) e.getSource();
            String labelText = clickedLabel.getText();
            
            resultLabels[resultRowIndex][resultColumnIndex].setText(labelText);
            
            categoryselected = true;
            clickCount++;
        }
    }

    class SelectNumber extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (categoryselected == true) {
            	JLabel clickedLabel = (JLabel) e.getSource();
                String numberText = clickedLabel.getText();
               
                resultLabels[resultRowIndex][resultColumnIndex + 1].setText(numberText);
                
                resultColumnIndex++;
                clickCount++;
            }
            
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Purchase();
        });
    }
}
