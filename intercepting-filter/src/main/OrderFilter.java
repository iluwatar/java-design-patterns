/**
 * Concrete implementation of filter
 * 
 * @author joshzambales
 *
 */
public class OrderFilter implements Filter{
	public String execute(String[] request){
		if(request[4].equals("")){
			return null;
		}else return request[4];
	}
}