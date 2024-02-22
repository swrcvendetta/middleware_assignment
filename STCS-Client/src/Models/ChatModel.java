package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import Primitives.MessageRecord;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ChatModel extends ModelBase {
    private final List<MessageRecord> messages;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String username;
    public ChatModel(String username, Socket socket) {
        this.messages = new ArrayList<>();
        this.username = username;
        this.socket = socket;
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            closeEverything(socket, ois, oos);
        }
    }
    @Override
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);
        //TODO: get messages from server first
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        MessageRecord msg = new MessageRecord(this.username, null, timestamp);
        sendMessage(msg);
        // receive messages and add to 'this.messages'
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("messages", this.messages));
    }
    public void sendMessage(MessageRecord message) {
        try {
            oos.writeObject(message);
            oos.flush();
            if(message.message() != null)
                addMessage(message);
        } catch (IOException e) {
            closeEverything(socket, ois, oos);
            for (INotifyPropertyChanged l : listeners)
                l.onPropertyChanged(this, new PropertyChangedEventArgs("message_failed", message));
        }
    }
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Object message;
                while(socket.isConnected()) {
                    try {
                        message = ois.readObject();
                        if(message instanceof MessageRecord) {
                            MessageRecord msg = (MessageRecord) message;
                            System.out.println(msg.message());
                            addMessage(msg);
                        }
                    } catch(IOException | ClassNotFoundException e) {
                        closeEverything(socket, ois, oos);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            if(socket != null)
                socket.close();
            if(ois != null)
                ois.close();
            if(oos != null)
                oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
