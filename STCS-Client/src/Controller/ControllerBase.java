package Controller;

import Interfaces.INotifyValidationChanged;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract class ControllerBase serves as a base implementation for controller classes.
 * It implements basic functionality for managing INotifyValidationChanged listeners.
 * Controller classes inheriting from this abstract class can add specific functionalities.
 */
public abstract class ControllerBase {

    /**
     * A list of INotifyValidationChanged listeners responding to validation changes.
     */
    protected List<INotifyValidationChanged> listeners;

    /**
     * Constructor creates a new instance of ControllerBase and initializes the listeners list.
     */
    public ControllerBase() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Adds an INotifyValidationChanged listener to the list.
     *
     * @param listener The listener to be added.
     */
    public void subscribe(INotifyValidationChanged listener) {
        this.listeners.add(listener);
    }
}
