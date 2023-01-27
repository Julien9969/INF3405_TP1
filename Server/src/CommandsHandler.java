import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
//import java.io.IOException;



public class CommandsHandler {
	private DataOutputStream out;
	private File currentFolder;
	int clientNumber;
	
	CommandsHandler(DataOutputStream out, int clientNumber){
		this.out = out;
		this.clientNumber = clientNumber;
		
		currentFolder = new File(System.getProperty("user.dir"));
		// System.out.println("Debut " + this.currentFolder.getAbsolutePath());
	}
	
	void cd (String path) {
		File folder = new File(this.currentFolder.getAbsolutePath() + "/" + path);
		
		try {
			
			if (this.currentFolder.getParentFile() != null) {
				if (path.contains("..") ) {
					this.currentFolder = this.currentFolder.getParentFile();
					out.writeUTF("");
					// System.out.println(".." + this.currentFolder.getAbsolutePath());
				}
				else {
					// System.out.println(this.currentFolder.getParent());
					
					if (folder.exists()) {
						this.currentFolder = new File(this.currentFolder, path);
						out.writeUTF("");
					}
					else {
						out.writeUTF("On est a la racine" );
					
					}
				}
			}
			else {out.writeUTF(path.toString() + " n'existe pas" );}
			
			out.writeUTF(currentFolder.getCanonicalPath());
			
		} catch (IOException e) {
				System.out.println("Error sending cd result to client : " + this.clientNumber + e);
		}		
	}
	
	void mkdir(String dirName) {
		try {	
			File folder = new File(currentFolder.getPath() + "/" + dirName);
			
			if (!folder.exists()) {
				folder.mkdirs();
				
				System.out.println("1" + folder.getAbsolutePath());
				System.out.println("2" + currentFolder.getAbsolutePath());
				
				out.writeUTF("Le dossier ./" + dirName + " a ete cree");
			}
			else {
				out.writeUTF("Le dossier ./" + dirName + " existe deja");
			}
			out.writeUTF(currentFolder.getCanonicalPath());
			
		
		} catch (IOException e) {
			System.out.println("Error sending mkdir result to client : " + this.clientNumber + e);
		}
	}
	
	void upload(String fileName) {
		
	}
	
	void download(String fileName) {
		
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
			out.writeUTF(currentFolder.getCanonicalPath());
		
		} catch (IOException e) {
			System.out.println("Error sending mkdir result to client : " + this.clientNumber + e);
		}
	}
	
	
}
