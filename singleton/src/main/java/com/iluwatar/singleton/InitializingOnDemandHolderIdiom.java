package com.iluwatar.singleton;

import java.io.Serializable;

/**
 * The Initialize-on-demand-holder idiom is a secure way of 
 * creating lazy initialized singleton object in Java.
 * refer to "The CERT Oracle Secure Coding Standard for Java"
 * By Dhruv Mohindra, Robert C. Seacord p.378
 * <p>
 * Singleton objects usually are heavy to create and sometimes need to serialize them.
 * This class also shows how to preserve singleton in serialized version of singleton.
 * 
 * @author mortezaadi@gmail.com
 *
 */
public class InitializingOnDemandHolderIdiom implements Serializable{

	private static final long serialVersionUID = 1L;

	private static class HelperHolder {
		public static final InitializingOnDemandHolderIdiom INSTANCE = new InitializingOnDemandHolderIdiom();
	}

	public static InitializingOnDemandHolderIdiom getInstance() {
		return HelperHolder.INSTANCE;
	}

	private InitializingOnDemandHolderIdiom() {
	}

	protected Object readResolve() {
		return getInstance();
	}

}
