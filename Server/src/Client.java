import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    


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
	    
		// Création d'une nouvelle connexion aves le serveur
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
		
		String prompt;
		
		while (true) {
			prompt = userInput.nextLine();
			System.out.println("sending");
			out.writeUTF(prompt);
			out.flush();
			
			if (prompt ==  "quit") {
				break;
			}
			
			System.out.println(in.readUTF());
			
		}
		
		// fermeture de La connexion avec le serveur
		socket.close();
	}
}
