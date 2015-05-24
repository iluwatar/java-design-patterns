package com.iluwatar;

public class App {
	
    public static void main( String[] args ) {
    	OliphauntPool pool = new OliphauntPool();
    	System.out.println(pool);
    	Oliphaunt oliphaunt1 = pool.checkOut();
    	System.out.println("Checked out " + oliphaunt1);
    	System.out.println(pool);
    	Oliphaunt oliphaunt2 = pool.checkOut();
    	System.out.println("Checked out " + oliphaunt2);
    	Oliphaunt oliphaunt3 = pool.checkOut();
    	System.out.println("Checked out " + oliphaunt3);
    	System.out.println(pool);
    	System.out.println("Checking in " + oliphaunt1);
    	pool.checkIn(oliphaunt1);
    	System.out.println("Checking in " + oliphaunt2);
    	pool.checkIn(oliphaunt2);
    	System.out.println(pool);
       	Oliphaunt oliphaunt4 = pool.checkOut();
    	System.out.println("Checked out " + oliphaunt4);
       	Oliphaunt oliphaunt5 = pool.checkOut();
    	System.out.println("Checked out " + oliphaunt5);
    	System.out.println(pool);
    }
}
