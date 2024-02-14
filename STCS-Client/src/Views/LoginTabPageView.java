package Views;

import Events.PropertyChangedEventArgs;
import Interfaces.ITabPageView;
import Models.ModelBase;
import ViewModels.LoginTabPageViewModel;

import javax.swing.*;

public class LoginTabPageView extends ViewBase implements ITabPageView {
    private JPanel basePanel;
    private JTextField txtField_username;
    private JButton btn_login;
    private JPanel pnl_form;
    private JLabel lbl_loginStatus;
    private final String tabPageName = "Login";
    private final Icon tabPageIcon = null;
    private final String tabPageTip = "Login";

    private ModelBase settings;

    public LoginTabPageView() {
        this.setVisible(false);
        this.viewModel = new LoginTabPageViewModel(this);
    }

    public LoginTabPageView(ModelBase model) {
        this.setVisible(false);
        this.settings = model;
        this.viewModel = new LoginTabPageViewModel(this);
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        // TODO:
        System.out.println(sender.toString() + e.getPropertyName());
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
}
