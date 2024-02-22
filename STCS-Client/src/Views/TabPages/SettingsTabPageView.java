package Views.TabPages;

import Controller.SettingsController;
import Events.PropertyChangedEventArgs;
import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Interfaces.ITabPageView;
import Views.ViewBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SettingsTabPageView extends ViewBase implements ITabPageView {
    private JPanel basePanel;
    private JPanel pnl_form;
    private JTextField txtField_address;
    private JTextField txtField_port;
    private JLabel lbl_address;
    private JLabel lbl_port;
    private JLabel lbl_error;
    private JButton btn_saveChanges;
    private final String tabPageName = "Settings";
    private final Icon tabPageIcon = null;
    private final String tabPageTip = "Settings";
    private SettingsController settingsController;
    private Map<String, String> failedValidations;

    public SettingsTabPageView(SettingsController controller) {
        this.setVisible(false);
        this.settingsController = controller;
        this.failedValidations = new HashMap<>();
        this.settingsController.subscribe(this);
        INotifyValidationChanged sender = this;
        this.btn_saveChanges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsController.confirmChanges(sender, txtField_address.getText(), Integer.valueOf(txtField_port.getText()));
            }
        });
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "address":
                String address = ((String)e.getPropertyValue());
                this.txtField_address.setText(address);
                break;
            case "port":
                int port = ((int)e.getPropertyValue());
                this.txtField_port.setText(String.valueOf(port));
                break;
            default:
                break;
        }
    }

    @Override
    public String getTabPageName() {
        return this.tabPageName;
    }

    @Override
    public JPanel getTabPagePanel() {
        return this.basePanel;
    }

    @Override
    public Icon getTabPageIcon() {
        return this.tabPageIcon;
    }

    @Override
    public String getTabPageTip() {
        return this.tabPageTip;
    }
    public String getAddress() {
        return this.txtField_address.getText();
    }
    public int getPort() {
        try {
            int port = Integer.valueOf(this.txtField_port.getText());
            return port;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {
        if(!e.isValid())
            this.failedValidations.put(e.getPropertyName(), e.getValidationMessage());
        if(e.isValid() && this.failedValidations.containsKey(e.getPropertyName()))
            this.failedValidations.remove(e.getPropertyName());
        handleFailedValidations();
    }
    private void handleFailedValidations() {
        int size = this.failedValidations.size();
        if(size == 0) {
            this.lbl_error.setText("");
            return;
        }
        for (String s : this.failedValidations.values()) {
            this.lbl_error.setText(s);
        }
    }
}
