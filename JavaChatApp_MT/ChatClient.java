// Imports 
import java.io.*;
import java.net.*;

// User side
public class ChatClient {
    public static void main(String[] args) {
        // Host chat app over server
        String host = "localhost";
        int port = 12345;

        // Test of client-server connection works
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Output verification
            System.out.println("Connection Successful");

            // Display menu for user
            String menu = """
                    Welcome to the Medical Center! Please select an option:
                    1. Appointment Scheduling 
                    2. Medical Records
                    3. Billing
                    4. Generalized Q & A
                    5. Exit
                    """;
            System.out.println(menu);

            /* Flow Control: while loop to keep menu running and process user input */
            while (true) {
                System.out.print("Enter an option: ");
                String option = consoleInput.readLine();

                // Flow control: if user selects to quit, stop running program
                if (option.equals("5")) {
                    System.out.println("Exiting program...");
                    break;
                }

                // If user types invalid option, show warning 
                if (!option.matches("[1-4]")) {
                    System.out.println("Invalid Input. Enter an option above.");
                    continue;
                }

                // Display to user which option they chose
                switch (option) {
                    case "1" -> System.out.println("Welcome to Appointment Scheduling Support.");
                    case "2" -> System.out.println("Welcome to Medical Records Support.");
                    case "3" -> System.out.println("Welcome to Billing Support.");
                    case "4" -> System.out.println("Welcome to General Q&A Support.");
                }

                // Message showing currently connected client to server
                System.out.println("Starting chat...");
                System.out.println("Type 'exit' to end chat.");

                // Chat interface 
                while (true) {
                    // 1 - Client side sends message to server
                    System.out.print("Client: ");
                    String clientMessage = consoleInput.readLine();
                    output.println(clientMessage); // Send message to server
                    
                    if (clientMessage.equalsIgnoreCase("exit")) {
                        System.out.println("Exiting chat...");
                        break;
                    }

                    // 2 - Wait for response from server
                    String serverResponse = in.readLine();
                    if (serverResponse == null || serverResponse.equalsIgnoreCase("exit")) {
                        System.out.println("Server Disconnected.");
                        break;
                    }

                    // Otherwise, display server's message correctly
                    System.out.println("Server: " + serverResponse);
                }
                
                // Exit the loop after chat ends
                break;
            }
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
        }
    }
}
