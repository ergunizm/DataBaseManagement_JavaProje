package com.example.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OfferPanel extends JPanel {
    private JTable offerTable;
    private JButton buyButton;

    public OfferPanel() {

        JPanel panel = new JPanel();
        offerTable = new JTable();
        buyButton = new JButton("Buy Selected");

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Seçilen teklifleri satın alma işlemleri
            }
        });

        panel.add(new JScrollPane(offerTable));
        panel.add(buyButton);

        add(panel);
        setVisible(true);
    }
}
