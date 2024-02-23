/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

/**
 * The ChatModel class represents the model for managing chat-related functionality in the application.
 * It extends the ModelBase class and includes features for sending and receiving messages over a socket.
 */
package Models;

import Events.PropertyChangedEventArgs;
import Interfaces.INotifyPropertyChanged;
import Primitives.MessageRecord;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The ChatModel class provides functionality for managing chat-related operations.
 */
public class ChatModel extends ModelBase {

    /**
     * A list of MessageRecord objects representing the chat messages.
     */
    private final List<MessageRecord> messages;

    /**
     * The socket used for communication with the server.
     */
    private Socket socket;

    /**
     * The ObjectInputStream for reading objects from the socket.
     */
    private ObjectInputStream ois;

    /**
     * The ObjectOutputStream for writing objects to the socket.
     */
    private ObjectOutputStream oos;

    /**
     * The username associated with the chat model.
     */
    private String username;

    /**
     * Constructs a new instance of ChatModel with the specified username and socket.
     *
     * @param username The username associated with the chat model.
     * @param socket   The socket used for communication with the server.
     */
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

    /**
     * Subscribes an INotifyPropertyChanged listener to receive notifications about property changes.
     * Initializes the chat by sending an initial message and starting the message listening thread.
     *
     * @param listener The INotifyPropertyChanged listener to be subscribed.
     */
    @Override
    public void subscribe(INotifyPropertyChanged listener) {
        this.listeners.add(listener);

        // Send an initial message to the server
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        MessageRecord initialMessage = new MessageRecord(this.username, null, timestamp);
        sendMessage(initialMessage);

        // Receive messages from the server and notify subscribers
        listener.onPropertyChanged(this, new PropertyChangedEventArgs("messages", this.messages));
    }

    /**
     * Sends a message to the server and notifies subscribers about the result.
     *
     * @param message The MessageRecord to be sent.
     */
    public void sendMessage(MessageRecord message) {
        try {
            oos.writeObject(message);
            oos.flush();
            if (message.message() != null)
                addMessage(message);
        } catch (IOException e) {
            closeEverything(socket, ois, oos);
            for (INotifyPropertyChanged l : listeners)
                l.onPropertyChanged(this, new PropertyChangedEventArgs("message_failed", message));
        }
    }

    /**
     * Listens for incoming messages from the server in a separate thread.
     */
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Object receivedMessage;
                while (socket.isConnected()) {
                    try {
                        receivedMessage = ois.readObject();
                        if (receivedMessage instanceof MessageRecord) {
                            MessageRecord msg = (MessageRecord) receivedMessage;
                            System.out.println(msg.message());
                            addMessage(msg);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        closeEverything(socket, ois, oos);
                    }
                }
            }
        }).start();
    }

    /**
     * Closes the socket, ObjectInputStream, and ObjectOutputStream.
     *
     * @param socket The socket to be closed.
     * @param ois    The ObjectInputStream to be closed.
     * @param oos    The ObjectOutputStream to be closed.
     */
    public void closeEverything(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            if (socket != null)
                socket.close();
            if (ois != null)
                ois.close();
            if (oos != null)
                oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new message to the list and notifies subscribers about the change.
     *
     * @param message The MessageRecord to be added.
     */
    private void addMessage(MessageRecord message) {
        this.messages.add(message);
        for (INotifyPropertyChanged l : listeners)
            l.onPropertyChanged(this, new PropertyChangedEventArgs("new_message", message));
    }
}
