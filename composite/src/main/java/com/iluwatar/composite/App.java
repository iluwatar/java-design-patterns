package com.iluwatar.composite;

/**
 * The Composite pattern is a partitioning design pattern. The Composite pattern 
 * describes that a group of objects is to be treated in the same way as a single 
 * instance of an object. The intent of a composite is to "compose" objects into 
 * tree structures to represent part-whole hierarchies. Implementing the Composite 
 * pattern lets clients treat individual objects and compositions uniformly.
 * <p>
 * In this example we have sentences composed of words composed of letters. All of
 * the objects can be treated through the same interface ({@link LetterComposite}).
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
