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
