import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
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

			DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi 
			out.writeUTF("Hello from server - you are client#" + clientNumber); // envoi de message
			DataInputStream in = new DataInputStream(socket.getInputStream());
		
			CommandsHandler commandsHandler = new CommandsHandler(in, out, clientNumber);
			String message, command;
			String[] messageParts;
			boolean exit = false;
			
			while(!exit){
				//reçoit messages du client
				message = in.readUTF();	
				messageParts = message.split("\\s+");
				command = messageParts[0];
				
				
				switch (command) {
					case "cd":
						commandsHandler.cd(messageParts[1]);
						break;
					
					case "mkdir":
						commandsHandler.mkdir(messageParts[1]);
						break;
					
					case "ls":
						commandsHandler.ls();
						break;
						
					case "upload":
						commandsHandler.upload(messageParts[1]);
						break;
						
					case "download":
						commandsHandler.download(messageParts[1]);
						break;
					
					case "exit":
						exit = true;
						break;
				}
				
				
				//cree un repertoire
//				if (command.equals("mkdir")){
//					String dirName = messageParts[1];
//					Path path = Paths.get(dirName);
//					if (Files.notExists(path)){
//						Files.createDirectory(path);
//						System.out.println("Le fichier "+ messageParts[1]+" a ete cree");
//					}
//					else{
//						System.out.println("le repertoire existe deja");
//					}
//				}
//
//				//naviguer les repertoires
//				if (command.equals("cd")){
//
//				}
				

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