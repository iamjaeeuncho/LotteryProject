package com.purchase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Number extends JPanel {
    public Number() {
        setLayout(new GridLayout(7, 7)); // Set layout to 7x7 GridLayout

        // Add components to the panel
        for (int i = 1; i < 7 * 7; i++) {
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
            Number panel = new Number();
            frame.add(panel); // Add SevenBySevenPanel to the frame
            frame.setVisible(true);
        });
    }
}
