/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

import Views.MainWindowView;
import Views.ViewBase;

import javax.swing.*;

/**
 * The Client class serves as the entry point for the client application.
 * It initializes the Swing GUI by creating an instance of the MainWindowView
 * and scheduling it to run on the Event Dispatch Thread using SwingUtilities.invokeLater().
 */
public class Client {

    /**
     * The main method to start the client application.
     *
     * @param args The command-line arguments (unused in this application).
     */
    public static void main(String[] args) {
        // Create an instance of the Client class to start the application
        Client client = new Client();
    }

    /**
     * Constructs a Client object.
     * It creates a new instance of MainWindowView and schedules it to run on the
     * Event Dispatch Thread, ensuring proper initialization of the Swing GUI.
     */
    public Client() {
        // Schedule the creation of the main window view on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the main window view
                ViewBase view = new MainWindowView();
            }
        });
    }
}
