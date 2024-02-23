package Controller;

import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Models.LoginModel;
import Models.SettingsModel;

/**
 * The LoginController class is responsible for handling user interactions and validation
 * related to the login functionality in the application. It extends the ControllerBase class.
 */
public class LoginController extends ControllerBase {

    private final LoginModel loginModel;
    private final SettingsModel settingsModel;

    /**
     * Constructs a LoginController object with the specified LoginModel and SettingsModel.
     *
     * @param loginModel    The LoginModel associated with the login functionality.
     * @param settingsModel The SettingsModel providing necessary settings information.
     */
    public LoginController(LoginModel loginModel, SettingsModel settingsModel) {
        this.loginModel = loginModel;
        this.settingsModel = settingsModel;
    }

    /**
     * Subscribes a listener for validation events related to the username.
     * It validates the initial state of the username and notifies the listener accordingly.
     *
     * @param listener The listener to subscribe for validation events.
     */
    @Override
    public void subscribe(INotifyValidationChanged listener) {
        this.listeners.add(listener);
        String usernameValidationMessage = validateUsername(this.loginModel.getUsername());
        listener.onValidationChanged(this, new ValidationChangedEventArgs("username", usernameValidationMessage == null, usernameValidationMessage));
    }

    /**
     * Validates the given username based on specific rules.
     *
     * @param username The username to be validated.
     * @return A validation message if the username is invalid, or null if it's valid.
     */
    private String validateUsername(String username) {
        // Validation rules for the username
        if (username.length() < 5) {
            return "Username must be more than 4 characters long.";
        }
        if (username.equals("SERVER")) {
            return "Username can not be 'SERVER'.";
        }
        return null;
    }

    /**
     * Initiates the login process and validates the provided username.
     * Notifies the sender about the validation result.
     *
     * @param sender   The sender of the validation event.
     * @param username The username to be validated and used for login.
     */
    public void login(INotifyValidationChanged sender, String username) {
        String usernameValidationMessage = validateUsername(username);
        sender.onValidationChanged(this, new ValidationChangedEventArgs("username", usernameValidationMessage == null, usernameValidationMessage));

        // If username is valid, initiate the login process
        if (usernameValidationMessage == null) {
            this.loginModel.login(this.settingsModel.getAddress(), this.settingsModel.getPort());
        }
    }
}
