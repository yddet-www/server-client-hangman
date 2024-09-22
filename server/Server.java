import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class Server {
    private static final int port = 3840;

    public static void main(String[] args) {
        // initialize sockets
        try (ServerSocket server_socket = new ServerSocket(port)) {
            // continuously listen for new connections
            while (true) {
                Socket client_socket = server_socket.accept();
                new ClientHandler(client_socket).start(); // make a thread and run it
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
