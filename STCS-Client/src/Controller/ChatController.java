package Controller;

import Events.ValidationChangedEventArgs;
import Interfaces.INotifyValidationChanged;
import Models.ChatModel;
import Primitives.MessageRecord;

import java.sql.Timestamp;

public class ChatController extends ControllerBase {
    private ChatModel chatModel;
    public ChatController(ChatModel chatModel) {
        this.chatModel = chatModel;
        this.chatModel.listenForMessage();
    }
    private String validateMessage(String message) {
        // some validation-rules for port
        if(message.isEmpty())
            return "Message can not be empty.";
        if(message.length() > 200)
            return "Message can not contain more than 200 characters.";
        return null;
    }
    public void sendMessage(INotifyValidationChanged sender, String username, String message) {
        String MessageValid = validateMessage(message);
        sender.onValidationChanged(this, new ValidationChangedEventArgs("message", MessageValid == null, MessageValid));
        if(MessageValid == null) {
            System.out.println(message);
            Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
            MessageRecord msg = new MessageRecord(username, message, timestamp);
            this.chatModel.sendMessage(msg);
        }
    }
}
