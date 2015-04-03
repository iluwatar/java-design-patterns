
/**
 * Concrete implementation of filter
 * 
 * @author joshzambales
 *
 */
public class AddressFilter implements Filter{
	public String execute(String[] request){
		if(request[2].equals("")){
			return null;
		}else return request[2];
	}
}