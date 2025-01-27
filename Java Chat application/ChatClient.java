import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        String host = "localhost"; // Replace with server's IP address if remote
        int port = 12345; // Match the port with the server

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to the server!");

            // Streams for reading from and writing to the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Thread for reading messages from the server
            Thread serverReader = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("\rServer: " + serverMessage); // Print server message
                        System.out.print("Enter message: "); // Re-display prompt for user input
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            serverReader.start();

            // Main thread for user input
            String userInput;
            while (true) {
                System.out.print("Enter message: "); // Prompt for user input
                userInput = consoleInput.readLine(); // Read user input

                if (userInput == null || userInput.equalsIgnoreCase("Q")) {
                    System.out.println("Exiting chat...");
                    break; // Exit the loop if user types "Q"
                }

                out.println(userInput); // Send message to the server
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
