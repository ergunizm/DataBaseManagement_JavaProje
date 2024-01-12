package com.example.gui;

import com.example.models.Organization;
import com.example.utils.DBUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddOrgFrame extends JFrame {
    JPanel mainPanel;
    private int id;
    private JTextField typeField;
    private JTextField limitField;
    private JTextField seasonField;
    private JTextField priceField;
    private JTextField dateField;
    private JButton commitButton;

    public AddOrgFrame(String cid, int len) {
        typeField = new JTextField(15);
        limitField = new JTextField(15);
        seasonField = new JTextField(15);
        priceField = new JTextField(15);
        dateField = new JTextField(15);
        commitButton = new JButton("Insert");

        setTitle("Company id-" + cid);
        setSize(576 * 2, 576);
        setResizable(false);
        setUndecorated(false);

        mainPanel = new JPanel();

        id = len;

        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Organization tmp = new Organization();
                tmp.setOrgId(String.valueOf(id));
                tmp.setCompanyId(cid);
                tmp.setOrgType(typeField.getText());
                tmp.setGuestLimit(Integer.parseInt(limitField.getText()));
                tmp.setSeason(seasonField.getText());
                tmp.setOrgDate(Integer.parseInt(dateField.getText()));
                tmp.setPrice(Float.parseFloat(priceField.getText()));
                tmp.setAvailability(true);
                id = id+1;
                DBUtil.insertToDB(tmp);
            }
        });


        mainPanel.add(new JLabel("Type:"));
        mainPanel.add(typeField);
        mainPanel.add(new JLabel("Limit:"));
        mainPanel.add(limitField);
        mainPanel.add(new JLabel("Season:"));
        mainPanel.add(seasonField);
        mainPanel.add(new JLabel("Date:"));
        mainPanel.add(dateField);
        mainPanel.add(new JLabel("Price:"));
        mainPanel.add(priceField);

        mainPanel.add(commitButton);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
