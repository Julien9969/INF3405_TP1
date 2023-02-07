package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

//import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;


public class Client {

	private static Socket socket;
		
	public static void main(String[] args) throws Exception {
		System.out.println("Entrez l'adresse IP du serveur : ");
		Scanner userInput = new Scanner(System.in);
	    String serverAddress = userInput.nextLine();
	    Verifier.adressVerifier(serverAddress);
	    
	    System.out.println("Entrez le port d'ecoute : ");
		int port = userInput.nextInt();
		
		Verifier.portVerifier(port);
		
		InetAddress IP= InetAddress.getLocalHost();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd @ HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		
		// // Création d'une nouvelle connexion aves le serveur
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
			
		Commands commands = new Commands(socket, in, out);
		String command, commandPrefix;
		String path = "Server";
		String[] messageParts;
		boolean exit = false;
		
		while(!exit){
			//reçoit messages du client
			System.out.print("["+IP.getHostAddress()+":"+port+" - "+dtf.format(now)+"]"+": "+ path + " > ");
			command = userInput.nextLine();	
			
			messageParts = command.split("\\s+");
			commandPrefix = messageParts[0];
			
			if  (IsValidCommand(messageParts)) {
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
						System.out.print(commandPrefix + " invalide réessayer\n");
				}
			} else {System.out.print("commande invalide\n");}
			
		// fermeture de La connexion avec le serveur
		}
		socket.close();
		userInput.close();
	}
	
	static boolean IsValidCommand(String[] command) {
		return (command.length > 1) || (command[0].contains("ls") || command[0].contains("exit"));
	}
	
	
}
