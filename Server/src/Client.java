import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;


public class Client {
	public static boolean userCommandPrompt(){
		System.out.println("Enter command: ");
		Scanner userCommandInput = new Scanner(System.in);
		String userStringCommand = userCommandInput.nextLine();
		String [] splitStr = userStringCommand.split("\\s+");
		String firstInput = splitStr[0];
		System.out.println(firstInput);
		String close = "close";
		if (firstInput.equals(close)){
			System.out.println("Closing");
			return false;
		}	
		return true;
	}


private static Socket socket;
		
	public static void main(String[] args) throws Exception {

		System.out.println("Entrez l'adresse IP du serveur : ");
		Scanner userInput = new Scanner(System.in);
	    String serverAddress = userInput.nextLine();
	    Verifier.adressVerifier(serverAddress);
	    
	    System.out.println("Entrez le port d'ecoute : ");
		int port = userInput.nextInt();
		Verifier.portVerifier(port);
	    
		
		// Création d'une nouvelle connexion aves le serveur
		socket = new Socket(serverAddress, port);
		System.out.format("Serveur lancé sur [%s:%d]", serverAddress, port);
		
		// Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
		DataInputStream in = new DataInputStream(socket.getInputStream());
		
		// Attente de la réception d'un message envoyé par le, server sur le canal
		String helloMessageFromServer = in.readUTF();
		System.out.println(helloMessageFromServer);
		
		while (userCommandPrompt()){}


		// fermeture de La connexion avec le serveur
		socket.close();
	}
}
