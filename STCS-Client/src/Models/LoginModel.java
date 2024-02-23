/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The LoginModel class represents the model for managing login-related functionality in the application.
 * It extends the ModelBase class and includes features for setting the username, subscribing to property changes,
 * and attempting to connect to a server.
 */
package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;

import java.io.IOException;
import java.net.Socket;

/**
 * The LoginModel class provides functionality for managing login-related operations.
 */
public class LoginModel extends ModelBase {

    /**
     * The username associated with the login model.
     */
    private String username;

    /**
     * Constructs a new instance of LoginModel with a default username.
     */
    public LoginModel() {
        this.setUsername("chatter");
    }

    /**
     * Subscribes an INotifyPropertyChanged listener to receive notifications about property changes.
     * Notifies subscribers about the initial username.
     *
     * @param listener The INotifyPropertyChanged listener to be subscribed.
     */
    @Override
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);

        // Notify subscribers about the initial username
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("username", this.username));
    }

    /**
     * Sets the username and notifies subscribers about the change.
     *
     * @param username The new username to be set.
     */
    public void setUsername(String username) {
        this.username = username;

        // Notify subscribers about the username change
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("username", username));
    }

    /**
     * Gets the current username associated with the login model.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Attempts to connect to a server with the specified address and port.
     * Notifies subscribers about the connection status.
     *
     * @param address The address of the server.
     * @param port    The port number of the server.
     */
    public void login(String address, int port) {
        Socket socket;
        try {
            // Attempt to create a socket and connect to the server
            socket = new Socket(address, port);
        } catch (IOException e) {
            // In case of an exception, set the socket to null
            socket = null;
        }

        // Notify subscribers about the connection status
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("connected", socket));
    }
}
