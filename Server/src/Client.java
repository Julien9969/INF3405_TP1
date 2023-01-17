import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    


private static Socket socket;
		
	public static void main(String[] args) throws Exception {
		System.out.println("Entrez l'adresse IP ainsi que le port d'ecoute: ");
		Scanner userInput = new Scanner(System.in);
	
	    String serverAddress = userInput.nextLine();
		int port = userInput.nextInt();
		Verifier.adressVerifier(serverAddress);
		Verifier.portVerifier(port);
	    
		// Création d'une nouvelle connexion aves le serveur
		socket = new Socket(serverAddress, port);
		System.out.format("Serveur lancé sur [%s:%d]", serverAddress, port);
		
		// Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
		DataInputStream in = new DataInputStream(socket.getInputStream());
		
		// Attente de la réception d'un message envoyé par le, server sur le canal
		String helloMessageFromServer = in.readUTF();
		System.out.println(helloMessageFromServer);
		
		// fermeture de La connexion avec le serveur
		socket.close();
	}
}
