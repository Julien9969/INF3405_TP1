import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;


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

		// System.out.println("Entrez l'adresse IP du serveur : ");
		// Scanner userInput = new Scanner(System.in);
	    // String serverAddress = userInput.nextLine();
	    // Verifier.adressVerifier(serverAddress);
	    
	    // System.out.println("Entrez le port d'ecoute : ");
		// int port = userInput.nextInt();
		// Verifier.portVerifier(port);
	    
		
		// // Création d'une nouvelle connexion aves le serveur
		// socket = new Socket(serverAddress, port);
		// System.out.format("Serveur lancé sur [%s:%d]", serverAddress, port);
		String serverAddress = "127.0.0.1";
		int port = 5000;

		socket = new Socket(serverAddress, port);

		
		// Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
		DataInputStream in = new DataInputStream(socket.getInputStream());
		
		// Attente de la réception d'un message envoyé par le, server sur le canal
		String helloMessageFromServer = in.readUTF();
		System.out.println(helloMessageFromServer);
		
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		while (userCommandHandler(out)){}
		// fermeture de La connexion avec le serveur
		socket.close();
	}
}
