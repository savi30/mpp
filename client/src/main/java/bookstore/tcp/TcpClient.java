package bookstore.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpClient {
    private String host;
    private int port;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(host, port);
             var is = new ObjectInputStream(socket.getInputStream());
             var os = new ObjectOutputStream(socket.getOutputStream())) {

            os.writeObject(request);

            return (Message)is.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new TcpException("client - exception connecting to server", e);
        }
    }
}
