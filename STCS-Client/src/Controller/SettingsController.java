package Controller;

import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Models.SettingsModel;

public class SettingsController extends ControllerBase{
    private SettingsModel settingsModel;
    public SettingsController(SettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }
    @Override
    public void subscribe(INotifyValidationChanged listener) {
        this.listeners.add(listener);
        String addressMsg = getAddressValidationMessage(this.settingsModel.getAddress());
        boolean bAddressValid = validateAddress(this.settingsModel.getAddress());
        listener.onValidationChanged(this, new ValidationChangedEventArgs("address", bAddressValid, addressMsg));
        String portMsg = getPortValidationMessage(this.settingsModel.getPort());
        boolean bPortValid = validatePort(this.settingsModel.getPort());
        listener.onValidationChanged(this, new ValidationChangedEventArgs("port", bPortValid, portMsg));
    }
    public void confirmChanges(INotifyValidationChanged sender, String address, int port) {
        boolean bValidAddress = validateAddress(address);
        boolean bValidPort = validatePort(port);
        if(bValidAddress && bValidPort) {
            this.settingsModel.setAddress(address);
            this.settingsModel.setPort(port);
        }
        /*
        if(!bValidAddress) {
            this.settingsModel.setAddress(this.settingsModel.getAddress());
        }
        if(!bValidPort) {
            this.settingsModel.setPort(this.settingsModel.getPort());
        }
        */
        String addressMsg = getAddressValidationMessage(address);
        sender.onValidationChanged(this, new ValidationChangedEventArgs("address", bValidAddress, addressMsg));
        String portMsg = getPortValidationMessage(port);
        sender.onValidationChanged(this, new ValidationChangedEventArgs("port", bValidPort, portMsg));
    }
    private boolean validateAddress(String address) {
        if(address.length() <= 5)
            return false;
        return true;
    }
    private boolean validatePort(int port) {
        // some validation-rules for port
        if(port < 10)
            return false;
        if(port == 80)
            return false;
        return true;
    }
    private String getAddressValidationMessage(String address) {
        if(validateAddress(address)) {
            return null;
        }
        else {
            return "Address must be more than 5 characters long.";
        }
    }
    private String getPortValidationMessage(int port) {
        if(validatePort(port)) {
            return null;
        }
        else {
            return "Port must be bigger than 9 and can not be 80.";
        }
    }
}
