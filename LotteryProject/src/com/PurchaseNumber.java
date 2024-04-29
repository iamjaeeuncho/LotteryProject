package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseNumber extends JPanel {
    public PurchaseNumber() {
        setLayout(new GridLayout(7, 7)); // Set layout to 7x7 GridLayout

        // Add components to the panel
        for (int i = 0; i < 7 * 7; i++) {
        	if (i < 46) {
        		JButton button = new JButton(String.valueOf(i));
                add(button);
        	}
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a JFrame to hold the SevenBySevenPanel
            JFrame frame = new JFrame("7x7 Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            PurchaseNumber panel = new PurchaseNumber();
            frame.add(panel); // Add SevenBySevenPanel to the frame
            frame.setVisible(true);
        });
    }
}
