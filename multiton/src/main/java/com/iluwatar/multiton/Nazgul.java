package com.iluwatar.multiton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * Nazgul is a Multiton class. Nazgul instances can be queried
 * using {@link #getInstance} method.
 *
 */
public class Nazgul {

	private static Map<NazgulName, Nazgul> nazguls;
	
	private NazgulName name;

	static {
		nazguls = new ConcurrentHashMap<>();
		nazguls.put(NazgulName.KHAMUL, new Nazgul(NazgulName.KHAMUL));
		nazguls.put(NazgulName.MURAZOR, new Nazgul(NazgulName.MURAZOR));
		nazguls.put(NazgulName.DWAR, new Nazgul(NazgulName.DWAR));
		nazguls.put(NazgulName.JI_INDUR, new Nazgul(NazgulName.JI_INDUR));
		nazguls.put(NazgulName.AKHORAHIL, new Nazgul(NazgulName.AKHORAHIL));
		nazguls.put(NazgulName.HOARMURATH, new Nazgul(NazgulName.HOARMURATH));
		nazguls.put(NazgulName.ADUNAPHEL, new Nazgul(NazgulName.ADUNAPHEL));
		nazguls.put(NazgulName.REN, new Nazgul(NazgulName.REN));
		nazguls.put(NazgulName.UVATHA, new Nazgul(NazgulName.UVATHA));
	}
	
	private Nazgul(NazgulName name) {
		this.name = name;
	}

	public static Nazgul getInstance(NazgulName name) {
		return nazguls.get(name);
	}
	
	public NazgulName getName() {
		return name;
	}
}
