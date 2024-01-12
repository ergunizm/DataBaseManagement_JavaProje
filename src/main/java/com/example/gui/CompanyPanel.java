package com.example.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.example.models.Organization;
import com.example.utils.DBUtil;

public class CompanyPanel extends JPanel {
        private JTable orgTable;
        private JTextField cidField;
        private JButton getButton;
        private JButton addButton;
        private List<Organization> orgs;

    public CompanyPanel() {
        JPanel panel = new JPanel();
        DefaultTableModel dftm = new DefaultTableModel();
        getButton = new JButton("Get All");
        addButton = new JButton("Add organization");
        orgTable = new JTable();
        cidField = new JTextField(9);

        orgs = DBUtil.selectOrgOfCompany(cidField.getText());

        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!orgs.isEmpty()){
                    dftm.setColumnCount(0);
                    dftm.setRowCount(0);
                    dftm.addColumn("Type");
                    dftm.addColumn("Limit");
                    dftm.addColumn("Season");
                    dftm.addColumn("Availability");
                    dftm.addColumn("Date");
                    for(Organization org : orgs){
                        dftm.addRow(new Object[]{org.getOrgType(), org.getGuestLimit(), org.getSeason(), org.getAvailability(), org.getOrgDate()});
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

        panel.add(cidField);
        panel.add(getButton);
        panel.add(new JScrollPane(orgTable));
        panel.add(addButton);
        add(panel);
        setVisible(true);
    }
}
