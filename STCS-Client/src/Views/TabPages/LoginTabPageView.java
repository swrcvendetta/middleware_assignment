package Views.TabPages;

import Controller.LoginController;
import Events.PropertyChangedEventArgs;
import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Interfaces.ITabPageView;
import Views.ViewBase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginTabPageView extends ViewBase implements ITabPageView {
    private JPanel basePanel;
    private JTextField txtField_username;
    private JButton btn_login;
    private JPanel pnl_form;
    private JLabel lbl_loginStatus;
    private JLabel lbl_username;
    private final String tabPageName = "Login";
    private final Icon tabPageIcon = null;
    private final String tabPageTip = "Login";

    private LoginController loginController;

    public LoginTabPageView(LoginController controller) {
        this.setVisible(false);
        this.loginController = controller;
        this.loginController.subscribe(this);
        INotifyValidationChanged sender = this;
        this.btn_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginController.login(sender, txtField_username.getText());
            }
        });
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "username":
                String username = ((String)e.getPropertyValue());
                this.txtField_username.setText(username);
                break;
            case "connected":
                if((boolean)e.getPropertyValue()) {
                    // connected, can close this tab + open chat-tab
                    System.out.println("logged in as: " + this.txtField_username.getText());
                }
                else {
                    // popup-error no connection or something
                    System.out.println("couldn't connect to server");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public String getTabPageName() {
        return tabPageName;
    }

    @Override
    public JPanel getTabPagePanel() {
        return this.basePanel;
    }

    @Override
    public Icon getTabPageIcon() {
        return tabPageIcon;
    }

    @Override
    public String getTabPageTip() {
        return this.tabPageTip;
    }

    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {
        if(e.isValid())
            this.lbl_loginStatus.setText("");
        else
            this.lbl_loginStatus.setText(e.getValidationMessage());
    }
    public String getUsername() {
        return this.txtField_username.getText();
    }
}
