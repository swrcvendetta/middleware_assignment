import Primitives.MessageRecord;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    // static so it belongs to the class and not each object independently
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.oos = new ObjectOutputStream(socket.getOutputStream());
            this.ois = new ObjectInputStream(socket.getInputStream());
            Object message = (this.ois.readObject());
            if(message instanceof MessageRecord) {
                MessageRecord msg = (MessageRecord) message;
                this.clientUsername = msg.user();
                Server.messages.add(msg);
                Server.storeMessage(msg);
            }
            else {
                closeEverything(socket, ois, oos);
            }
            for (int index = 0; index < Server.messages.size(); index++) {
                MessageRecord msg = Server.messages.get(index);
                if(msg.user().equals(this.clientUsername) && msg.message() == null) {
                    sendChatHistory(index);
                    break;
                }
            }
            clientHandlers.add(this);
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

    @Override
    public void run() {
        Object message;
        while(socket.isConnected()) {
            try {
                message = ois.readObject();
                if(message instanceof MessageRecord) {
                    MessageRecord msg = (MessageRecord) message;
                    System.out.println(msg.user() + ": " + msg.message());
                    broadcastMessage(msg);
                    Server.messages.add(msg);
                    Server.storeMessage(msg);
                }
            }
            catch (IOException | ClassNotFoundException e) {
                closeEverything(socket, ois, oos);
                break;
            }
        }
    }
    public void broadcastMessage(MessageRecord message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if(!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.oos.writeObject(message);
                    clientHandler.oos.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, ois, oos);
            }
        }
    }
    public void sendChatHistory(int index) {
        for (int i = index; i < Server.messages.size(); i++) {
            MessageRecord msg = Server.messages.get(i);
                if(msg.message() != null && !msg.message().equals(clientUsername + " has entered the chat.")
                    && !msg.message().equals(clientUsername + " has left the chat."))
                try {
                    if(i < Server.messages.size() - 1)
                        Thread.sleep(10);
                    oos.writeObject(Server.messages.get(i));
                    oos.flush();
                } catch (IOException | InterruptedException e) {
                    closeEverything(socket, ois, oos);
                }
        }
    }
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
