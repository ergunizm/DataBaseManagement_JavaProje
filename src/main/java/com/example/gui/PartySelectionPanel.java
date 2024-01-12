package com.example.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
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

        JPanel panel = new JPanel();
        panel.setBackground(Theme.bgColor);

        partyTypeComboBox = new JComboBox<>(orgTypes);
        partyTypeComboBox.setBackground(Theme.bgColor);
        partyTypeComboBox.setFont(Theme.font);
        partyTypeComboBox.setEditable(true);

        String[] seasons = {"winter","spring","summer","fall"} ;

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

                if(!orgs.isEmpty()){
                    dftm.setColumnCount(0);
                    dftm.setRowCount(0);
                    dftm.addColumn("ID");
                    dftm.addColumn("Type");
                    dftm.addColumn("Limit");
                    dftm.addColumn("Season");
                    dftm.addColumn("Availability");
                    dftm.addColumn("Date");
                    for(Organization org : orgs){
                        dftm.addRow(new Object[]{org.getOrgId(), org.getOrgType(), org.getGuestLimit(), org.getSeason(), org.getAvailability(), org.getOrgDate()});
                    }

                    orgTable.setModel(dftm);
                }
            }
        });

        addOrgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orgTable.getSelectedRow();

                if(selectedRow != -1){
                    int selectedId = (int) orgTable.getValueAt(selectedRow,0);

                    String result = DBUtil.addOrgToUser(user_ssn, selectedId);

                    JOptionPane.showMessageDialog(null, result, "Result", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JLabel label = new JLabel("Party Type:");
        label.setForeground(Color.WHITE);

        panel.add(label);
        panel.add(seasonComboBox);
        panel.add(partyTypeComboBox);
        panel.add(addOrgButton);
        panel.add(new JScrollPane(orgTable));


        add(panel);
        setVisible(true);

    }
}