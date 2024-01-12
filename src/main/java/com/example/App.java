package com.example;

import java.util.List;

import com.example.gui.AuthFrame;
import com.example.models.User;
import com.example.utils.DBUtil;
import com.example.utils.ColoredOutput;

/**
 * The main class of the application.
 * This class contains the entry point of the application and handles the
 * database connection.
 */
public class App {
    /**
     * The main method is the entry point of the application.
     * It performs the necessary setup tasks before starting the application,
     * such as establishing a connection to the database and testing the connection.
     * After that, it creates and shows the graphical user interface (GUI) using
     * Swing.
     * Finally, it performs cleanup tasks after the application is closed, such as
     * closing the database connection.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Things to do before starting the application
        // --------------------------------------------------------------------

        // Start the connection to the database
        DBUtil.startConnection();

        // Test the database connection is working with a simple query of the user table
        List<User> users = DBUtil.selectAllFromDB("users");
        /* if the first user's first name is "Alice", then the connection is working
        if (users.get(0).getUserName().equals("Alice")) {
            ColoredOutput.print("Database test is successful.", ColoredOutput.Color.GREEN_BOLD_BRIGHT);
        } else {
            System.err.println("Database test is failed.");
        }*/

        // ---------------------------------------------------------------------
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

        // Things to do after the application is closed
        // --------------------------------------------------------------------
        // Close the connection to the database if the application is closed
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                DBUtil.closeConnection();
            }
        });
    }

    // Functions
    // ========================================================================
    /**
     * Creates and shows the graphical user interface (GUI) for the application.
     */
    private static void createAndShowGUI() {
        // Create and set up the first window to be shown
        AuthFrame authFrame = new AuthFrame();
        authFrame.setVisible(true);
    }
}
