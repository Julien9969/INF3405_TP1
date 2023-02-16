package Client;
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
	
	    public static void portVerifier(int port){
	        if (port>=5050 || port<5000){
	            throw new IllegalArgumentException("not a valid port");
	        }
	    }
		
}
