import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Level 3 - Task 1 (Client part): Chat Application
 * Start ChatServer first, then run this in two or more terminals.
 * Usage: java ChatClient [host] [port]   (default localhost 5000)
 */
public class ChatClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner keyboard = new Scanner(System.in)) {

            // Separate thread keeps printing messages that arrive from the server
            Thread reader = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    // socket closed - nothing to do
                }
                System.out.println("Disconnected from server.");
            });
            reader.setDaemon(true);
            reader.start();

            // Main thread reads what the user types and sends it
            while (keyboard.hasNextLine()) {
                String message = keyboard.nextLine();
                out.println(message);
                if (message.equalsIgnoreCase("/quit")) break;
            }
        } catch (IOException e) {
            System.out.println("Could not connect to " + host + ":" + port + " - is the server running?");
        }
    }
}
