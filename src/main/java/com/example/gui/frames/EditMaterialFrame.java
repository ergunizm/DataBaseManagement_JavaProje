package com.example.gui.frames;

import com.example.models.Material;
import com.example.models.Organization;
import com.example.utils.DBUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMaterialFrame extends JFrame {
    JPanel mainPanel;
    private JTextField idField;
    private JTextField newidField;
    private JTextField compIdField;
    private JTextField typeField;
    private JTextField barcodeField;
    private JTextField priceField;
    private JButton commitButton;

    public EditMaterialFrame() {
        idField = new JTextField(15);
        newidField = new JTextField(15);
        compIdField = new JTextField(15);
        typeField = new JTextField(15);
        barcodeField = new JTextField(15);
        priceField = new JTextField(15);
        commitButton = new JButton("Update");

        setTitle("Material Edit Frame");
        setSize(576 * 2, 576);
        setResizable(false);
        setUndecorated(false);

        mainPanel = new JPanel();

        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Material tmp = new Material();
                tmp.setMaterialId(newidField.getText());
                tmp.setCompanyId(compIdField.getText());
                tmp.setMaterialType(typeField.getText());
                tmp.setBarcode(barcodeField.getText());
                tmp.setPrice(Float.parseFloat(priceField.getText()));
                String trigResult = DBUtil.updateMaterial(idField.getText(), tmp);

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
        mainPanel.add(new JLabel("Barcode:"));
        mainPanel.add(barcodeField);
        mainPanel.add(new JLabel("Price:"));
        mainPanel.add(priceField);

        mainPanel.add(commitButton);

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
