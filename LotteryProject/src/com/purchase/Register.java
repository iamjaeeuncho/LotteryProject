package com.purchase;

import javax.swing.*;
import java.awt.*;

public class Register extends JPanel {
    public Register() {
        setLayout(new GridLayout(1, 3)); // Set layout to 1x3 GridLayout

        // Add components to the panel
        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");
        JButton button3 = new JButton("Button 3");

        add(button1);
        add(button2);
        add(button3);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a JFrame to hold the OneByThreePanel
            JFrame frame = new JFrame("1x3 Panel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);
            Category panel = new Category();
            frame.add(panel); // Add OneByThreePanel to the frame
            frame.setVisible(true);
        });
    }
}
