package com.iluwatar.singleton;

/**
 *
 * Singleton pattern ensures that the class ({@link IvoryTower}) can have only one
 * existing instance per Java classloader instance and provides global access to it. 
 * <p>
 * http://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
 *<p>
 * The risk of this pattern is that bugs resulting from setting a singleton up in a distributed environment can 
 * be tricky to debug, since it will work fine if you debug with a single classloader. Additionally, these
 * problems can crop up a while after the implementation of a singleton, since they may start out synchronous and
 * only become async with time, so you it may not be clear why you are seeing certain changes in behaviour.
 * <p>
 * http://stackoverflow.com/questions/17721263/singleton-across-jvm-or-application-instance-or-tomcat-instance
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {

		// eagerly initialized singleton
		IvoryTower ivoryTower1 = IvoryTower.getInstance();
		IvoryTower ivoryTower2 = IvoryTower.getInstance();
		System.out.println("ivoryTower1=" + ivoryTower1);
		System.out.println("ivoryTower2=" + ivoryTower2);

		// lazily initialized singleton
		ThreadSafeLazyLoadedIvoryTower threadSafeIvoryTower1 = ThreadSafeLazyLoadedIvoryTower
				.getInstance();
		ThreadSafeLazyLoadedIvoryTower threadSafeIvoryTower2 = ThreadSafeLazyLoadedIvoryTower
				.getInstance();
		System.out.println("threadSafeIvoryTower1=" + threadSafeIvoryTower1);
		System.out.println("threadSafeIvoryTower2=" + threadSafeIvoryTower2);

		// enum singleton
		EnumIvoryTower enumIvoryTower1 = EnumIvoryTower.INSTANCE;
		EnumIvoryTower enumIvoryTower2 = EnumIvoryTower.INSTANCE;
		System.out.println("enumIvoryTower1=" + enumIvoryTower1);
		System.out.println("enumIvoryTower2=" + enumIvoryTower2);
		
		InitializingOnDemandHolderIdiom demandHolderIdiom = InitializingOnDemandHolderIdiom.getInstance();
		System.out.println(demandHolderIdiom);
		InitializingOnDemandHolderIdiom demandHolderIdiom2 = InitializingOnDemandHolderIdiom.getInstance();
		System.out.println(demandHolderIdiom2);
		
		ThreadSafeDoubleCheckLocking dcl1 = ThreadSafeDoubleCheckLocking.getInstance();
		System.out.println(dcl1);
		ThreadSafeDoubleCheckLocking dcl2 = ThreadSafeDoubleCheckLocking.getInstance();
		System.out.println(dcl2);
	}
}
