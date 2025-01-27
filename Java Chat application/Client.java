import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String host = "localhost"; // Server address
        int port = 12345; // Port number for the server

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to the server");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Display the menu
            String menu = """
                Welcome to the support system! Please choose an option:
                1. Medical Records
                2. Billing
                3. Customer Service
                Q. Quit
                """;

            System.out.println(menu);

            while (true) {
                System.out.print("Enter your choice: ");
                String choice = consoleInput.readLine();

                if (choice.equalsIgnoreCase("Q")) {
                    System.out.println("Exiting the connection. Goodbye!");
                    break;
                }

                if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                    throw new IllegalArgumentException("Invalid input");
                }

                // Notify the server about the selected support type
                switch (choice) {
                    case "1" -> System.out.println("You selected Medical Records support.");
                    case "2" -> System.out.println("You selected Billing support.");
                    case "3" -> System.out.println("You selected Customer Service support.");
                }

                System.out.println("Starting chat with the server...");
                System.out.println("(Type 'exit' to quit the chat)");

                // Chat application starts here
                while (true) {
                    // Step 1: Send a message to the server
                    System.out.print("Client: ");
                    String clientMessage = consoleInput.readLine();
                    output.println(clientMessage);
                    if (clientMessage.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting chat");
                        break;
                    }

                    // Step 2: Wait for a response from the server
                    String serverResponse = input.readLine();
                    if (serverResponse == null || serverResponse.equalsIgnoreCase("exit")) {
                        System.out.println("Server disconnected");
                        break;
                    }
                    System.out.println("Server: " + serverResponse);
                }

                // Exit the loop after the chat ends
                break;
            }

            socket.close();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
        }
    }
}
