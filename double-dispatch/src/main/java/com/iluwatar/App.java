package com.iluwatar;

import java.util.ArrayList;
import java.util.List;

public class App {
	
    public static void main( String[] args ) {
    	List<GameObject> objects = new ArrayList<>();
    	objects.add(new FlamingAsteroid(0, 0, 5, 5));
    	objects.add(new SpaceStationMir(1, 1, 4, 4));
    	objects.add(new Meteoroid(10, 10, 15, 15));
    	objects.add(new SpaceStationIss(12, 11, 14, 15));
    	objects.stream().forEach(o1 -> objects.stream().forEach(o2 -> { if (o1 != o2) System.out.println(String.format("%s -> %s", o1, o2)); } ));
    }
}
