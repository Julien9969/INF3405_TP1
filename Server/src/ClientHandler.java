import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ClientHandler extends Thread { // pour traiter la demande de chaque client sur un socket particulier
	private Socket socket; 
	private int clientNumber; 
	public ClientHandler(Socket socket, int clientNumber) {
		this.socket = socket;
		this.clientNumber = clientNumber; System.out.println("New connection with client#" + clientNumber + " at" + socket);
	}
	public void run() { // Création de thread qui envoi un message à un client
		try {
			while(true){
				DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi 
				out.writeUTF("Hello from server - you are client#" + clientNumber); // envoi de message
				
				//recoit messages du client
				DataInputStream in = new DataInputStream(socket.getInputStream());
				String message = in.readUTF();	
				String[] messageParts = message.split("\\s+");
				String command = messageParts[0];
				
				
				//cree un repertoire
				if (command.equals("mkdir")){
					String dirName = messageParts[1];
					Path path = Paths.get(dirName);
					if (Files.notExists(path)){
						Files.createDirectory(path);
						System.out.println("Le fichier "+ messageParts[1]+" a ete cree");
					}
					else{
						System.out.println("le repertoire existe deja");
					}
				}

				//naviguer les repertoires
				if (command.equals("cd")){

				}
				

			}	
		} 
		catch (IOException e) {
			System.out.println("Error handling client# " + clientNumber + ": " + e);
		} 
		
		finally {
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("Couldn't close a socket, what's going on?");}
			System.out.println("Connection with client# " + clientNumber+ " closed");}
		}
}