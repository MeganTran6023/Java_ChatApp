import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 12345; // Port number for the server
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String clientMessage;
            while (true) {
                // Step 1: Wait for a message from the client
                clientMessage = input.readLine();
                if (clientMessage == null || clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected");
                    break;
                }
                System.out.println("Client: " + clientMessage);

                // Step 2: Respond to the client
                System.out.print("Server: ");
                String serverResponse = consoleInput.readLine();
                output.println(serverResponse);
                if (serverResponse.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting chat");
                    break;
                }
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
        }
    }
}
