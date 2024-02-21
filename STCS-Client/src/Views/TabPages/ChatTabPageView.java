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

public class ChatTabPageView extends ViewBase implements ITabPageView {
    private final String tabPageName = "Chat";
    private final Icon tabPageIcon = null;
    private final String tabPageTip = "Chats";
    private JPanel basePanel;
    private JButton btn_sendMsg;
    private JTextField txtField_msg;
    private JPanel pnl_msg;
    private JScrollPane pnlScroll_messages;
    private JLabel lbl_invalidMsg;
    private JPanel pnl_messages;
    private ChatController chatController;

    public ChatTabPageView(ChatController chatController, String username) {
        this.setVisible(false);
        this.pnl_messages.setLayout(new GridLayout(0, 1, 0, 5));
        this.chatController = chatController;
        this.lbl_invalidMsg.setText("");
        INotifyValidationChanged sender = this;
        this.btn_sendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatController.sendMessage(sender, username, txtField_msg.getText());
            }
        });
    }

    @Override
    public void onPropertyChanged(Object sender, PropertyChangedEventArgs e) {
        switch (e.getPropertyName()) {
            case "messages":
                List<MessageRecord> messages = (List<MessageRecord>)(e.getPropertyValue());
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
        if(e.isValid())
            this.lbl_invalidMsg.setText("");
        else
            this.lbl_invalidMsg.setText(e.getValidationMessage());
    }
}
