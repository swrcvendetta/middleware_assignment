package Views;

import Controller.ControllerBase;
import Interfaces.INotifyPropertyChanged;
import Interfaces.INotifyValidationChanged;

import javax.swing.*;
import java.util.List;

public abstract class ViewBase extends JFrame implements INotifyPropertyChanged, INotifyValidationChanged {
    public ViewBase() {
        this.setVisible(true);
    }
}
