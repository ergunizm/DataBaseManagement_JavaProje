package com.example.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import com.example.utils.PartyUtil;

public class PartySelectionPanel extends JPanel {
    private JComboBox<String> partyTypeComboBox;
    String selectedPartyType;
    private String[] partyTypes;

    public PartySelectionPanel() {
        partyTypes = PartyUtil.getPartyTypes();

        JPanel panel = new JPanel();
        panel.setBackground(Theme.bgColor);

        partyTypeComboBox = new JComboBox<>(partyTypes);
        partyTypeComboBox.setBackground(Theme.bgColor);
        partyTypeComboBox.setFont(Theme.font);
        // set the dropdown list to be editable
        partyTypeComboBox.setEditable(true);

        partyTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected item
                selectedPartyType = (String) partyTypeComboBox.getSelectedItem();
            }
        });

        // add label with white text
        JLabel label = new JLabel("Party Type:");
        label.setForeground(Color.WHITE);

        panel.add(label);
        panel.add(partyTypeComboBox);

        add(panel);
        setVisible(true);

    }
}
