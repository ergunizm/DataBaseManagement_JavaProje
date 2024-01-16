package com.example.gui.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.example.gui.Theme;
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
        setUndecorated(false);
        setSize(600, 350);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBackground(Theme.bgColor);
        // Add padding to the panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ssnField = new JTextField(5);
        passwordField = new JPasswordField(5);
        fnameField = new JTextField(5);
        lnameField = new JTextField(5);

        passwordField.setEchoChar('*');

        configureTextField(ssnField);
        configureTextField(passwordField);
        configureTextField(fnameField);
        configureTextField(lnameField);

        ImageIcon loginIcon = new ImageIcon("src/resources/images/icons/login.png", "Login");
        Image loginImage = loginIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        loginButton = new JButton("Login", new ImageIcon(loginImage));

        ImageIcon registerIcon = new ImageIcon("src/resources/images/icons/register.png", "Register");
        Image registerImage = registerIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        registerButton = new JButton("Register", new ImageIcon(registerImage));

        configureButton(loginButton);
        configureButton(registerButton);
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
                            "Username and password must be at most 8 characters long!");
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
                            "Username and password must be at most 8 characters long!");
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
        panel.add(configureLabel(new JLabel("FirstName (for registiration):")));
        panel.add(fnameField);
        panel.add(configureLabel(new JLabel("LastName (for registiration):")));
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

    // #region UI Configurations for the components
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
    // #endregion

    // --------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        new AuthFrame();
    }
}
