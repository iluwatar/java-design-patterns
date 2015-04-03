/**
 * Concrete implementation of filter
 * 
 * @author joshzambales
 *
 */
public class DepositFilter implements Filter{
	public String execute(String[] request){
		if(request[3].equals("")){
			return null;
		}else return request[3];
	}
}
