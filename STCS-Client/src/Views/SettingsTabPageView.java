package Views;

import Events.PropertyChangedEventArgs;
import Interfaces.ITabPageView;
import ViewModels.SettingsTabPageViewModel;

import javax.swing.*;

public class SettingsTabPageView extends ViewBase implements ITabPageView {
    private JPanel basePanel;
    private JPanel pnl_form;
    private JTextField txtField_address;
    private JTextField txtField_port;
    private JLabel lbl_address;
    private JLabel lbl_port;
    private final String tabPageName = "Settings";
    private final Icon tabPageIcon = null;
    private final String tabPageTip = "Settings";

    public SettingsTabPageView() {
        this.setVisible(false);
        this.viewModel = new SettingsTabPageViewModel(this);
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "address":
                this.txtField_address.setText(((String)e.getPropertyValue()));
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
    public String getIP() {
        return null;
    }
    public int getPort() {
        return -1;
    }
}
