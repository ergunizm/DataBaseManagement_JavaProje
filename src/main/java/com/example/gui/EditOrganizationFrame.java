package com.example.gui;

import com.example.models.Material;
import com.example.models.Organization;
import com.example.utils.DBUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditOrganizationFrame extends JFrame {
    JPanel mainPanel;
    private JTextField idField;
    private JTextField newidField;
    private JTextField compIdField;
    private JTextField typeField;
    private JTextField limitField;
    private JTextField seasonField;
    private JTextField priceField;
    private JTextField orgDateField;
    private JButton commitButton;

    public EditOrganizationFrame() {
        idField = new JTextField(15);
        newidField = new JTextField(15);
        compIdField = new JTextField(15);
        typeField = new JTextField(15);
        limitField = new JTextField(15);
        seasonField = new JTextField(15);
        priceField = new JTextField(15);
        orgDateField = new JTextField(15);
        commitButton = new JButton("Update");

        setTitle("Organization Edit Frame");
        setSize(576 * 2, 576);
        setResizable(false);
        setUndecorated(false);

        mainPanel = new JPanel();

        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Organization tmp = new Organization();
                tmp.setOrgId(Integer.parseInt(newidField.getText()));
                tmp.setCompanyId(compIdField.getText());
                tmp.setOrgType(typeField.getText());
                tmp.setGuestLimit(Integer.parseInt(limitField.getText()));
                tmp.setSeason(seasonField.getText());
                tmp.setPrice(Float.parseFloat(priceField.getText()));
                tmp.setOrgDate(Integer.parseInt(orgDateField.getText()));
                tmp.setAvailability(true);
                String trigResult =  DBUtil.updateOrganization(idField.getText(), tmp);

                JOptionPane.showMessageDialog(null, trigResult, "Result", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        mainPanel.add(new JLabel("Id of Material to Changed:"));
        mainPanel.add(idField);
        mainPanel.add(new JLabel("New Id:"));
        mainPanel.add(newidField);
        mainPanel.add(new JLabel("Company Id:"));
        mainPanel.add(compIdField);
        mainPanel.add(new JLabel("Type:"));
        mainPanel.add(typeField);
        mainPanel.add(new JLabel("Guest Limit:"));
        mainPanel.add(limitField);
        mainPanel.add(new JLabel("Season:"));
        mainPanel.add(seasonField);
        mainPanel.add(new JLabel("Price:"));
        mainPanel.add(priceField);
        mainPanel.add(new JLabel("Organization Date:"));
        mainPanel.add(orgDateField);

        mainPanel.add(commitButton);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
