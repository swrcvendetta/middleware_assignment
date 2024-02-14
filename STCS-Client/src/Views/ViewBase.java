package Views;

import Interfaces.INotifyPropertyChanged;
import ViewModels.ViewModelBase;

import javax.swing.*;

public abstract class ViewBase extends JFrame implements INotifyPropertyChanged {
    protected ViewModelBase viewModel;
    public ViewBase() {
        this.setVisible(true);
    }
    public ViewBase(ViewModelBase viewModel) {
        this.viewModel = viewModel;
    }
    public ViewModelBase getViewModel() {
        return this.viewModel;
    }
}
