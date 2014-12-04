package com.iluwatar;

import java.io.Serializable;

/**
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
