package Views;

import Interfaces.INotifyPropertyChanged;

import javax.swing.*;

public abstract class ViewBase implements INotifyPropertyChanged {
    protected JFrame window;
    public ViewBase() {
        this.window = new JFrame();
        this.show();
    }
    public void show() {
        this.window.setVisible(true);
    }

    public void hide() {
        this.window.setVisible(false);
    }
}
