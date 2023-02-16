import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;


public class CommandsHandler {
	private DataOutputStream out;
	DataInputStream in;
	private File currentFolder;
	int clientNumber;
	
	
	CommandsHandler(DataInputStream in, DataOutputStream out, int clientNumber){
		this.out = out;
		this.clientNumber = clientNumber;
		this.in = in;
		currentFolder = new File("./");
		System.out.println("Debut " + this.currentFolder.getAbsolutePath());
	}
	
	void cd (String path) {
		File folder = new File(this.currentFolder, path);
		
		try {
			
			if (path.contains("..")) {
				if (this.currentFolder.getParentFile() != null) {
					this.currentFolder = this.currentFolder.getParentFile();
					out.writeUTF("");
				}
				else { out.writeUTF("On est a la racine" ); }
			}
			else {
				if (folder.exists()) {
					this.currentFolder = folder;
					out.writeUTF("");
				}
				else {
					out.writeUTF(path + " n'existe pas" );
				}
			}
			
			out.writeUTF(currentFolder.getName().contains(".") ? "Server" : currentFolder.getName());
			
		} catch (IOException e) {
				System.out.println("Error sending cd result to client : " + this.clientNumber + e);
		}		
	}
	
	void mkdir(String dirName) {
		try {	
			File folder = new File(currentFolder.getName(), dirName);
			
			if (!folder.exists()) {
				folder.mkdirs();
				
				out.writeUTF("Le dossier " + dirName + " a ete cree");
			}
			else {
				out.writeUTF("Le dossier " + dirName + " existe deja");
			}
			out.writeUTF(currentFolder.getName().contains(".") ? "Server" : currentFolder.getName());
			
		
		} catch (IOException e) {
			System.out.println("Error sending mkdir result to client : " + this.clientNumber + e);
		}
	}
	
	// https://heptadecane.medium.com/file-transfer-via-java-sockets-e8d4f30703a5
	void upload(String filePath) {
		System.out.println("ici serveur upload");
		
		int bytes = 0;
        try {
        	// dire au client qu'il peut envoyer le fichier
        	this.out.writeUTF("");
			FileOutputStream fileOutputStream = new FileOutputStream(this.currentFolder.getName() + "/" + filePath);
	        
	        long size = this.in.readLong();     // taille du fichier
	        byte[] buffer = new byte[4 * 1024];
	        
	        while (size > 0 && (bytes = this.in.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
	        	fileOutputStream.write(buffer,0,bytes);
	            size -= bytes; // le reste a lire
	        }
	        fileOutputStream.close();
        } catch (IOException e) {
			System.out.println("Error in upload from the client : " + this.clientNumber + e);
		}
		
	}
	
	void download(String filePath) {
		System.out.println("ici serveur download");
		int bytes = 0;
        File file = new File(this.currentFolder.getName()+"/"+filePath);
        	try {
				if (file.exists()){
					this.out.writeUTF("");
					FileInputStream fileInputStream = new FileInputStream(file);
					this.out.writeLong(file.length());
					byte[] buffer = new byte[4*1024];
					while((bytes=fileInputStream.read(buffer))!=-1){
						this.out.write(buffer,0,bytes);
						this.out.flush();
					}
					fileInputStream.close();		
				}
			}
		catch (IOException e) {
			System.out.println("Error while downloading file :" + e);
		}
	}
	
	void ls() {
		try {
			String[] filesList = currentFolder.list();
			String message = "";
			if (filesList.length != 0) {
				for (String file : filesList) {
					message += file + "\n"; 
				}
			}	
			else {
				message += "Dossier vide"; 
			}
			out.writeUTF(message);
			out.writeUTF(currentFolder.getName().contains(".") ? "Server" : currentFolder.getName());
		
		} catch (IOException e) {
			System.out.println("Error sending mkdir result to client : " + this.clientNumber + e);
		}
	}
	
	
}
