import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;


public class Server {
    private static final int port = 3840;
    private static final File words_file = new File("server/resource/words.txt");   // change to relative path according to working directory
    private static final Random rand = new Random();    
    
    public static void main(String[] args) {
        // parse text file into arraylist
        try (Scanner scan = new Scanner(words_file)) {
            ArrayList<String> words = new ArrayList<String>();

            while (scan.hasNextLine()) {
                words.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // initialize sockets
        try (ServerSocket server_socket = new ServerSocket(port)) {
            // continuously listen for new connections
            while (true) {
                Socket client_socket = server_socket.accept();
                System.out.println("New connection");
                new ClientHandler(client_socket).start();   // make a thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
