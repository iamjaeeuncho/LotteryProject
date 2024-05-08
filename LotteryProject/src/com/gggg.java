
package com;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class gggg extends JFrame {
    public gggg() {
        // 전체 프레임 설정
        setSize(994, 906);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 상단 패널 설정
        JPanel topPanel = new JPanel();
        topPanel.setSize(1000, 300);
        Color red = null;
		topPanel.setBackground(red);;
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        
        // 하단 패널 설정
        JPanel bottomPanel = new JPanel();
        bottomPanel.setSize(1000, 600);
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());
        getContentPane().add(bottomPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gggg());
    }
}
