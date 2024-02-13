package Views;

import Events.PropertyChangedEventArgs;
import Interfaces.ITabPageView;

import javax.swing.*;

public class SettingsTabPageView extends ViewBase implements ITabPageView {
    private JPanel basePanel;
    private final String tabPageName = "Settings";
    private final Icon tabPageIcon = null;
    private final String tabPageTip = "Settings";

    public SettingsTabPageView() {
        this.setVisible(false);
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        // TODO:
        System.out.println("Notify not implemented yet @SettingsTabPageView");
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
}
