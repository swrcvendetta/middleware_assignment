import Views.MainWindowView;
import Views.ViewBase;

import javax.swing.*;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();
    }
    public Client() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewBase view = new MainWindowView();
            }
        });
    }
}