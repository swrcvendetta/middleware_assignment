package Models;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientSocket {
    private static Socket _clientSocket;
    private static SocketAddress _connectEP;
    private static InputStream _in;
    private static OutputStream _out;
    private String _address;
    private int _port;
    public ClientSocket(String address, int port) {
        this._address = address;
        this._port = port;
    }
}
