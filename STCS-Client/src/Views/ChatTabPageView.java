package Views;

import Events.PropertyChangedEventArgs;
import Events.ValidationChangedEventArgs;
import Interfaces.ITabPageView;

import javax.swing.*;

public class ChatTabPageView extends ViewBase implements ITabPageView {
    private final String tabPageName = "Chat";
    private final Icon tabPageIcon = null;
    private final String tabPageTip = "Chats";
    private JPanel basePanel;
    private JRadioButton radioButton1;

    public ChatTabPageView() {
        this.setVisible(false);
        //this.viewModel = new ChatTabPageViewModel(this);
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        // TODO:
        System.out.println("Notify not implemented yet @ChatTabPageView");
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

    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {

    }
}
