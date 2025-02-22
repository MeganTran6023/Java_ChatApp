import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args){
        //port to connect
        int port = 12345;

        //test of client server connection works
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            //shows on terminal area
            System.out.println("Server is listening on port " + port);

            //if incoming connection accepted, print out string
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            //turn bytes to readable text entering from server
            BufferedReader In = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Sends message over the network via socket (flush = sending)
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            //Processes input from user when they type message in terminal
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String clientMessage;//define variable to a string
            
            //loop to carry out server side of chat
            while (true) {
                //display client message to server side
                clientMessage = In.readLine();
                //if type exit -> disconnect
                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected");
                    break;
                }
            System.out.println("Client: " + clientMessage);

            //Responding to client (get server side message)
            System.out.print("Server: ");
            String serverResponse = consoleInput.readLine();
            output.println(serverResponse);
            //if type exit, then exit chat
            if (serverResponse.equalsIgnoreCase("exit")){
                System.out.println("Exiting chat");
                break;
            }

            }
            socket.close();
            //exception handling
            } catch (IOException ex) {
        System.out.println("Server error: " + ex.getMessage());
    }
}
}
