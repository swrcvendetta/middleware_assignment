package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import ViewModels.ViewModelBase;

public class SettingsTabPageModel extends ModelBase{
    private String address;
    private int port;
    public SettingsTabPageModel(ViewModelBase viewModel, INotifyPropertyChanged listener) {
        super(viewModel, listener);
        this.setAddress("localhost");
        this.setPort(1337);
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
}
