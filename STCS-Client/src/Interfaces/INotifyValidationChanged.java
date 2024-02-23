/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The INotifyValidationChanged interface defines a contract for objects that notify listeners
 * when a validation status changes. It includes the onValidationChanged method for raising
 * validation change events.
 */
package Interfaces;

import Events.ValidationChangedEventArgs;

/**
 * The INotifyValidationChanged interface provides a standard mechanism for objects to notify
 * listeners that a validation status has changed.
 */
public interface INotifyValidationChanged {

    /**
     * Notifies listeners that a validation status has changed.
     *
     * @param sender The object raising the validation change event.
     * @param e      The event arguments containing information about the validation change.
     */
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e);
}
