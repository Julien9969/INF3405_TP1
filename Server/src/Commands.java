import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import java.io.IOException;
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
	
	void upload(String fileName) {
		
	}
	
	void download(String fileName) {
		
	}
	
}
