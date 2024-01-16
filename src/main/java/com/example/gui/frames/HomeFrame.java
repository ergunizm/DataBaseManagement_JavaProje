package com.example.gui.frames;

import javax.swing.*;

import com.example.gui.MainFrame;
import com.example.gui.Theme;
import com.example.gui.panels.CompanyPanel;
import com.example.gui.panels.MaterialPanel;
import com.example.gui.panels.PartySelectionPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends MainFrame {
    public HomeFrame(String ssn) {
        super(ssn);

        JPanel leftPanel = new JPanel();

        // #region Tabbed pane ---------------------------------------------
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(Theme.font);
        tabbedPane.setForeground(Color.BLACK);
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setOpaque(true);

        tabbedPane.addTab("Party Selection", new PartySelectionPanel(ssn));
        tabbedPane.addTab("CompanyLogin", new CompanyPanel());
        tabbedPane.addTab("Material", new MaterialPanel(ssn));
        tabbedPane.addTab("Logout", null);

        tabbedPane.setSelectedIndex(0);

        // Logout
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 3) {
                dispose();
                new AuthFrame();
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu editMenu = new JMenu("Edit Menu");
        editMenu.setFont(Theme.font);

        JMenu deleteMenu = new JMenu("Delete Menu");
        deleteMenu.setFont(Theme.font);

        JMenuItem materialEdit = new JMenuItem("Edit Material");
        materialEdit.setFont(Theme.font);

        JMenuItem organizationEdit = new JMenuItem("Edit Organization");
        organizationEdit.setFont(Theme.font);

        JMenuItem organizationDelete = new JMenuItem("Delete Organization");
        organizationDelete.setFont(Theme.font);

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

        leftPanel.add(tabbedPane);

        leftPanel.setBackground(Color.white);
        leftPanel.setOpaque(true);

        contentPanel.add(leftPanel);
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