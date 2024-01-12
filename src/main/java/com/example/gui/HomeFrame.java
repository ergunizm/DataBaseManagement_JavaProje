package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends MainFrame {
    public HomeFrame(String ssn) {
        super(ssn);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));

        // #region Tabbed pane ---------------------------------------------
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(Theme.font);
        tabbedPane.setForeground(Color.white);
        tabbedPane.setBackground(Theme.bgColor);
        tabbedPane.setOpaque(true);
        tabbedPane.setPreferredSize(new Dimension(576, 576));

        tabbedPane.addTab("Party Selection", new PartySelectionPanel(ssn));
        tabbedPane.addTab("CompanyLogin", new CompanyPanel());
        tabbedPane.addTab("Material", new MaterialPanel(ssn));
        tabbedPane.addTab("Logout", null);

        tabbedPane.setBackgroundAt(0, Theme.buttonColor);
        tabbedPane.setBackgroundAt(1, Theme.buttonColor);
        tabbedPane.setBackgroundAt(2, Theme.buttonColor);

        tabbedPane.setSelectedIndex(0);

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 3) {
                new AuthFrame();
                dispose();
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu editMenu = new JMenu("Edit Menu");
        JMenu deleteMenu = new JMenu("Delete Menu");
        JMenuItem materialEdit = new JMenuItem("Edit Material");
        JMenuItem organizationEdit = new JMenuItem("Edit Organization");
        JMenuItem organizationDelete = new JMenuItem("Delete Organization");
        materialEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditMaterialFrame editMatFrame = new EditMaterialFrame();
                editMatFrame.setVisible(true);
            }
        });
        organizationEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditOrganizationFrame editOrgFrame = new EditOrganizationFrame();
                editOrgFrame.setVisible(true);
            }
        });
        organizationDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteOrganizationFrame deleteOrgFrame = new DeleteOrganizationFrame();
                deleteOrgFrame.setVisible(true);
            }
        });
        editMenu.add(materialEdit);
        editMenu.add(organizationEdit);
        deleteMenu.add(organizationDelete);
        menuBar.add(editMenu);
        menuBar.add(deleteMenu);
        this.setJMenuBar(menuBar);

        leftPanel.add(tabbedPane, BorderLayout.CENTER);

        leftPanel.setBackground(Theme.bgColor);
        leftPanel.setOpaque(true);

        leftPanel.setSize(new Dimension(576, 576));

        contentPanel.add(leftPanel, BorderLayout.WEST);

        // Create a label to display the image
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("SidePic.png")));
        contentPanel.add(imageLabel, BorderLayout.EAST);
    }

    // --------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        // Example of how to use the MainFrame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeFrame("");
            }
        });
    }
}