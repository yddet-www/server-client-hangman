import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        try (
            Socket sock = new Socket("18.224.71.230", 3840);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromClient;

            while (true) {
                fromServer = in.readLine();

                // if game-over text is received, prompt restart
                if (fromServer.contains("You lose") || fromServer.contains("You win")) {
                    System.out.println(fromServer);
                    System.out.println("Would you like to start again? (Y/N)");
                    
                    fromClient = stdIn.readLine();

                    if (fromClient.toUpperCase().contains("Y")) {
                        System.out.println("Restart sent");
                        out.println("RESTART");             // send restart request
                    } else {
                        break;  // close connection
                    }
                } else {
                    System.out.println(fromServer); // print first message from server

                    fromServer = in.readLine();
                    System.out.println(fromServer); // print second message from server

                    fromClient = stdIn.readLine();  
                    out.println(fromClient);        // send client message to server
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}