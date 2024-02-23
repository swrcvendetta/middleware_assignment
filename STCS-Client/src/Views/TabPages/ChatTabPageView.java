/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */


/**
 * The ChatTabPageView class represents a tab page for displaying chat-related content in the main window.
 * It extends ViewBase and implements the ITabPageView interface.
 */
package Views.TabPages;

import Controller.ChatController;
import Events.PropertyChangedEventArgs;
import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Interfaces.ITabPageView;
import Primitives.MessageRecord;
import Views.Controls.MessageControl;
import Views.ViewBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The ChatTabPageView class provides a tab page for managing chat-related content in the main window.
 * It includes UI components such as buttons, text fields, and message panels.
 */
public class ChatTabPageView extends ViewBase implements ITabPageView, INotifyValidationChanged {

    /**
     * Name of the chat tab page.
     */
    private final String tabPageName = "Chat";

    /**
     * Icon for the chat tab page.
     */
    private final Icon tabPageIcon = null;

    /**
     * Tooltip for the chat tab page.
     */
    private final String tabPageTip = "Chats";

    /**
     * Base panel containing chat-related UI components.
     */
    private JPanel basePanel;

    /**
     * Button for sending messages.
     */
    private JButton btn_sendMsg;

    /**
     * Text field for entering messages.
     */
    private JTextField txtField_msg;

    /**
     * Panel for displaying messages.
     */
    private JPanel pnl_msg;

    /**
     * Scroll pane for messages panel.
     */
    private JScrollPane pnlScroll_messages;

    /**
     * Label for displaying invalid message information.
     */
    private JLabel lbl_invalidMsg;

    /**
     * Panel for holding message controls.
     */
    private JPanel pnl_messages;

    /**
     * Chat controller managing the chat-related functionality.
     */
    private ChatController chatController;

    /**
     * Constructs a ChatTabPageView object with the provided ChatController and username.
     *
     * @param chatController The ChatController managing chat-related functionality.
     * @param username       The username associated with the chat.
     */
    public ChatTabPageView(ChatController chatController, String username) {
        this.setVisible(false);
        this.pnl_messages.setLayout(new GridLayout(0, 1, 0, 5));
        this.chatController = chatController;
        this.lbl_invalidMsg.setText("");
        INotifyValidationChanged sender = this;

        // ActionListener for the send message button
        this.btn_sendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatController.sendMessage(sender, username, txtField_msg.getText());
            }
        });
    }

    /**
     * Handles property changes in the chat tab page view.
     *
     * @param sender The object sending the property change.
     * @param e      The PropertyChangedEventArgs containing information about the property change.
     */
    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "messages":
                List<MessageRecord> messages = (List<MessageRecord>) (e.getPropertyValue());
                for (MessageRecord msg : messages)
                    System.out.println(msg);
                break;
            case "new_message":
                MessageRecord msg = (MessageRecord) (e.getPropertyValue());
                this.pnl_messages.add(new MessageControl(msg).getControl(), 0);
                this.pnl_messages.revalidate();
                break;
            case "message_failed":
                MessageRecord failed_msg = (MessageRecord) (e.getPropertyValue());
                System.out.println(failed_msg);
                break;
            default:
                break;
        }
    }

    /**
     * Gets the name of the chat tab page.
     *
     * @return The name of the chat tab page.
     */
    @Override
    public String getTabPageName() {
        return this.tabPageName;
    }

    /**
     * Gets the JPanel representing the chat tab page.
     *
     * @return The JPanel representing the chat tab page.
     */
    @Override
    public JPanel getTabPagePanel() {
        return this.basePanel;
    }

    /**
     * Gets the icon for the chat tab page.
     *
     * @return The icon for the chat tab page.
     */
    @Override
    public Icon getTabPageIcon() {
        return this.tabPageIcon;
    }

    /**
     * Gets the tooltip for the chat tab page.
     *
     * @return The tooltip for the chat tab page.
     */
    @Override
    public String getTabPageTip() {
        return this.tabPageTip;
    }

    /**
     * Handles validation changes in the chat tab page view.
     *
     * @param sender The object sending the validation change.
     * @param e      The ValidationChangedEventArgs containing information about the validation change.
     */
    @Override
    public void onValidationChanged(Object sender, ValidationChangedEventArgs e) {
        if (e.isValid()) {
            this.lbl_invalidMsg.setText("");
            this.txtField_msg.setText("");
        } else
            this.lbl_invalidMsg.setText(e.getValidationMessage());
    }
}
