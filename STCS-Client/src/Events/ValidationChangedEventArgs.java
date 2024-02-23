/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package Events;

/**
 * The ValidationChangedEventArgs class represents event arguments for validation change events.
 * It extends the base EventArgs class and provides information about the changed property name,
 * the validation status, and an optional validation message.
 */
public class ValidationChangedEventArgs extends EventArgs {

    /**
     * The name of the property associated with the validation change.
     */
    protected String _propertyName;

    /**
     * The validation status indicating whether the associated property is valid or not.
     */
    protected boolean _valid;

    /**
     * An optional validation message providing details about the validation result.
     */
    protected String _validationMessage;

    /**
     * Constructs a new instance of ValidationChangedEventArgs with the specified property name,
     * validation status, and validation message.
     *
     * @param propertyName      The name of the property associated with the validation change.
     * @param valid             The validation status indicating whether the property is valid or not.
     * @param validationMessage An optional validation message providing details about the validation result.
     */
    public ValidationChangedEventArgs(String propertyName, boolean valid, String validationMessage) {
        this._propertyName = propertyName;
        this._valid = valid;
        this._validationMessage = validationMessage;
    }

    /**
     * Gets the name of the property associated with the validation change.
     *
     * @return The name of the property.
     */
    public String getPropertyName() {
        return this._propertyName;
    }

    /**
     * Checks if the associated property is valid.
     *
     * @return True if the property is valid; otherwise, false.
     */
    public boolean isValid() {
        return this._valid;
    }

    /**
     * Gets the optional validation message providing details about the validation result.
     *
     * @return The validation message or null if not specified.
     */
    public String getValidationMessage() {
        return this._validationMessage;
    }
}
