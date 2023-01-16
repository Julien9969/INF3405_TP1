import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;
public class Client {
    public static void adressVerifier(String address){
    String stringSegment = "";
    int dotCounter = 0;
    for (int i = 0; i<address.length(); i++){
        

        if(Character.isDigit(address.charAt(i))== false){
            if(address.charAt(i) == '.'){
                dotCounter++;
                if(Integer.parseInt(stringSegment) > 255){
                    throw new IllegalArgumentException("not a valid IP address");
                }else{
                    stringSegment = "";
                }
                if( i+1 == address.length()||address.charAt(i+1) == '.' ){
                    throw new IllegalArgumentException("not a valid IP address");
                }
            }
            else {
                throw new IllegalArgumentException("not a valid IP address");
            }   
        }
        else {
            stringSegment += address.charAt(i);
        }
    }
    if(Integer.parseInt(stringSegment) > 255){
        throw new IllegalArgumentException("not a valid IP address");
    }
    if (dotCounter != 3){
        throw new IllegalArgumentException("not a valid IP address");
    }

    System.out.println("Voici l'adresse entree: "+ stringSegment);
    }
    public static void portVerifier(int port){
        if (port>=5050 || port<5000){
            throw new IllegalArgumentException("not a valid port");
        }
    }


private static Socket socket;
	public static void main(String[] args) throws Exception {
	System.out.println("Entrez l'adresse IP ainsi que le port d'ecoute: ");
	Scanner userInput = new Scanner(System.in);

    String serverAddress = userInput.nextLine();
	int port = userInput.nextInt();
    adressVerifier(serverAddress);
    portVerifier(port);
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
