package com.example.gui.panels;

import com.example.models.MatNumberOfUser;
import com.example.models.Material;
import com.example.models.OwnedMaterial;
import com.example.utils.DBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import static com.example.utils.DBUtil.getMatNumbersOfUsersView;

public class MaterialPanel extends JPanel {
    private String ssn;
    private JTextField barcode;
    private JLabel barcodeLbl;
    private JTable matTable;
    private JButton listOwnedButton;
    private JButton listAllButton;
    private JButton addButton;
    private JButton getNumbersOfUsers;
    private List<Material> mats;
    private List<MatNumberOfUser> mnou;

    public MaterialPanel(String ssn_get) {
        this.ssn = ssn_get;
        JPanel panel = new JPanel(new GridBagLayout());

        barcodeLbl = new JLabel("Barcode of the material to be added");
        barcode = new JTextField(5);
        DefaultTableModel dftm = new DefaultTableModel();
        getNumbersOfUsers = new JButton("Get Material Number of Users");
        listAllButton = new JButton("List All Materials");
        listOwnedButton = new JButton("List Owned Materials");
        addButton = new JButton("Add To User");

        matTable = new JTable();

        // Create table size
        int tableWidth = 530;
        int tableHeight = 370;

        // Create table size
        java.awt.Dimension tableSize = new java.awt.Dimension(tableWidth, tableHeight);

        matTable.setPreferredSize(tableSize);
        matTable.setPreferredScrollableViewportSize(tableSize);
        matTable.setFillsViewportHeight(true);
        matTable.setSize(tableSize);

        mnou = getMatNumbersOfUsersView();

        getNumbersOfUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dftm.setColumnCount(0);
                dftm.setRowCount(0);
                if (!mnou.isEmpty()) {
                    dftm.addColumn("User SSN");
                    dftm.addColumn("Number Of Mats");
                    for (MatNumberOfUser mno : mnou) {
                        dftm.addRow(new Object[] { mno.getSsn(), mno.getNumberOfMats() });
                    }

                    matTable.setModel(dftm);
                }
            }
        });

        listAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mats = DBUtil.selectAllFromDB("materials");

                dftm.setColumnCount(0);
                dftm.setRowCount(0);
                if (!mnou.isEmpty()) {
                    dftm.addColumn("Company Id");
                    dftm.addColumn("Type");
                    dftm.addColumn("Barcode");
                    dftm.addColumn("Price");
                    for (Material mat : mats) {
                        dftm.addRow(new Object[] { mat.getCompanyId(), mat.getMaterialType(), mat.getBarcode(),
                                mat.getPrice() });
                    }

                    matTable.setModel(dftm);
                }
            }
        });

        listOwnedButton.addActionListener(new ActionListener() {
            Object[] infoArr;

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    infoArr = (Object[]) DBUtil.getOwnedMaterials(ssn).getArray();
                    dftm.setColumnCount(0);
                    dftm.setRowCount(0);
                    if (!Arrays.asList(infoArr).isEmpty()) {
                        dftm.addColumn("Type");
                        dftm.addColumn("Barcode");
                        dftm.addColumn("Price");
                        for (Object obj : infoArr) {
                            String bes[] = obj.toString().split(",");
                            dftm.addRow(new Object[] { bes[2], bes[3], bes[4] });
                        }
                        matTable.setModel(dftm);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                double price = DBUtil.getMatPrice(ssn);
                panel.add(new JLabel("Price : " + price));
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barc = barcode.getText();
                for (Material mt : mats) {
                    if (mt.getBarcode().equals(barc)) {
                        OwnedMaterial own = new OwnedMaterial();
                        own.setMaterialId(mt.getMaterialId());
                        own.setUserSsn(ssn);
                        DBUtil.insertToDB(own);
                    }
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(barcodeLbl, gbc);

        gbc.gridx = 1;
        panel.add(barcode, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(addButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(listOwnedButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(listAllButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(getNumbersOfUsers, gbc);

        gbc.gridwidth = 6;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JScrollPane(matTable), gbc);

        add(panel);
        setVisible(true);
    }
}
