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
        String AddressValid = validateAddress(this.settingsModel.getAddress());
        listener.onValidationChanged(this, new ValidationChangedEventArgs("address", AddressValid == null, AddressValid));
        String PortValid = validatePort(this.settingsModel.getPort());
        listener.onValidationChanged(this, new ValidationChangedEventArgs("port", PortValid == null, PortValid));
    }
    public void confirmChanges(INotifyValidationChanged sender, String address, int port) {
        String ValidAddress = validateAddress(address);
        String ValidPort = validatePort(port);
        if(ValidAddress != null && ValidPort != null) {
            this.settingsModel.setAddress(address);
            this.settingsModel.setPort(port);
        }
        sender.onValidationChanged(this, new ValidationChangedEventArgs("address", ValidAddress == null, ValidAddress));
        sender.onValidationChanged(this, new ValidationChangedEventArgs("port", ValidPort == null, ValidPort));
    }
    private String validateAddress(String address) {
        if(address.length() <= 5)
            return "Address must be more than 5 characters long.";
        return null;
    }
    private String validatePort(int port) {
        // some validation-rules for port
        if(port < 10)
            return "Port must be bigger than 9.";
        if(port == 80)
            return "Port can not be 80.";
        return null;
    }
}
