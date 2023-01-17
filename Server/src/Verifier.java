
public class Verifier {

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
		
}
