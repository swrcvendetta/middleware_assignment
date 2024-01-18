package Views;

import Events.PropertyChangedEventArgs;
import ViewModels.MainWindowViewModel;

import javax.swing.*;

public class MainWindowView extends ViewBase {
    public MainWindowViewModel model;
    public MainWindowView() {
        super();
        this.model = new MainWindowViewModel(this);
        this.window.setTitle("Test");
        this.window.setSize(600, 400);
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.window.setResizable(false);
        this.window.setLocationRelativeTo(null);
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        System.out.println(sender.toString() + e.getPropertyName());
    }
}
