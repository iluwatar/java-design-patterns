/**
 * Concrete implementation of filter
 * 
 * @author joshzambales
 *
 */
public class ContactFilter implements Filter{
	public String execute(String[] request){
		if(request[1].equals("") || request[1].matches(".*[^\\d]+.*") || request[1].length() != 11){
			return null;
		}else return request[1];
	}
}