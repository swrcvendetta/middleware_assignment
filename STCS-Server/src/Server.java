import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private static final String _address = "localhost";
    private static final int _port = 1337;

    public static void main(String[] args) throws IOException {
        Inet4Address address = (Inet4Address) Inet4Address.getByName(_address);
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(address, _port));
        Server server = new Server(serverSocket);
        server.startServer();
    }

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected.");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e) {

        }
    }

    public void closeServerSocket() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
