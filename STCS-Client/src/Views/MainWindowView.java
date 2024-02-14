package Views;

import Events.PropertyChangedEventArgs;
import ViewModels.MainWindowViewModel;

import javax.swing.*;

public class MainWindowView extends ViewBase {
    private JPanel basePanel;
    private JTabbedPane tabPanel;

    public MainWindowView() {
        super();
        this.viewModel = new MainWindowViewModel(this);
        this.setContentPane(this.basePanel);
        this.setTitle("MainWindowView");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Make settings-tab first, so we can use information on settings with our login-tab
        SettingsTabPageView settingsPage = new SettingsTabPageView();
        LoginTabPageView loginPage = new LoginTabPageView(settingsPage.getViewModel().getModel());

        this.tabPanel.addTab(loginPage.getTabPageName(), loginPage.getTabPageIcon(), loginPage.getTabPagePanel(), loginPage.getTabPageTip());
        this.tabPanel.addTab(settingsPage.getTabPageName(), settingsPage.getTabPageIcon(), settingsPage.getTabPagePanel(), settingsPage.getTabPageTip());

        // ITabPageView chatPage = new ChatTabPageView();
        // this.tabPanel.addTab(chatPage.getTabPageName(), chatPage.getTabPageIcon(), chatPage.getTabPagePanel(), chatPage.getTabPageTip());
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        System.out.println(sender.toString() + e.getPropertyName());
    }
}
