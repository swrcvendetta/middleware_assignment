/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The MainWindowView class represents the main window of the application.
 * It extends ViewBase and contains tabs for settings, login, and chat views.
 */
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

/**
 * The MainWindowView class provides the main window of the application with tabs for settings, login, and chat.
 */
public class MainWindowView extends ViewBase {

    /**
     * The base panel of the main window view.
     */
    private JPanel basePanel;

    /**
     * The tabbed pane containing settings, login, and chat tabs.
     */
    private JTabbedPane tabPanel;

    /**
     * The settings tab page view.
     */
    private SettingsTabPageView settingsPage;

    /**
     * The login tab page view.
     */
    private LoginTabPageView loginPage;

    /**
     * The chat tab page view.
     */
    private ChatTabPageView chatPage;

    /**
     * Constructs a new instance of MainWindowView, initializing the frame and creating tabs.
     */
    public MainWindowView() {
        super();

        // Set the content pane and configure the frame
        this.setContentPane(this.basePanel);
        this.setTitle("MainWindowView");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Create and configure settings, login, and chat tab pages
        SettingsModel settingsModel = new SettingsModel();
        SettingsController settingsController = new SettingsController(settingsModel);
        this.settingsPage = new SettingsTabPageView(settingsController);

        LoginModel loginModel = new LoginModel();
        LoginController loginController = new LoginController(loginModel, settingsModel);
        this.loginPage = new LoginTabPageView(loginController);
        settingsModel.subscribe(settingsPage);
        loginModel.subscribe(loginPage);
        loginModel.subscribe(this);

        // Add login and settings tabs to the tab panel
        this.tabPanel.addTab(loginPage.getTabPageName(), loginPage.getTabPageIcon(), loginPage.getTabPagePanel(), loginPage.getTabPageTip());
        this.tabPanel.addTab(settingsPage.getTabPageName(), settingsPage.getTabPageIcon(), settingsPage.getTabPagePanel(), settingsPage.getTabPageTip());
    }

    /**
     * Handles property change events, such as when the connection status changes.
     *
     * @param sender The object that triggered the event.
     * @param e      The PropertyChangedEventArgs containing information about the changed property.
     */
    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "connected":
                if (e.getPropertyValue() != null) {
                    Socket socket = (Socket) (e.getPropertyValue());
                    String user = this.loginPage.getUsername();

                    // Remove the login tab and add the chat tab
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

    /**
     * Handles validation change events.
     *
     * @param sender The object that triggered the event.
     * @param e      The ValidationChangedEventArgs containing information about the validation change.
     */
    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {
        // Handle validation change events if needed
    }
}
