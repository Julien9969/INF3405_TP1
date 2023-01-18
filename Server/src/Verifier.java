import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Verifier {
	
	
	// Source : https://www.codeurjava.com/2015/05/les-expressions-regulieres-avec-regex.html
	public static void adressVerifier(String ip) {
		
		Pattern p = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
		Matcher m = p.matcher(ip);
		if( !m.matches()) {
			throw new IllegalArgumentException("not a valid IP address"); 
		}
	}

//	public static void adressVerifier(String address){
//	    String stringSegment = "";
//	    int dotCounter = 0;
//	    for (int i = 0; i<address.length(); i++){
//	        
//
//	        if(Character.isDigit(address.charAt(i))== false){
//	            if(address.charAt(i) == '.'){
//	                dotCounter++;
//	                if(Integer.parseInt(stringSegment) > 255){
//	                    throw new IllegalArgumentException("not a valid IP address");
//	                }else{
//	                    stringSegment = "";
//	                }
//	                if( i+1 == address.length()||address.charAt(i+1) == '.' ){
//	                    throw new IllegalArgumentException("not a valid IP address");
//	                }
//	            }
//	            else {
//	                throw new IllegalArgumentException("not a valid IP address");
//	            }   
//	        }
//	        else {
//	            stringSegment += address.charAt(i);
//	        }
//	    }
//	    if(Integer.parseInt(stringSegment) > 255){
//	        throw new IllegalArgumentException("not a valid IP address");
//	    }
//	    if (dotCounter != 3){
//	        throw new IllegalArgumentException("not a valid IP address");
//	    }
//
//	    System.out.println("Voici l'adresse entree: "+ stringSegment);
//	    }
	
	    public static void portVerifier(int port){
	        if (port>=5050 || port<5000){
	            throw new IllegalArgumentException("not a valid port");
	        }
	    }
		
}
