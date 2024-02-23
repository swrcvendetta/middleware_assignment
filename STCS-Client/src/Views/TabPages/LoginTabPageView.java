/**
 * The LoginTabPageView class represents a tab page for user login functionality in the main window.
 * It extends ViewBase and implements the ITabPageView interface.
 */
package Views.TabPages;

import Controller.LoginController;
import Events.PropertyChangedEventArgs;
import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Interfaces.ITabPageView;
import Views.ViewBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The LoginTabPageView class provides a tab page for managing user login functionality in the main window.
 * It includes UI components such as text fields, buttons, and status labels.
 */
public class LoginTabPageView extends ViewBase implements ITabPageView, INotifyValidationChanged {

    /**
     * Base panel containing login-related UI components.
     */
    private JPanel basePanel;

    /**
     * Text field for entering the username.
     */
    private JTextField txtField_username;

    /**
     * Button for initiating the login process.
     */
    private JButton btn_login;

    /**
     * Panel for holding login form components.
     */
    private JPanel pnl_form;

    /**
     * Label for displaying the login status.
     */
    private JLabel lbl_loginStatus;

    /**
     * Label for displaying the username.
     */
    private JLabel lbl_username;

    /**
     * Name of the login tab page.
     */
    private final String tabPageName = "Login";

    /**
     * Icon for the login tab page.
     */
    private final Icon tabPageIcon = null;

    /**
     * Tooltip for the login tab page.
     */
    private final String tabPageTip = "Login";

    /**
     * Login controller managing the user login functionality.
     */
    private LoginController loginController;

    /**
     * Constructs a LoginTabPageView object with the provided LoginController.
     *
     * @param controller The LoginController managing user login functionality.
     */
    public LoginTabPageView(LoginController controller) {
        this.setVisible(false);
        this.loginController = controller;
        this.loginController.subscribe(this);
        INotifyValidationChanged sender = this;

        // ActionListener for the login button
        this.btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginController.login(sender, txtField_username.getText());
            }
        });
    }

    /**
     * Handles property changes in the login tab page view.
     *
     * @param sender The object sending the property change.
     * @param e      The PropertyChangedEventArgs containing information about the property change.
     */
    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "username":
                String username = ((String) e.getPropertyValue());
                this.txtField_username.setText(username);
                break;
            case "connected":
                if (e.getPropertyValue() != null) {
                    // connected, can close this tab + open chat-tab
                    System.out.println("logged in as: " + this.txtField_username.getText());
                } else {
                    // popup-error no connection or something
                    System.out.println("couldn't connect to the server");
                    this.lbl_loginStatus.setText("Connection failed...");
                }
                break;
            default:
                break;
        }
    }

    /**
     * Gets the name of the login tab page.
     *
     * @return The name of the login tab page.
     */
    @Override
    public String getTabPageName() {
        return tabPageName;
    }

    /**
     * Gets the JPanel representing the login tab page.
     *
     * @return The JPanel representing the login tab page.
     */
    @Override
    public JPanel getTabPagePanel() {
        return this.basePanel;
    }

    /**
     * Gets the icon for the login tab page.
     *
     * @return The icon for the login tab page.
     */
    @Override
    public Icon getTabPageIcon() {
        return tabPageIcon;
    }

    /**
     * Gets the tooltip for the login tab page.
     *
     * @return The tooltip for the login tab page.
     */
    @Override
    public String getTabPageTip() {
        return this.tabPageTip;
    }

    /**
     * Handles validation changes in the login tab page view.
     *
     * @param sender The object sending the validation change.
     * @param e      The ValidationChangedEventArgs containing information about the validation change.
     */
    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {
        if (e.isValid())
            this.lbl_loginStatus.setText("");
        else
            this.lbl_loginStatus.setText(e.getValidationMessage());
    }

    /**
     * Gets the entered username from the text field.
     *
     * @return The entered username.
     */
    public String getUsername() {
        return this.txtField_username.getText();
    }
}
