package com.example.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Organization;
import com.example.utils.DBUtil;

public class PartySelectionPanel extends JPanel {
    private String user_ssn;
    private JTable orgTable;
    private JComboBox<String> partyTypeComboBox;
    private JComboBox<String> seasonComboBox;
    String selectedPartyType;
    private String[] orgTypes;
    private List<Organization> orgs;
    private JButton addOrgButton;

    public PartySelectionPanel(String ssn) {
        orgTypes = DBUtil.getOrgTypes();
        this.user_ssn = ssn;

        orgTable = new JTable();
        DefaultTableModel dftm = new DefaultTableModel();

        addOrgButton = new JButton("Add Party");

        // with layout of vertically
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Theme.bgColor);

        partyTypeComboBox = new JComboBox<>(orgTypes);
        partyTypeComboBox.setBackground(Theme.bgColor);
        partyTypeComboBox.setFont(Theme.font);
        partyTypeComboBox.setEditable(true);

        String[] seasons = { "winter", "spring", "summer", "fall" };

        seasonComboBox = new JComboBox<>(seasons);
        seasonComboBox.setBackground(Theme.bgColor);
        seasonComboBox.setFont(Theme.font);
        seasonComboBox.setEditable(true);

        orgs = new ArrayList<>();

        partyTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedPartyType = (String) partyTypeComboBox.getSelectedItem();

                orgs = DBUtil.selectSeasonTypeIntersect((String) seasonComboBox.getSelectedItem(), selectedPartyType);

                if (!orgs.isEmpty()) {
                    dftm.setColumnCount(0);
                    dftm.setRowCount(0);
                    dftm.addColumn("ID");
                    dftm.addColumn("Type");
                    dftm.addColumn("Limit");
                    dftm.addColumn("Season");
                    dftm.addColumn("Availability");
                    dftm.addColumn("Date");
                    for (Organization org : orgs) {
                        dftm.addRow(new Object[] { org.getOrgId(), org.getOrgType(), org.getGuestLimit(),
                                org.getSeason(), org.getAvailability(), org.getOrgDate() });
                    }

                    orgTable.setModel(dftm);
                }
            }
        });

        addOrgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orgTable.getSelectedRow();

                if (selectedRow != -1) {
                    int selectedId = (int) orgTable.getValueAt(selectedRow, 0);

                    String result = DBUtil.addOrgToUser(user_ssn, selectedId);

                    JOptionPane.showMessageDialog(null, result, "Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JLabel label = new JLabel("Party Type:");
        label.setForeground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridy = 1;
        panel.add(seasonComboBox, gbc);

        gbc.gridy = 2;
        panel.add(partyTypeComboBox, gbc);

        gbc.gridy = 3;
        panel.add(addOrgButton, gbc);

        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(orgTable), gbc);

        add(panel);
        setVisible(true);

    }
}