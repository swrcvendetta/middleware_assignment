package Views.Controls;

import Primitives.MessageRecord;

import javax.swing.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MessageControl extends ControlBase {
    private JLabel lbl_timestamp;
    private JLabel lbl_message;
    private JLabel lbl_sender;
    private JPanel basePanel;

    public MessageControl(MessageRecord message) {
        super();
        this.lbl_sender.setText(message.user());
        this.lbl_message.setText(message.message());
        this.lbl_timestamp.setText(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(message.timestamp()));
    }
    @Override
    public JPanel getControl() {
        return this.basePanel;
    }
}
