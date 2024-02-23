/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The SettingsModel class represents the model for managing application settings.
 * It extends the ModelBase class and includes features for setting and getting the server address and port.
 */
package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;

/**
 * The SettingsModel class provides functionality for managing application settings.
 */
public class SettingsModel extends ModelBase {

    /**
     * The server address associated with the settings model.
     */
    private String address;

    /**
     * The port number associated with the settings model.
     */
    private int port;

    /**
     * Constructs a new instance of SettingsModel with default address "localhost" and port 1337.
     */
    public SettingsModel() {
        this.setAddress("localhost");
        this.setPort(1337);
    }

    /**
     * Subscribes an INotifyPropertyChanged listener to receive notifications about property changes.
     * Notifies subscribers about the initial address and port.
     *
     * @param listener The INotifyPropertyChanged listener to be subscribed.
     */
    @Override
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);

        // Notify subscribers about the initial address and port
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("address", address));
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("port", port));
    }

    /**
     * Sets the server address and notifies subscribers about the change.
     *
     * @param address The new server address to be set.
     */
    public void setAddress(String address) {
        this.address = address;

        // Notify subscribers about the address change
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("address", address));
    }

    /**
     * Gets the current server address associated with the settings model.
     *
     * @return The server address.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Sets the port number and notifies subscribers about the change.
     *
     * @param port The new port number to be set.
     */
    public void setPort(int port) {
        this.port = port;

        // Notify subscribers about the port change
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("port", port));
    }

    /**
     * Gets the current port number associated with the settings model.
     *
     * @return The port number.
     */
    public int getPort() {
        return this.port;
    }
}
