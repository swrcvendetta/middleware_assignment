package Controller;

import Interfaces.INotifyValidationChanged;

import java.util.ArrayList;
import java.util.List;

public abstract class ControllerBase {
    protected List<INotifyValidationChanged> listeners;
    public ControllerBase() {
        this.listeners = new ArrayList<>();
    }
    public void subscribe(INotifyValidationChanged listener) {
        this.listeners.add(listener);
    }
}
