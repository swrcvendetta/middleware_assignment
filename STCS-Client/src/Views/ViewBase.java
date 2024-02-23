/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The ViewBase class serves as the base class for views in the application.
 * It extends JFrame and implements interfaces for notifying about property changes and validation changes.
 */
package Views;

import Interfaces.INotifyPropertyChanged;
import Interfaces.INotifyValidationChanged;

import javax.swing.*;

/**
 * The ViewBase class provides a common foundation for views in the application.
 */
public abstract class ViewBase extends JFrame implements INotifyPropertyChanged, INotifyValidationChanged {

    /**
     * Constructs a new instance of ViewBase, making the frame visible.
     */
    public ViewBase() {
        this.setVisible(true);
    }
}
