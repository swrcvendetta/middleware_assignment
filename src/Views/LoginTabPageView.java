package Views;

import Events.PropertyChangedEventArgs;
import Interfaces.ITabPageView;

import javax.swing.*;

public class LoginTabPageView extends ViewBase implements ITabPageView {
    private JPanel basePanel;
    private JButton button1;
    private final String tabPageName = "Login";
    private final Icon tabPageIcon = null;
    private final String tabPageTip = "Login";

    public LoginTabPageView() {
        this.setVisible(false);
    }
    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        // TODO:
        System.out.println("Notify not implemented yet @LoginTabPageView");
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
