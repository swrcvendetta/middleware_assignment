package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;

public class SettingsModel extends ModelBase {
    private String address;
    private int port;
    public SettingsModel() {
        this.setAddress("localhost");
        this.setPort(1337);
    }
    @Override
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("address", address));
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("port", port));
    }
    public void setAddress(String address) {
        this.address = address;
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("address", address));
    }
    public String getAddress() {
        return this.address;
    }
    public void setPort(int port) {
        this.port = port;
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("port", port));
    }
    public int getPort() {
        return this.port;
    }
    public boolean login(String address, int port) {
        return false;
    }
}
