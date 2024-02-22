package Views;

import Controller.ChatController;
import Controller.LoginController;
import Controller.SettingsController;
import Events.PropertyChangedEventArgs;
import Events.ValidationChangedEventArgs;
import Models.ChatModel;
import Models.LoginModel;
import Models.SettingsModel;
import Views.TabPages.ChatTabPageView;
import Views.TabPages.LoginTabPageView;
import Views.TabPages.SettingsTabPageView;

import javax.swing.*;
import java.net.Socket;

public class MainWindowView extends ViewBase {
    private JPanel basePanel;
    private JTabbedPane tabPanel;
    private SettingsTabPageView settingsPage;
    private LoginTabPageView loginPage;
    private ChatTabPageView chatPage;

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
        this.settingsPage = new SettingsTabPageView(settingsController);
        LoginModel loginModel = new LoginModel();
        LoginController loginController = new LoginController(loginModel, settingsModel);
        this.loginPage = new LoginTabPageView(loginController);
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
                if(e.getPropertyValue() != null) {
                    Socket socket = (Socket) (e.getPropertyValue());
                    String user = this.loginPage.getUsername();
                    this.tabPanel.removeTabAt(0);
                    ChatModel chatModel = new ChatModel(user, socket);
                    ChatController chatController = new ChatController(chatModel);
                    this.chatPage = new ChatTabPageView(chatController, user);
                    chatModel.subscribe(chatPage);
                    this.tabPanel.insertTab(chatPage.getTabPageName(), chatPage.getTabPageIcon(), chatPage.getTabPagePanel(), chatPage.getTabPageTip(), 0);
                    this.tabPanel.setSelectedIndex(0);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {

    }
}
