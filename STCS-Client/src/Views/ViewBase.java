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
