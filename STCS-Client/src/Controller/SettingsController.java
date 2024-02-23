/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package Controller;

import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Models.SettingsModel;

/**
 * The SettingsController class manages user settings and provides validation functionality
 * for address and port changes. It extends the ControllerBase class and interacts with
 * the SettingsModel to validate and apply changes.
 */
public class SettingsController extends ControllerBase {

    /**
     * The SettingsModel associated with this controller.
     */
    private SettingsModel settingsModel;

    /**
     * Constructs a new SettingsController instance with the specified SettingsModel.
     *
     * @param settingsModel The SettingsModel instance to be associated with this controller.
     */
    public SettingsController(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    /**
     * Subscribes a listener for validation changes and immediately notifies the listener
     * about the validation status of the current address and port settings.
     *
     * @param listener The INotifyValidationChanged listener to be subscribed.
     */
    @Override
    public void subscribe(INotifyValidationChanged listener) {
        this.listeners.add(listener);

        // Validate and notify about address
        String addressValidationResult = validateAddress(this.settingsModel.getAddress());
        listener.onValidationChanged(this, new ValidationChangedEventArgs("address", addressValidationResult == null, addressValidationResult));

        // Validate and notify about port
        String portValidationResult = validatePort(this.settingsModel.getPort());
        listener.onValidationChanged(this, new ValidationChangedEventArgs("port", portValidationResult == null, portValidationResult));
    }

    /**
     * Validates the provided address and port, updates the SettingsModel if valid,
     * and notifies the sender about the validation results for both address and port.
     *
     * @param sender  The sender of the changes, implementing INotifyValidationChanged.
     * @param address The new address to be validated and applied.
     * @param port    The new port to be validated and applied.
     */
    public void confirmChanges(INotifyValidationChanged sender, String address, int port) {
        // Validate address and port
        String validAddress = validateAddress(address);
        String validPort = validatePort(port);

        // If both address and port are valid, apply changes to the SettingsModel
        if (validAddress == null && validPort == null) {
            this.settingsModel.setAddress(address);
            this.settingsModel.setPort(port);
        }

        // Notify the sender about the validation results for address and port
        sender.onValidationChanged(this, new ValidationChangedEventArgs("address", validAddress == null, validAddress));
        sender.onValidationChanged(this, new ValidationChangedEventArgs("port", validPort == null, validPort));
    }

    /**
     * Validates the provided address based on specific rules.
     *
     * @param address The address to be validated.
     * @return A validation message or null if the address is valid.
     */
    private String validateAddress(String address) {
        if (address.length() <= 5)
            return "Address must be more than 5 characters long.";
        return null;
    }

    /**
     * Validates the provided port based on specific rules.
     *
     * @param port The port to be validated.
     * @return A validation message or null if the port is valid.
     */
    private String validatePort(int port) {
        // Some validation rules for the port
        if (port < 10)
            return "Port must be bigger than 9.";
        if (port == 80)
            return "Port cannot be 80.";
        return null;
    }
}
