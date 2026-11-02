import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Level 3 - Task 1 (Server part): Chat Application
 * Run this first. Many ChatClient programs can connect; every message is broadcast to all.
 */
public class ChatServer {

    static final int PORT = 5000;
    // Thread-safe list of all connected clients
    static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.send(message);
            }
        }
    }

    // One ClientHandler thread per connected user
    static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;
        private String name = "Unknown";

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        void send(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter your name:");
                String firstLine = in.readLine();
                name = (firstLine == null || firstLine.isBlank()) ? "User" + socket.getPort() : firstLine.trim();

                clients.add(this);
                System.out.println(name + " joined. Online: " + clients.size());
                broadcast("*** " + name + " joined the chat ***", this);
                out.println("Welcome, " + name + "! Type messages. Type /quit to leave.");

                String line;
                while ((line = in.readLine()) != null) {
                    if (line.equalsIgnoreCase("/quit")) break;
                    broadcast(name + ": " + line, this);
                }
            } catch (IOException e) {
                System.out.println("Connection problem with " + name + ": " + e.getMessage());
            } finally {
                clients.remove(this);
                broadcast("*** " + name + " left the chat ***", this);
                System.out.println(name + " left. Online: " + clients.size());
                try { socket.close(); } catch (IOException ignored) { }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Chat server started on port " + PORT + " ...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
