import java.net.ServerSocket;
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
        System.out.println(words_file.canRead());

        try {
            Scanner scan = new Scanner(words_file);
            ArrayList<String> words = new ArrayList<String>();

            while(scan.hasNextLine()) {
                words.add(scan.nextLine());
                System.out.println(words.getLast());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
