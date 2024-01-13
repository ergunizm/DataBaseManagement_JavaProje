package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.example.services.Authentication;

public class AuthFrame extends JFrame {
    private JTextField ssnField;
    private JTextField fnameField;
    private JTextField lnameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public AuthFrame() {
        setTitle("Partea - Party Organizing System");
        // make the frame full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(Theme.bgColor);
        // Add padding to the panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ssnField = new JTextField(15);
        passwordField = new JPasswordField(15);
        fnameField = new JTextField(15);
        lnameField = new JTextField(15);

        configureTextField(ssnField);
        configureTextField(passwordField);
        configureTextField(fnameField);
        configureTextField(lnameField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        configureButton(loginButton);
        configureButton(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ssnField.getText().isEmpty() || String.valueOf(passwordField.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(AuthFrame.this, "Username and password cannot be empty!");
                    return;
                } else if (ssnField.getText().length() < 3
                        || String.valueOf(passwordField.getPassword()).length() < 3) {
                    JOptionPane.showMessageDialog(AuthFrame.this,
                            "Username and password must be at least 3 characters long!");
                    return;
                } else if (ssnField.getText().length() > 8
                        || String.valueOf(passwordField.getPassword()).length() > 8) {
                    JOptionPane.showMessageDialog(AuthFrame.this,
                            "Username and password must be at most 15 characters long!");
                    return;
                }

                if (Authentication.authenticate(ssnField.getText(), String.valueOf(passwordField.getPassword()))) {
                    dispose();
                    new HomeFrame(ssnField.getText());
                } else {
                    JOptionPane.showMessageDialog(AuthFrame.this, "Invalid username or password!");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ssnField.getText().isEmpty() || String.valueOf(passwordField.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(AuthFrame.this, "Username and password cannot be empty!");
                    return;
                } else if (ssnField.getText().length() < 3
                        || String.valueOf(passwordField.getPassword()).length() < 3) {
                    JOptionPane.showMessageDialog(AuthFrame.this,
                            "Username and password must be at least 3 characters long!");
                    return;
                } else if (ssnField.getText().length() > 8
                        || String.valueOf(passwordField.getPassword()).length() > 8) {
                    JOptionPane.showMessageDialog(AuthFrame.this,
                            "Username and password must be at most 15 characters long!");
                    return;
                }

                try {
                    Authentication.register(ssnField.getText(), fnameField.getText(), lnameField.getText(),
                            String.valueOf(passwordField.getPassword()));
                    JOptionPane.showMessageDialog(AuthFrame.this, "Registration successful!");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(AuthFrame.this, exception.getMessage());
                }
            }
        });

        panel.add(configureLabel(new JLabel("Username:")));
        panel.add(ssnField);
        panel.add(configureLabel(new JLabel("Password:")));
        panel.add(passwordField);
        panel.add(configureLabel(new JLabel("FirstName:(For registry)")));
        panel.add(fnameField);
        panel.add(configureLabel(new JLabel("LastName:(For registry)")));
        panel.add(lnameField);
        panel.add(loginButton);
        panel.add(registerButton);

        // Center align the panel within the frame
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void configureButton(JButton button) {
        button.setFont(Theme.font);
        button.setBackground(Theme.buttonColor);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding to the button
        button.setForeground(Color.white);
        button.setPreferredSize(new Dimension(200, 40));
    }

    private JLabel configureLabel(JLabel label) {
        label.setFont(Theme.font);
        label.setForeground(Color.white);
        label.setBackground(Theme.bgColor);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(100, 40));
        // Add padding to the label
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        return label;
    }

    private void configureTextField(JTextField textField) {
        textField.setFont(Theme.font);
        textField.setBackground(Theme.buttonColor);
        textField.setOpaque(true);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding to the text field
        textField.setForeground(Color.white);
        textField.setCaretColor(Color.white);
        textField.setPreferredSize(new Dimension(200, 40));
    }

    // --------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        new AuthFrame();
    }
}
