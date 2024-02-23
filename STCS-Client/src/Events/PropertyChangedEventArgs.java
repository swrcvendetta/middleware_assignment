/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package Events;

/**
 * The PropertyChangedEventArgs class represents event arguments for property change events.
 * It extends the base EventArgs class and provides information about the changed property name
 * and its new value.
 */
public class PropertyChangedEventArgs extends EventArgs {

    /**
     * The name of the property that has changed.
     */
    protected String _propertyName;

    /**
     * The new value of the property.
     */
    protected Object _value;

    /**
     * Constructs a new instance of PropertyChangedEventArgs with the specified property name and value.
     *
     * @param propertyName The name of the property that has changed.
     * @param value        The new value of the property.
     */
    public PropertyChangedEventArgs(String propertyName, Object value) {
        this._propertyName = propertyName;
        this._value = value;
    }

    /**
     * Gets the name of the property that has changed.
     *
     * @return The name of the property.
     */
    public String getPropertyName() {
        return this._propertyName;
    }

    /**
     * Gets the new value of the property.
     *
     * @return The new value of the property.
     */
    public Object getPropertyValue() {
        return this._value;
    }
}
