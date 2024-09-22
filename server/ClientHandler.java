import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;

/*
 * This class handles threading of multiple client connections
 */

class ClientHandler extends Thread {
    private static final File words_file = new File("./server/resource/words.txt");   // path to text file
    private static ArrayList<String> words = new ArrayList<String>();   // arraylist to retrieve list of words

    private Socket client_socket;
    PrintWriter out;
    BufferedReader in;

    public ClientHandler(Socket socket) {
        client_socket = socket;

        // parse text file into arraylist
        try (Scanner scan = new Scanner(words_file)) {
            while (scan.hasNextLine()) {
                words.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            client_socket.setSoTimeout(15000);   // set timeout to 15 seconds

            out = new PrintWriter(client_socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
            Hangman game = new Hangman(words);
            out.println(game.start());

            String input, output;   // input is what was received from client, output is to be sent to client
            while ((input = in.readLine()) != null) {
                if (input.equals("RESTART")) {      // if input is a restart request,
                    out.println(game.start());               // start the game anew
                } else {
                    output = game.play(input);
                    out.println(output);
                }

                client_socket.setSoTimeout(15000);   // refresh timeout
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Client timed out: " + client_socket.getInetAddress());  // catch client timeouts
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