package com;

import javax.swing.*;

import com.purchase.Category;
import com.purchase.Number;
import com.purchase.Register;

public class Purchase {
	public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Purchase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create a JPanel to hold the panels for purchasing
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        
        // Create instances of Category, Number, and Register panels
        Category categoryPanel = new Category();
        Number numberPanel = new Number();
        Register registerPanel = new Register();
        
        // Add Category, Number, and Register panels to the panel1
        panel1.add(categoryPanel);
        panel1.add(numberPanel);
        panel1.add(registerPanel);
        
        // Add panel1 to the frame
        frame.add(panel1);
        
        // Set frame size and make it visible
        frame.setSize(800, 600);
        frame.setVisible(true);
	}
}
