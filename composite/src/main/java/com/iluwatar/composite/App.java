package com.iluwatar.composite;

/**
 * 
 * With Composite we can treat tree hierarchies of objects with uniform
 * interface ({@link LetterComposite}). In this example we have sentences composed of
 * words composed of letters.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		System.out.println("Message from the orcs: ");

		LetterComposite orcMessage = new Messenger().messageFromOrcs();
		orcMessage.print();

		System.out.println("\n");

		System.out.println("Message from the elves: ");

		LetterComposite elfMessage = new Messenger().messageFromElves();
		elfMessage.print();
	}
}
