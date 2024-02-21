package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import Primitives.MessageRecord;

import java.util.ArrayList;
import java.util.List;

public class ChatModel extends ModelBase {
    List<MessageRecord> messages;
    public ChatModel() {
        this.messages = new ArrayList<>();
    }
    @Override
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);
        //TODO: get messages from server first
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("messages", this.messages));
    }
    public void sendMessage(MessageRecord message) {
        //TODO: send message to server first
        boolean bMessageSent = true;
        if(bMessageSent)
            addMessage(message);
        else
            for (INotifyPropertyChanged l : listeners)
                l.onPropertyChanged(this, new PropertyChangedEventArgs("message_failed", message));
    }
    private void addMessage(MessageRecord message) {
        this.messages.add(message);
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("new_message", message));
    }
    public List<MessageRecord> getMessages() {
        return this.messages;
    }
    public MessageRecord getLastMessage() {
        return this.messages.get(this.messages.size() - 1);
    }
    public MessageRecord getMessageAt(int index) {
        return this.messages.get(index);
    }
}
