/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The ControlBase class serves as the base class for custom controls in the application.
 * It extends JFrame and provides methods for controlling the visibility of the control and obtaining its JPanel representation.
 */
package Views.Controls;

import javax.swing.*;

/**
 * The ControlBase class provides a common foundation for custom controls in the application.
 */
public abstract class ControlBase extends JFrame {

    /**
     * Constructs a new instance of ControlBase, initializing the control as not visible.
     */
    public ControlBase() {
        this.setVisible(false);
    }

    /**
     * Gets the JPanel representation of the control.
     *
     * @return The JPanel representing the control.
     */
    public JPanel getControl() {
        return null; // This method should be overridden by subclasses to provide the actual control panel.
    }
}
