package Views.Controls;

import javax.swing.*;

public abstract class ControlBase extends JFrame {
    public ControlBase() {
        this.setVisible(false);
    }
    public JPanel getControl() {
        return null;
    }
}
