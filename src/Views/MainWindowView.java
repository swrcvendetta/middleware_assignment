package Views;

import Events.PropertyChangedEventArgs;
import Interfaces.ITabPageView;
import ViewModels.MainWindowViewModel;

import javax.swing.*;

public class MainWindowView extends ViewBase {
    public MainWindowViewModel model;
    private JPanel basepanel;
    private JTabbedPane tabcontrol;

    public MainWindowView() {
        super();
        this.model = new MainWindowViewModel(this);
        this.setContentPane(this.basepanel);
        this.setTitle("MainWindowView");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        ITabPageView loginPage = new LoginTabPageView();
        ITabPageView chatPage = new ChatTabPageView();
        ITabPageView settingsPage = new SettingsTabPageView();

        this.tabcontrol.addTab(loginPage.getTabPageName(), loginPage.getTabPageIcon(), loginPage.getTabPagePanel(), loginPage.getTabPageTip());
        this.tabcontrol.addTab(chatPage.getTabPageName(), chatPage.getTabPageIcon(), chatPage.getTabPagePanel(), chatPage.getTabPageTip());
        this.tabcontrol.addTab(settingsPage.getTabPageName(), settingsPage.getTabPageIcon(), settingsPage.getTabPagePanel(), settingsPage.getTabPageTip());
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        System.out.println(sender.toString() + e.getPropertyName());
    }
}
