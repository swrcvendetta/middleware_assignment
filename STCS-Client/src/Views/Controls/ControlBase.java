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
