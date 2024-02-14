package ViewModels;

import Interfaces.INotifyPropertyChanged;
import Models.ModelBase;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewModelBase implements INotifyPropertyChanged {
    protected List<INotifyPropertyChanged> listeners;
    protected ModelBase model;

    public ViewModelBase(INotifyPropertyChanged listener) {
        this.listeners = new ArrayList<>();
        this.listeners.add(listener);
    }
    public ModelBase getModel() {
        return this.model;
    }
}
