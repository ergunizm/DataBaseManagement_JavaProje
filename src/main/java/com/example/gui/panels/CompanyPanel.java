package com.example.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.example.gui.Theme;
import com.example.gui.frames.AddOrgFrame;
import com.example.models.Organization;
import com.example.utils.DBUtil;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class CompanyPanel extends JPanel {
    private JTable orgTable;
    private JTextField cidField;
    private JButton getButton;
    private JButton addButton;
    private List<Organization> orgs;

    public CompanyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        DefaultTableModel dftm = new DefaultTableModel();

        getButton = new JButton("Get All");
        getButton.setFont(Theme.font);

        addButton = new JButton("Add organization");
        addButton.setFont(Theme.font);

        orgTable = new JTable();

        // Create table size
        int tableWidth = 530;
        int tableHeight = 430;

        // Create table size
        java.awt.Dimension tableSize = new java.awt.Dimension(tableWidth, tableHeight);

        orgTable.setPreferredSize(tableSize);
        orgTable.setPreferredScrollableViewportSize(tableSize);
        orgTable.setFillsViewportHeight(true);
        orgTable.setSize(tableSize);

        cidField = new JTextField(9);
        cidField.setFont(Theme.font);

        orgs = DBUtil.selectOrgOfCompany(cidField.getText());

        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!orgs.isEmpty()) {
                    dftm.setColumnCount(0);
                    dftm.setRowCount(0);
                    dftm.addColumn("Type");
                    dftm.addColumn("Limit");
                    dftm.addColumn("Season");
                    dftm.addColumn("Availability");
                    dftm.addColumn("Date");
                    for (Organization org : orgs) {
                        dftm.addRow(new Object[] { org.getOrgType(), org.getGuestLimit(), org.getSeason(),
                                org.getAvailability(), org.getOrgDate() });
                    }

                    orgTable.setModel(dftm);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrgFrame addFrame = new AddOrgFrame(cidField.getText());
                addFrame.setVisible(true);
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel cidLabel = new JLabel("Company ID:");
        cidLabel.setFont(Theme.font);

        panel.add(cidLabel, gbc);

        gbc.gridx = 1;
        panel.add(cidField, gbc);

        gbc.gridx = 2;
        panel.add(getButton, gbc);

        gbc.gridx = 3;
        panel.add(addButton, gbc);

        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(orgTable, gbc);

        add(panel);
        setVisible(true);
    }
}
