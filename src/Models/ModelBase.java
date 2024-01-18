package Models;

import Interfaces.INotifyPropertyChanged;
import ViewModels.ViewModelBase;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelBase {
    protected ViewModelBase viewModel;
    protected List<INotifyPropertyChanged> listeners;
    public ModelBase(ViewModelBase viewModel, INotifyPropertyChanged listener) {
        this.viewModel = viewModel;
        this.listeners = new ArrayList<>();
        this.listeners.add(listener);
    }
}
