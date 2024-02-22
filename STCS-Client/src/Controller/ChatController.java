package Controller;

import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Models.ChatModel;
import Models.SettingsModel;
import Primitives.MessageRecord;

import java.sql.Timestamp;

public class ChatController extends ControllerBase {
    private ChatModel chatModel;
    public ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }
    private boolean validateMessage(String message) {
        // some validation-rules for port
        if(message.isEmpty() || message.length() > 200)
            return false;
        return true;
    }
    private String getMessageValidationMessage(String message) {
        if(validateMessage(message)) {
            return null;
        }
        else {
            return "Message can not be empty.";
        }
    }
    public void sendMessage(INotifyValidationChanged sender, String username, String message) {
        boolean bMessageValid = validateMessage(message);
        String messageMsg = getMessageValidationMessage(message);
        sender.onValidationChanged(this, new ValidationChangedEventArgs("message", bMessageValid, messageMsg));
        if(bMessageValid) {
            Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
            MessageRecord msg = new MessageRecord(username, message, timestamp);
            this.chatModel.sendMessage(msg);
        }
    }
}
