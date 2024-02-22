package Controller;

import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Models.LoginModel;
import Models.SettingsModel;

public class LoginController extends ControllerBase {
    private final LoginModel loginModel;
    private final SettingsModel settingsModel;
    public LoginController(LoginModel loginModel, SettingsModel settingsModel) {
        this.loginModel = loginModel;
        this.settingsModel = settingsModel;
    }
    @Override
    public void subscribe(INotifyValidationChanged listener) {
        this.listeners.add(listener);
        String usernameMsg = getUsernameValidationMessage(this.loginModel.getUsername());
        boolean bUsernameValid = validateUsername(this.loginModel.getUsername());
        listener.onValidationChanged(this, new ValidationChangedEventArgs("username", bUsernameValid, usernameMsg));
    }
    private boolean validateUsername(String username) {
        // some validation-rules for port
        if(username.length() < 5 || username.equals("SERVER"))
            return false;
        return true;
    }
    private String getUsernameValidationMessage(String username) {
        if(validateUsername(username)) {
            return null;
        }
        else {
            return "Username must be more than 5 characters long.";
        }
    }
    public void login(INotifyValidationChanged sender, String username) {
        boolean bUsernameValid = validateUsername(username);
        String usernameMsg = getUsernameValidationMessage(username);
        sender.onValidationChanged(this, new ValidationChangedEventArgs("username", bUsernameValid, usernameMsg));
        if(bUsernameValid)
            this.loginModel.login(this.settingsModel.getAddress(), this.settingsModel.getPort());
    }
}
