package com.example.gui;

import com.example.models.Material;
import com.example.utils.DBUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteOrganizationFrame extends JFrame {
    JPanel mainPanel;
    private JTextField idField;
    private JButton commitButton;

    public DeleteOrganizationFrame() {
        idField = new JTextField(10);
        commitButton = new JButton("Delete");

        setTitle("Organization Delete Frame");
        setSize(576 * 2, 576);
        setResizable(false);
        setUndecorated(false);

        mainPanel = new JPanel();

        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String deleteResult = DBUtil.deleteOrganization(idField.getText());

                JOptionPane.showMessageDialog(null, deleteResult, "Result", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        mainPanel.add(new JLabel("Id of Organization:"));
        mainPanel.add(idField);

        mainPanel.add(commitButton);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
