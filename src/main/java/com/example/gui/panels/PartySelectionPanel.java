package com.example.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import com.example.gui.Theme;
import com.example.models.Organization;
import com.example.utils.DBUtil;

public class PartySelectionPanel extends JPanel {
    private String user_ssn;
    private JTable partyTable;
    private JComboBox<String> partyTypeComboBox;
    private JComboBox<String> seasonComboBox;
    String selectedPartyType;
    private String[] orgTypes;
    private List<Organization> orgs;
    private JButton addPartyButton;

    public PartySelectionPanel(String ssn) {

        orgTypes = DBUtil.getOrgTypes();
        this.user_ssn = ssn;

        partyTable = new JTable();

        // Create table size
        int tableWidth = 530;
        int tableHeight = 430;

        // Create table size
        java.awt.Dimension tableSize = new java.awt.Dimension(tableWidth, tableHeight);

        partyTable.setPreferredSize(tableSize);
        partyTable.setPreferredScrollableViewportSize(tableSize);
        partyTable.setFillsViewportHeight(true);
        partyTable.setSize(tableSize);

        DefaultTableModel dftm = new DefaultTableModel();

        addPartyButton = new JButton("Add Party");
        addPartyButton.setFont(Theme.font);

        // with layout of vertically
        JPanel panel = new JPanel(new GridBagLayout());

        partyTypeComboBox = new JComboBox<>(orgTypes);
        partyTypeComboBox.setBackground(Color.WHITE);
        partyTypeComboBox.setFont(Theme.font);
        partyTypeComboBox.setEditable(true);

        String[] seasons = { "winter", "spring", "summer", "fall" };

        seasonComboBox = new JComboBox<>(seasons);
        seasonComboBox.setBackground(Color.WHITE);
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

                    partyTable.setModel(dftm);
                }
            }
        });

        addPartyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = partyTable.getSelectedRow();

                if (selectedRow != -1) {
                    int selectedId = (int) partyTable.getValueAt(selectedRow, 0);

                    String result = DBUtil.addOrgToUser(user_ssn, selectedId);

                    JOptionPane.showMessageDialog(null, result, "Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JLabel label = new JLabel("Party Type:");
        label.setFont(Theme.font);
        label.setForeground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        // Set gap between components
        gbc.ipadx = 15;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(partyTypeComboBox, gbc);

        gbc.gridx = 2;

        JLabel seasonLabel = new JLabel("Season:");
        seasonLabel.setFont(Theme.font);
        panel.add(seasonLabel, gbc);

        gbc.gridx = 3;
        panel.add(seasonComboBox, gbc);

        gbc.gridx = 4;
        panel.add(addPartyButton, gbc);

        gbc.gridwidth = 5;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 1;

        panel.add(new JScrollPane(partyTable), gbc);

        add(panel);
        setVisible(true);

    }
}