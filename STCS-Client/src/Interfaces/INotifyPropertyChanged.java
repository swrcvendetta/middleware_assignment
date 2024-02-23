/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The INotifyPropertyChanged interface defines a contract for objects that notify listeners
 * when a property value changes. It includes the onPropertyChanged method for raising
 * property change events.
 */
package Interfaces;

import Events.PropertyChangedEventArgs;

/**
 * The INotifyPropertyChanged interface provides a standard mechanism for objects to notify
 * listeners that a property value has changed.
 */
public interface INotifyPropertyChanged {

    /**
     * Notifies listeners that a property value has changed.
     *
     * @param sender The object raising the property change event.
     * @param e      The event arguments containing information about the changed property.
     */
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e);
}
