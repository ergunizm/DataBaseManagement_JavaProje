package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class MainFrame extends JFrame {
    JPanel mainPanel;
    JPanel contentPanel;
    String ssn;

    public MainFrame(String ssn) {
        this.ssn = ssn;

        setTitle("Partea - Party Organizing System #" + ssn);
        setSize(576 * 2, 576);
        setResizable(false);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.black);
        contentPanel.setOpaque(true);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
