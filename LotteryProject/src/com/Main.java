package com;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JFrame {
    private JPanel currentPanel;
    private Category category;
    private Chat chat;
    private int userNo;

    public Main(int userNo) {
        this.userNo = userNo;
        initializeUI();
    }

    public Main() {
        initializeUI();
    }

    private void initializeUI() {
        category = new Category();
        setTitle("가슴속에 복권 한장");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setLayout(new BorderLayout());
        add("North", category.panel);

        JButton loginBtn = category.login;
        JButton chatBtn = category.chat;

        Lsistener listener = new Lsistener();

        loginBtn.addActionListener(listener);
        chatBtn.addActionListener(listener);

        setVisible(true);
    }

    private class Lsistener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (button == category.login) {
                removeCurrentPanel();
                JPanel panel = new JPanel();
                panel.add(new Login(Main.this));
                add("Center", panel);
                currentPanel = panel;
            } else if (button == category.chat) {
                removeCurrentPanel();
                chat = new Chat(userNo);
                add("Center", chat);
                currentPanel = chat;
            }
            revalidate();
            repaint();
        }
    }

    private void removeCurrentPanel() {
        if (currentPanel != null) {
            remove(currentPanel);
        }
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}