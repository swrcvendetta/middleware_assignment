package Models;

import Interfaces.INotifyPropertyChanged;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelBase {
    protected List<INotifyPropertyChanged> listeners;
    public ModelBase() {
        this.listeners = new ArrayList<>();
    }
    public ModelBase(INotifyPropertyChanged listener) {
        this.listeners = new ArrayList<>();
        this.listeners.add(listener);
    }
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);
    }
}
