//imports 
import java.io.*;
import java.net.*;
//user side
public class ChatClient {
    public static void main(String[] args ) {
        //host chat app over server
        String host = "localhost";
        int port = 12345;

        //test of client server connection works
        try (Socket socket = new Socket(host, port)) {
            //output verification
            System.out.println("Connection Successful");

            //turn bytes to readable text entering from server
            BufferedReader In = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Sends message over the network via socket (flush = sending)
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            //Processes input from user when they type message in terminal
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            //Display menu for user
            String menu = """
                    Welcome to the Medical Center! Please select an option:
                    1. Appointment Scheduling 
                    2. Medical Records
                    3. Billing
                    4. Generalized Q & A
                    5. Exit
                    """;
           System.out.println(menu);

           /*Flow Control: while loop to keep menu running and process user input */
           while (true) {
            System.out.print("Enter an option: ");
            String option = consoleInput.readLine();

            //flow control: if user selects to quit, stop runing program with break
            if (option.equalsIgnoreCase("Q")) {
                System.out.println("Exiting program...");
                break;
            }

            //if user types invalid option, put warning 
            if (!option.equals("1")&&!option.equals("2")&&!option.equals("3")&&!option.equals("4")) 
            {
                throw new IllegalArgumentException("Invalid Input. Enter an option above.");
            }

            //Display to user as line for text option they chose
            switch (option) {
                case "1" -> System.out.println("Welcome to Appointment Scheduling Support.");
                case "2" -> System.out.println("Welcome to Medical Records Support.");
                case "3" -> System.out.println("Welcome to Billing Support.");
                case "4" -> System.out.println("Welcome to General Q&A Support.");
                
            }
            //message showing currently connect client to server
            System.out.println("Starting chat...");
            System.out.println("Type 'exit' to end chat.");

            //Chat interface 
            while (true){
                //1- Client side send message to server
                System.out.print("Client:");
                String clientMessage = consoleInput.readLine();
                output.println(clientMessage);//shows client message on console for chat
                if (clientMessage.equalsIgnoreCase("exit")){
                    System.out.println("Exiting Chat");
                    break;
                }
                //2- Wait for response from server
                String serverResponse = In.readLine();
                if (serverResponse == null || serverResponse.equalsIgnoreCase("exit")) {
                    System.out.println("Server Disconnected");
                    break;
                }
                //otherwise display server's message
                output.println("Server:" + serverResponse);
            }
            //exit loop after chat ends from inputs that activate this 
            break;
           }
           socket.close(); // close connection
        //exception handling
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        } catch (IOException ex){
            System.out.println("Client error:" + ex.getMessage());  
        }
    }
    
}


