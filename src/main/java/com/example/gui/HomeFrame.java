package com.example.gui;

import javax.swing.*;
import java.awt.*;

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

        tabbedPane.addTab("Party Selection", new PartySelectionPanel());
        tabbedPane.addTab("Cart", new ShoppingCartPanel());
        tabbedPane.addTab("Offer", new OfferPanel());
        tabbedPane.addTab("CompanyLogin", new CompanyPanel());
        tabbedPane.addTab("Material", new MaterialPanel(ssn));
        tabbedPane.addTab("Logout", null);

        tabbedPane.setBackgroundAt(0, Theme.buttonColor);
        tabbedPane.setBackgroundAt(1, Theme.buttonColor);
        tabbedPane.setBackgroundAt(2, Theme.buttonColor);
        tabbedPane.setBackgroundAt(3, Theme.buttonColor);
        tabbedPane.setBackgroundAt(4, Theme.buttonColor);

        tabbedPane.setSelectedIndex(0);

        // when the user clicks on the logout tab, the program will return to the
        // authentication panel
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 5) {
                new AuthFrame();
                dispose();
            }
        });
        // #endregion Tabbed pane ------------------------------------------

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
