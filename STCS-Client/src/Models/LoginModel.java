package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;

import java.io.IOException;
import java.net.Socket;

public class LoginModel extends ModelBase {
    private String username;
    public LoginModel() {
        this.setUsername("chatter");
    }
    @Override
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("username", this.username));
    }
    public void setUsername(String username) {
        this.username = username;
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("username", username));
    }
    public String getUsername() {
        return username;
    }
    public void login(String address, int port) {
        Socket socket;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            socket = null;
        }
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("connected", socket));
    }
}
