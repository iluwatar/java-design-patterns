 import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
/**
 *  Filter Manager manages the filters and Filter Chain. 
 * @author joshzambales
 *
 */
public class FilterManager{
	private FilterChain filterChain;
	
	public FilterManager(Target target){		
		filterChain = new FilterChain(target);
	}
	public void setFilter(Filter filter){
		filterChain.addFilter(filter);
	}
	public String filterRequest(String request){
		return filterChain.execute(request);
	}
}