/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

import Primitives.MessageRecord;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * The ClientHandler class represents a server-side component responsible for handling communication with a client.
 * It implements the Runnable interface to handle messages in a separate thread.
 */
public class ClientHandler implements Runnable {
    /**
     * A list of all active client handlers.
     */
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String clientUsername;

    /**
     * Constructs a ClientHandler object for a given socket connection.
     *
     * @param socket The client's socket connection.
     */
    public ClientHandler(Socket socket) {
        try {
            // Initialization of input and output streams
            this.socket = socket;
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());

            // Read the initial message from the client
            Object message = (this.ois.readObject());
            if (message instanceof MessageRecord) {
                MessageRecord msg = (MessageRecord) message;
                this.clientUsername = msg.user();
                Server.messages.add(msg);
                Server.storeMessage(msg);
            } else {
                closeEverything(socket, ois, oos);
            }

            // Send chat history to the client
            for (int index = 0; index < Server.messages.size(); index++) {
                MessageRecord msg = Server.messages.get(index);
                if (msg.user().equals(this.clientUsername) && msg.message() == null) {
                    sendChatHistory(index);
                    break;
                }
            }

            // Add the client handler to the list
            clientHandlers.add(this);

            // Notify other clients about the new client's entry
            Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
            String str = clientUsername + " has entered the chat.";
            System.out.println(str);
            MessageRecord msg = new MessageRecord("SERVER", str, timestamp);
            broadcastMessage(msg);
            Server.messages.add(msg);
            Server.storeMessage(msg);
        } catch (IOException | ClassNotFoundException e) {
            closeEverything(socket, ois, oos);
        }
    }

    /**
     * The run method to handle incoming messages from the client.
     */
    @Override
    public void run() {
        Object message;
        while (socket.isConnected()) {
            try {
                // Read and broadcast incoming messages
                message = ois.readObject();
                if (message instanceof MessageRecord) {
                    MessageRecord msg = (MessageRecord) message;
                    System.out.println(msg.user() + ": " + msg.message());
                    broadcastMessage(msg);
                    Server.messages.add(msg);
                    Server.storeMessage(msg);
                }
            } catch (IOException | ClassNotFoundException e) {
                closeEverything(socket, ois, oos);
                break;
            }
        }
    }

    /**
     * Broadcasts a message to all connected clients except the sender.
     *
     * @param message The message to broadcast.
     */
    public void broadcastMessage(MessageRecord message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.oos.writeObject(message);
                    clientHandler.oos.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, ois, oos);
            }
        }
    }

    /**
     * Sends the chat history to the client starting from a specified index.
     *
     * @param index The starting index of the chat history.
     */
    public void sendChatHistory(int index) {
        for (int i = index; i < Server.messages.size(); i++) {
            MessageRecord msg = Server.messages.get(i);
            if (msg.message() != null && !msg.message().equals(clientUsername + " has entered the chat.")
                    && !msg.message().equals(clientUsername + " has left the chat.")) {
                try {
                    if (i < Server.messages.size() - 1)
                        Thread.sleep(10);
                    oos.writeObject(Server.messages.get(i));
                    oos.flush();
                } catch (IOException | InterruptedException e) {
                    closeEverything(socket, ois, oos);
                }
            }
        }
    }

    /**
     * Removes the current client handler from the list and broadcasts a message about the client leaving.
     */
    public void removeClientHandler() {
        clientHandlers.remove(this);
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        String message = clientUsername + " has left the chat.";
        System.out.println(message);
        MessageRecord msg = new MessageRecord("SERVER", message, timestamp);
        broadcastMessage(msg);
        Server.messages.add(msg);
        Server.storeMessage(msg);
    }

    /**
     * Closes all associated resources including socket, input stream, and output stream.
     *
     * @param socket The socket to close.
     * @param ois    The ObjectInputStream to close.
     * @param oos    The ObjectOutputStream to close.
     */
    public void closeEverything(Socket socket, ObjectInputStream ois, ObjectOutputStream oos) {
        removeClientHandler();
        try {
            if (ois != null) {
                ois.close();
            }
            if (oos != null) {
                oos.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
