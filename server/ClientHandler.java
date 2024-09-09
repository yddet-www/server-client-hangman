import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * This class handles threading of multiple client connections
 */

class ClientHandler extends Thread {
    private Socket client_socket;
    PrintWriter out;
    BufferedReader in;

    public ClientHandler(Socket socket) {
        client_socket = socket;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(client_socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // makes sure that resources are closed
            try {
                if (in != null) {in.close();}
                if (out != null) {out.close();}
                if (client_socket != null) {client_socket.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}