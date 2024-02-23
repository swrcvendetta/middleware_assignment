/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The ModelBase class serves as the base class for model classes in the application.
 * It provides functionality for managing INotifyPropertyChanged listeners and subscriptions.
 */
package Models;

import Interfaces.INotifyPropertyChanged;

import java.util.ArrayList;
import java.util.List;

/**
 * The ModelBase class provides a common foundation for model classes in the application.
 */
public abstract class ModelBase {

    /**
     * A list of INotifyPropertyChanged listeners, allowing models to notify subscribers of property changes.
     */
    protected List<INotifyPropertyChanged> listeners;

    /**
     * Constructs a new instance of ModelBase with an empty list of listeners.
     */
    public ModelBase() {
        this.listeners = new ArrayList<>();
    }

    /**
     * Constructs a new instance of ModelBase with an initial listener.
     *
     * @param listener The initial INotifyPropertyChanged listener to be added.
     */
    public ModelBase(INotifyPropertyChanged listener) {
        this.listeners = new ArrayList<>();
        this.listeners.add(listener);
    }

    /**
     * Subscribes an INotifyPropertyChanged listener to receive notifications about property changes.
     *
     * @param listener The INotifyPropertyChanged listener to be subscribed.
     */
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);
    }
}
