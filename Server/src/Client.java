import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;

import java.net.Socket;
import java.util.Scanner;

//import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;


public class Client {
	public static boolean userCommandHandler(DataOutputStream out){
		System.out.println("Enter command: ");
		Scanner userCommandInput = new Scanner(System.in);
		String userStringCommand = userCommandInput.nextLine();
		if (userStringCommand.equals("close")){
			System.out.println("Closing");
			return false;
		}
		try {
			out.writeUTF(userStringCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}


private static Socket socket;
		
	public static void main(String[] args) throws Exception {

//		System.out.println("Client");
//		System.out.println("Entrez l'adresse IP du serveur : ");
		Scanner userInput = new Scanner(System.in);
//	    String serverAddress = userInput.nextLine();
//	    Verifier.adressVerifier(serverAddress);
//	    
//	    System.out.println("Entrez le port d'ecoute : ");
//		int port = userInput.nextInt();
//		Verifier.portVerifier(port);
		
		int port = 5006;
		String serverAddress = "127.1.1.1";

	    
		
		// // Création d'une nouvelle connexion aves le serveur
		// socket = new Socket(serverAddress, port);
		socket = new Socket(serverAddress, port);
		System.out.format("Serveur lancé sur [%s:%d]", serverAddress, port);
		


		
		// Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
		DataInputStream in = new DataInputStream(socket.getInputStream());
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		// Attente de la réception d'un message envoyé par le, server sur le canal
		String helloMessageFromServer = in.readUTF();
		System.out.println(helloMessageFromServer);
		

		out.writeUTF("bonjour");
		out.flush();
		
//		String prompt;
//		
//		while (true) {
//			prompt = userInput.nextLine();
//			System.out.println("sending");
//			out.writeUTF(prompt);
//			out.flush();
//			
//			if (prompt ==  "quit") {
//				break;
//			}
//			
//			System.out.println(in.readUTF());
//			
//		}
//		
////		while (userCommandPrompt()){}
//
//
//
//		while (userCommandHandler(out)){}
		
		Commands commands = new Commands(socket, in, out);
		String command, commandPrefix;
		String path = System.getProperty("user.dir");
		String[] messageParts;
		boolean exit = false;
		
		while(!exit){
			//reçoit messages du client
			System.out.print(path + "> ");
			command = userInput.nextLine();	
			
			messageParts = command.split("\\s+");
//			System.out.println(messageParts[0]);
			commandPrefix = messageParts[0];
			
			
			switch (commandPrefix) {
				case "cd":
				case "mkdir":
				case "ls":
					path = commands.sendCommand(command);
					break;
					
				case "upload":
					commands.upload(command);
					break;
					
				case "download":
					commands.download(command);
					break;
				
				case "exit":
					exit = true;
					break;
			
				default:
					out.writeUTF(commandPrefix + "invalide réessayer");
			}
			
			

		// fermeture de La connexion avec le serveur
		}
		socket.close();
	}
}
