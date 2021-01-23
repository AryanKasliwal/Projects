import java.awt.List;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class server_source_code {
	
	// Fixed port number 16000 as required.
	public static final int port = 16000;

	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		
		// ArrayList to store all client texts and send them back when client requires.
		ArrayList <String> clientTexts = new ArrayList <String> ();
		
		
		// Initialize server port
		ServerSocket server_socket = new ServerSocket(port); 
		
		
		// Blocking call - Server waits until a connection is made and then accepts the connection
		Socket SocketToClient = server_socket.accept(); 
		
		
		
		/*  Server communicates (input and output) with the client only.
		 *  'input' is a BufferedReader used to take input from the client
		 *  'toCLient' is a PrintWriter used to send output to the client
		 */
		BufferedReader input = new BufferedReader(new InputStreamReader(SocketToClient.getInputStream()));
		PrintWriter toClient = new PrintWriter(SocketToClient.getOutputStream(), true);
		
		
		// Receive the command from the client.
		String clientCommand = input.readLine();
		toClient.println("Client: " + clientCommand);
		
		
		/*  Enter a while loop to keep repeating commands until client sends 'QUIT'.
		 *  Each case for different commands have been implemented in this while loop.
		 */
		while (!clientCommand.contentEquals("QUIT")) {
			
			// If the client sends command 'POST'.
			if (clientCommand.contentEquals("POST")) {
				
				// Read text input from the client.
				String clientText = input.readLine();
				toClient.println("Client: " + clientText);
				clientTexts.add(clientText);
				
				
				/*  Enter a while loop to continuously receive texts from the user until '.' is received. 
				 *  Append each text to ArrayList ('clientTexts').
				 */
				while (!clientText.contentEquals(".")) {
					
					clientText = input.readLine();
					toClient.println("Client: " + clientText);
					if (!clientText.contentEquals(".")) {
						clientTexts.add(clientText);
					}
					
					// Once a '.' is received, exit the while loop for 'POST' command.
					else if (clientText.contentEquals(".")) {
						toClient.println("Server: Ok");
						break;
					}
				}
			}
			
			// If the client sends command 'READ'.
			else if (clientCommand.contentEquals("READ")) {
				
				// Output elements in ArrayList ('clientTexts') one by one to the server.
				for (String i : clientTexts) {
					toClient.println("Server: " + i);
				}
			}
			
			// If the client sends command "QUIT".
			else if (clientCommand.contentEquals("QUIT")) {
				toClient.println("Server: Ok");
			}
			
			// If the client sends a command that is not recognized
			else {
				toClient.println("server: ERROR - Command not understood");
			}
			
			// Keep taking in new commands until the command is not "QUIT".
			clientCommand = input.readLine();
			
			toClient.println("Client: " + clientCommand);
		}
		
		PrintWriter output = new PrintWriter(SocketToClient.getOutputStream());
		output.println("Client: ");
	}

}
