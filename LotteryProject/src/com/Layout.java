package com;

import javax.swing.*;
import java.awt.*;

public class Layout {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("JTabbedPane Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);

        // Create a JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Create panels for each tab
        JPanel panel1 = new JPanel();
        SignIn signUpPanel = new SignIn();
        panel1.add(signUpPanel);
        
        JPanel panel2 = new JPanel();
        PurchaseNumber purchaseNumberPanel = new PurchaseNumber();
        panel2.add(purchaseNumberPanel);
        
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.BLUE);
        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.YELLOW);

        // Add panels to the tabbed pane with titles
        tabbedPane.addTab("메인", panel1);
        tabbedPane.addTab("복권구매", panel2);
        tabbedPane.addTab("구매내역", panel3);
        tabbedPane.addTab("채팅", panel4);

        // Add the tabbed pane to the frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }
}

