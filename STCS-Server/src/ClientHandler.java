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
            }
            else {
                closeEverything(socket, ois, oos);
            }
            clientHandlers.add(this);
            Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
            String str = clientUsername + " has entered the chat.";
            MessageRecord msg = new MessageRecord("SERVER", str, timestamp);
            broadcastMessage(msg);
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
                    broadcastMessage(msg);
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
    public void removeClientHandler() {
        clientHandlers.remove(this);
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        String message = clientUsername + " has left the chat.";
        MessageRecord msg = new MessageRecord("SERVER", message, timestamp);
        broadcastMessage(msg);
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
