import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("this is client code");

        Socket test = new Socket("localhost", 3840);
    }
}