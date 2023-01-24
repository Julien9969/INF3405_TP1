import java.net.InetAddress;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private static ServerSocket Listener; 
	// Application Serveur
		public static void main(String[] args) throws Exception {
			
			System.out.println("serveur");
			// Compteur incrémenté à chaque connexion d'un client au serveur
			int clientNumber = 0;
			
			// Adresse et port du serveur
			//String serverAddress = "127.0.0.1"; int serverPort = 5000;
			Scanner userInput = new Scanner(System.in);
//			System.out.println("Entrez l'adresse IP du serveur : ");
//		    String serverAddress = userInput.nextLine();
//		    Verifier.adressVerifier(serverAddress);
//		    
//		    System.out.println("Entrez le port d'ecoute: ");
//			int serverPort = userInput.nextInt();
//			Verifier.portVerifier(serverPort);
			int serverPort = 5006;
			String serverAddress = "127.1.1.1";
			
			
			// Création de la connexien pour communiquer ave les, clients
			Listener = new ServerSocket();
			Listener.setReuseAddress(true);
			InetAddress serverIP = InetAddress.getByName(serverAddress);
			
			// Association de l'adresse et du port à la connexien
			Listener.bind(new InetSocketAddress(serverIP, serverPort));
			System.out.format("Server running on %s:%d%n", serverAddress, serverPort);
			
			try {
				// À chaque fois qu'un nouveau client se, connecte, on exécute la fonstion
				// run() de l'objet ClientHandler
				while (true) {
					// Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte
					// Une nouvetle connection : on incémente le compteur clientNumber 
					new ClientHandler(Listener.accept(), clientNumber++).run();
				}
			} finally {
				// Fermeture de la connexion
				Listener.close();
			}
	} 
}


