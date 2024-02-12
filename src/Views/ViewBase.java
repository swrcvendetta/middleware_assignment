package Views;

import Interfaces.INotifyPropertyChanged;

import javax.swing.*;

public abstract class ViewBase extends JFrame implements INotifyPropertyChanged {
    public ViewBase() {
        this.setVisible(true);
    }
}
