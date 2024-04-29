package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignIn extends JPanel implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton SignInButton;
    private JButton SignUpButton;

    public SignIn() {
        // JPanel initialization
        setLayout(new GridLayout(3, 2)); // Set layout for the JPanel

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        SignInButton = new JButton("Sign In");
        SignInButton.addActionListener(this);
        add(SignInButton);
        
        SignUpButton = new JButton("Sign Up");
        SignUpButton.addActionListener(this);
        add(SignUpButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SignInButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Here you can add code to handle the registration process
            // For now, let's just print the input values
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignIn form = new SignIn();
            // Create a JFrame to hold the SignUp panel
            JFrame frame = new JFrame("User Registration Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 150);
            frame.add(form); // Add SignUp panel to the frame
            frame.setVisible(true);
        });
    }
}
