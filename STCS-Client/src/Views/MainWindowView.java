package Views;

import Controller.LoginController;
import Controller.SettingsController;
import Events.PropertyChangedEventArgs;
import Events.ValidationChangedEventArgs;
import Models.LoginModel;
import Models.SettingsModel;

import javax.swing.*;

public class MainWindowView extends ViewBase {
    private JPanel basePanel;
    private JTabbedPane tabPanel;

    public MainWindowView() {
        super();
        //this.viewModel = new MainWindowViewModel(this);
        this.setContentPane(this.basePanel);
        this.setTitle("MainWindowView");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Make settings-tab first, so we can use information on settings with our login-tab
        SettingsModel settingsModel = new SettingsModel();
        SettingsController settingsController = new SettingsController(settingsModel);
        SettingsTabPageView settingsPage = new SettingsTabPageView(settingsController);
        LoginModel loginModel = new LoginModel();
        LoginController loginController = new LoginController(loginModel, settingsModel);
        LoginTabPageView loginPage = new LoginTabPageView(loginController);
        settingsModel.subscribe(settingsPage);
        //settingsModel.subscribe(loginPage);
        loginModel.subscribe(loginPage);
        loginModel.subscribe(this);

        this.tabPanel.addTab(loginPage.getTabPageName(), loginPage.getTabPageIcon(), loginPage.getTabPagePanel(), loginPage.getTabPageTip());
        this.tabPanel.addTab(settingsPage.getTabPageName(), settingsPage.getTabPageIcon(), settingsPage.getTabPagePanel(), settingsPage.getTabPageTip());
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "connected":
                this.tabPanel.removeTabAt(0);
                ChatTabPageView chatPage = new ChatTabPageView();
                this.tabPanel.insertTab(chatPage.getTabPageName(), chatPage.getTabPageIcon(), chatPage.getTabPagePanel(), chatPage.getTabPageTip(), 0);
                this.tabPanel.setSelectedIndex(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {

    }
}
