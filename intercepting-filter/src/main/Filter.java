/**
* Filter interface 
 * Filters perform certain tasks prior or after execution of request by request handler.
 * In this case, before the request is handled by the target, the request undergoes through each Filter
 * @author joshzambales
 *
 */
public interface Filter{
	public String execute(String[] request);
}
