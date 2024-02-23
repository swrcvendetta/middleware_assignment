/*
 * Copyright (c) 2024 Christof Sobotta, Harz University of Applied Sciences
 * This work is licensed under the Creative Commons Attribution-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

import Primitives.MessageRecord;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The Server class represents a chat server that accepts incoming client connections.
 */
public class Server {

    private ServerSocket serverSocket;
    private final String _address = "localhost";
    private final int _port = 1337;
    private static final String chatsDirectory = "chats/";
    private static final String chatsFile = "/chats.xml";
    private static final String chatsValidationFile = "/chats.xsd";
    /**
     * Contains all received messages.
     */
    public static ArrayList<MessageRecord> messages = new ArrayList<>();

    /**
     * The main method to start the chat server.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException If an I/O error occurs while creating the server.
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }

    /**
     * Constructs a Server object and initializes the server socket.
     */
    public Server() {
        ServerSocket serverSocket = null;
        try {
            Inet4Address address = (Inet4Address) Inet4Address.getByName(_address);
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(address, _port));
        } catch (IOException e) {
            e.printStackTrace();
            closeServerSocket();
        }
        this.serverSocket = serverSocket;

        // When missing create directory and validation file
        MessageParser msgParser = new MessageParser();
        new File(chatsDirectory).mkdir();
        File chats = new File(chatsDirectory + chatsFile);
        File validation = new File(chatsDirectory + chatsValidationFile);
        if (!validation.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(chatsDirectory + chatsValidationFile));
                writer.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                        "\t<xs:element name=\"chatlog\">\n" +
                        "\t\t<xs:complexType>\n" +
                        "\t\t\t<xs:sequence>\n" +
                        "\t\t\t\t<xs:element maxOccurs=\"unbounded\" name=\"message-object\">\n" +
                        "\t\t\t\t\t<xs:complexType>\n" +
                        "\t\t\t\t\t\t<xs:sequence>\n" +
                        "\t\t\t\t\t\t\t<xs:element name=\"user\" type=\"xs:string\" />\n" +
                        "\t\t\t\t\t\t\t<xs:element name=\"message\" type=\"xs:string\" />\n" +
                        "\t\t\t\t\t\t\t<xs:element name=\"timestamp\" type=\"xs:string\" />\n" +
                        "\t\t\t\t\t\t</xs:sequence>\n" +
                        "\t\t\t\t\t</xs:complexType>\n" +
                        "\t\t\t\t</xs:element>\n" +
                        "\t\t\t</xs:sequence>\n" +
                        "\t\t</xs:complexType>\n" +
                        "\t</xs:element>\n" +
                        "</xs:schema>");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // Read messages
        if (chats.exists()) {
            if (msgParser.readFileFormat(chatsDirectory + chatsFile, chatsDirectory + chatsValidationFile)) {
                ArrayList<MessageRecord> messages = msgParser.parseAllObjects();
                if (messages != null)
                    Server.messages = messages;
            }
        }
    }

    /**
     * Starts the chat server and listens for incoming client connections.
     */
    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected.");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeServerSocket();
        }
    }

    /**
     * Closes the server socket.
     */
    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores a new message in the chat log.
     *
     * @param message The message to store.
     */
    public static void storeMessage(MessageRecord message) {
        MessageParser msgParser = new MessageParser();
        try {
            msgParser.writeFileFormat(chatsDirectory + chatsFile, Server.messages);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
