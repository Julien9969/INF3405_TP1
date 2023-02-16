package Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Commands {
	Socket socket;
	DataInputStream in ;
	DataOutputStream out;
	
	
	Commands(Socket socket, DataInputStream in, DataOutputStream out){
		this.socket = socket;
		this.in = in;
		this.out = out;
	}
	
	
	String sendCommand(String command) {
		try {
		out.writeUTF(command);
		System.out.println(in.readUTF());
			return in.readUTF();
		}
		catch (IOException e) {
			System.out.println("Error sending command " +  e);
			return "";
		}
	}
	
	// https://heptadecane.medium.com/file-transfer-via-java-sockets-e8d4f30703a5
	void upload(String command) {
		int bytes = 0;
        File file = new File(command.split("\\s+")[1]);
        
        try {
        	if (file.exists()) {
        		
        		// wait to the server read to get the upload
        		out.writeUTF(command);
        		in.readUTF();
        		
		        FileInputStream fileInputStream = new FileInputStream(file);
		        
		        // envoyer la taille du fichier
		        this.out.writeLong(file.length());  
		        // séparer le fichier en paquets de 4KiB
		        byte[] buffer = new byte[4*1024];
		        
		        System.out.println("file is uploading...");
		        
		        while ( (bytes = fileInputStream.read(buffer)) != -1 ){
		        	this.out.write(buffer,0,bytes);
		        	this.out.flush();
		        }
		        
		        fileInputStream.close();
		        System.out.println("Done !");
        	}
        	
        } catch (IOException e) {
			System.out.println("Error uploading the file to the server: " + e);
		}
	}
	
	void download(String command) {
		
		String fileName = command.split("\\s+")[1];
		System.out.println("ici client download");
		
		int bytes = 0;
        try {
        	// Dire au client que le serveur est prêt
        	out.writeUTF(command);
			in.readUTF();
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
	        
	        long size = this.in.readLong();     // lire la taille du fichier
	        byte[] buffer = new byte[4 * 1024];
	        
	        while (size > 0 && (bytes = this.in.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
	        	fileOutputStream.write(buffer,0,bytes);
	            size -= bytes; // lire jusqu'à la taille du fichier
	        }
	        fileOutputStream.close();
        } catch (IOException e) {
			System.out.println(e.getLocalizedMessage());

		}

	}
	
}
