/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The SettingsTabPageView class represents a tab page for managing application settings in the main window.
 * It extends ViewBase and implements the ITabPageView interface.
 */
package Views.TabPages;

import Controller.SettingsController;
import Events.PropertyChangedEventArgs;
import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Interfaces.ITabPageView;
import Views.ViewBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The SettingsTabPageView class provides a tab page for managing application settings in the main window.
 * It includes UI components such as text fields, buttons, and error labels.
 */
public class SettingsTabPageView extends ViewBase implements ITabPageView, INotifyValidationChanged {

    /**
     * Base panel containing settings-related UI components.
     */
    private JPanel basePanel;

    /**
     * Panel for holding settings form components.
     */
    private JPanel pnl_form;

    /**
     * Text field for entering the server address.
     */
    private JTextField txtField_address;

    /**
     * Text field for entering the server port.
     */
    private JTextField txtField_port;

    /**
     * Label for displaying the server address.
     */
    private JLabel lbl_address;

    /**
     * Label for displaying the server port.
     */
    private JLabel lbl_port;

    /**
     * Label for displaying validation errors.
     */
    private JLabel lbl_error;

    /**
     * Button for saving changes to the settings.
     */
    private JButton btn_saveChanges;

    /**
     * Name of the settings tab page.
     */
    private final String tabPageName = "Settings";

    /**
     * Icon for the settings tab page.
     */
    private final Icon tabPageIcon = null;

    /**
     * Tooltip for the settings tab page.
     */
    private final String tabPageTip = "Settings";

    /**
     * Settings controller managing application settings.
     */
    private SettingsController settingsController;

    /**
     * Map to store failed validation messages.
     */
    private Map<String, String> failedValidations;

    /**
     * Constructs a SettingsTabPageView object with the provided SettingsController.
     *
     * @param controller The SettingsController managing application settings.
     */
    public SettingsTabPageView(SettingsController controller) {
        this.setVisible(false);
        this.settingsController = controller;
        this.failedValidations = new HashMap<>();
        this.settingsController.subscribe(this);
        INotifyValidationChanged sender = this;

        // ActionListener for the save changes button
        this.btn_saveChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsController.confirmChanges(sender, txtField_address.getText(), Integer.valueOf(txtField_port.getText()));
            }
        });
    }

    /**
     * Handles property changes in the settings tab page view.
     *
     * @param sender The object sending the property change.
     * @param e      The PropertyChangedEventArgs containing information about the property change.
     */
    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "address":
                String address = ((String) e.getPropertyValue());
                this.txtField_address.setText(address);
                break;
            case "port":
                int port = ((int) e.getPropertyValue());
                this.txtField_port.setText(String.valueOf(port));
                break;
            default:
                break;
        }
    }

    /**
     * Gets the name of the settings tab page.
     *
     * @return The name of the settings tab page.
     */
    @Override
    public String getTabPageName() {
        return this.tabPageName;
    }

    /**
     * Gets the JPanel representing the settings tab page.
     *
     * @return The JPanel representing the settings tab page.
     */
    @Override
    public JPanel getTabPagePanel() {
        return this.basePanel;
    }

    /**
     * Gets the icon for the settings tab page.
     *
     * @return The icon for the settings tab page.
     */
    @Override
    public Icon getTabPageIcon() {
        return this.tabPageIcon;
    }

    /**
     * Gets the tooltip for the settings tab page.
     *
     * @return The tooltip for the settings tab page.
     */
    @Override
    public String getTabPageTip() {
        return this.tabPageTip;
    }

    /**
     * Gets the entered server address from the text field.
     *
     * @return The entered server address.
     */
    public String getAddress() {
        return this.txtField_address.getText();
    }

    /**
     * Gets the entered server port from the text field.
     *
     * @return The entered server port.
     */
    public int getPort() {
        try {
            int port = Integer.valueOf(this.txtField_port.getText());
            return port;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Handles validation changes in the settings tab page view.
     *
     * @param sender The object sending the validation change.
     * @param e      The ValidationChangedEventArgs containing information about the validation change.
     */
    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {
        if (!e.isValid())
            this.failedValidations.put(e.getPropertyName(), e.getValidationMessage());
        if (e.isValid() && this.failedValidations.containsKey(e.getPropertyName()))
            this.failedValidations.remove(e.getPropertyName());
        handleFailedValidations();
    }

    /**
     * Updates the error label with the current validation messages.
     */
    private void handleFailedValidations() {
        int size = this.failedValidations.size();
        if (size == 0) {
            this.lbl_error.setText("");
            return;
        }
        for (String s : this.failedValidations.values()) {
            this.lbl_error.setText(s);
        }
    }
}
