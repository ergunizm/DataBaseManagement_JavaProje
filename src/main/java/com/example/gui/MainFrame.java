package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import com.example.gui.components.TopBarPanel;

public abstract class MainFrame extends JFrame {
    JPanel mainPanel;
    JPanel topBarPanel;
    protected JPanel contentPanel;
    JLabel titleLabel;
    String username;

    public MainFrame(String username) {
        this.username = username;

        setIconImage(new ImageIcon("src/resources/images/icons/logo.png").getImage());

        setSize(576 * 2, 576);
        // setResizable(false);
        setUndecorated(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new GridLayout(1, 2));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setOpaque(true);

        JLabel imageLabel = new JLabel(new ImageIcon("src/resources/images/side_pic.png"));

        mainPanel.add(contentPanel, BorderLayout.WEST);
        mainPanel.add(imageLabel, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
