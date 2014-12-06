package com.iluwater;

/**
 * For JNDI lookup of services from the web.xml. Will match name of the service name that
 * is being requested and return a newly created service object with the name
 * @author saifasif
 *
 */
public class InitContext {

	/**
	 * Perform the lookup based on the service name. The returned object will need to be
	 * casted into a {@link Service}
	 * @param serviceName
	 * @return 
	 */
	public Object lookup(String serviceName){
		if( serviceName.equals("jndi/serviceA") ){
			System.out.println("Looking up service A and creating new serivce for A");
			return new ServiceImpl("jndi/serviceA");
		} else if( serviceName.equals("jndi/serviceB") ){
			System.out.println("Looking up service B and creating new serivce for B");
			return new ServiceImpl("jndi/serviceB");
		} else {
			return null;
		}
	}

}
