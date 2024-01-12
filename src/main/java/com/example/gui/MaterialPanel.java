package com.example.gui;

import com.example.models.MatNumberOfUser;
import com.example.models.Material;
import com.example.models.Organization;
import com.example.models.OwnedMaterial;
import com.example.utils.DBUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static com.example.utils.DBUtil.getMatNumbersOfUsersView;
import static com.example.utils.DBUtil.insertToDB;

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
        JPanel panel = new JPanel();

        barcodeLbl = new JLabel("Barcode of the material to be added");
        barcode = new JTextField(5);
        DefaultTableModel dftm = new DefaultTableModel();
        getNumbersOfUsers = new JButton("Get Material Number of Users");
        listAllButton = new JButton("List All Materials");
        listOwnedButton = new JButton("List Owned Materials");
        addButton = new JButton("Add To User");
        matTable = new JTable();

        mnou = getMatNumbersOfUsersView();

        getNumbersOfUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dftm.setColumnCount(0);
                dftm.setRowCount(0);
                if(!mnou.isEmpty()){
                    dftm.addColumn("User SSN");
                    dftm.addColumn("Number Of Mats");
                    for(MatNumberOfUser mno : mnou){
                        dftm.addRow(new Object[]{mno.getSsn(), mno.getNumberOfMats()});
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
                if(!mnou.isEmpty()){
                    dftm.addColumn("Company Id");
                    dftm.addColumn("Type");
                    dftm.addColumn("Barcode");
                    dftm.addColumn("Price");
                    for(Material mat : mats){
                        dftm.addRow(new Object[]{mat.getCompanyId(), mat.getMaterialType(), mat.getBarcode(), mat.getPrice()});
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
                    if(!Arrays.asList(infoArr).isEmpty()) {
                        dftm.addColumn("Type");
                        dftm.addColumn("Barcode");
                        dftm.addColumn("Price");
                        for (Object obj :infoArr) {
                            String bes[] = obj.toString().split(",");
                            dftm.addRow(new Object[]{bes[2], bes[3], bes[4]});
                        }
                        matTable.setModel(dftm);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                double price = DBUtil.getMatPrice(ssn);
                panel.add(new JLabel("Price : "+price));
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barc = barcode.getText();
                for(Material mt: mats){
                    if(mt.getBarcode().equals(barc)){
                        OwnedMaterial own = new OwnedMaterial();
                        own.setMaterialId(mt.getMaterialId());
                        own.setUserSsn(ssn);
                        DBUtil.insertToDB(own);
                    }
                }
            }
        });

        panel.add(getNumbersOfUsers);
        panel.add(listAllButton);
        panel.add(listOwnedButton);
        panel.add(barcodeLbl);
        panel.add(barcode);
        panel.add(addButton);
        panel.add(new JScrollPane(matTable));
        add(panel);
        setVisible(true);
    }
}
