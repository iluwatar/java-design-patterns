package com.iluwatar;

/**
 *
 * Singleton pattern ensures that the class (IvoryTower) can have only one
 * existing instance and provides global access to that instance.
 *
 */
public class App {

	public static void main(String[] args) {

		IvoryTower ivoryTower1 = IvoryTower.getInstance();
		IvoryTower ivoryTower2 = IvoryTower.getInstance();
		System.out.println("ivoryTower1=" + ivoryTower1);
		System.out.println("ivoryTower2=" + ivoryTower2);

		ThreadSafeLazyLoadedIvoryTower threadSafeIvoryTower1 = ThreadSafeLazyLoadedIvoryTower
				.getInstance();
		ThreadSafeLazyLoadedIvoryTower threadSafeIvoryTower2 = ThreadSafeLazyLoadedIvoryTower
				.getInstance();
		System.out.println("threadSafeIvoryTower1=" + threadSafeIvoryTower1);
		System.out.println("threadSafeIvoryTower2=" + threadSafeIvoryTower2);
		
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
