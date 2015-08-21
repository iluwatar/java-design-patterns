package com.iluwatar.tolerantreader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * RainbowFishSerializer provides methods for reading and writing {@link RainbowFish} objects to file.
 * Tolerant Reader pattern is implemented here by serializing maps instead of {@link RainbowFish} objects.
 * This way the reader does not break even though new properties are added to the schema.
 *
 */
public class RainbowFishSerializer {

	/**
	 * Write V1 RainbowFish to file
	 * @param rainbowFish
	 * @param filename
	 * @throws IOException
	 */
	public static void writeV1(RainbowFish rainbowFish, String filename) throws IOException {
		Map<String, String> map = new HashMap<>();
		map.put("name", rainbowFish.getName());
		map.put("age", String.format("%d", rainbowFish.getAge()));
		map.put("lengthMeters", String.format("%d", rainbowFish.getLengthMeters()));
		map.put("weightTons", String.format("%d", rainbowFish.getWeightTons()));
		FileOutputStream fileOut = new FileOutputStream(filename);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(map);
		objOut.close();
		fileOut.close();
	}

	/**
	 * Write V2 RainbowFish to file
	 * @param rainbowFish
	 * @param filename
	 * @throws IOException
	 */
	public static void writeV2(RainbowFishV2 rainbowFish, String filename) throws IOException {
		Map<String, String> map = new HashMap<>();
		map.put("name", rainbowFish.getName());
		map.put("age", String.format("%d", rainbowFish.getAge()));
		map.put("lengthMeters", String.format("%d", rainbowFish.getLengthMeters()));
		map.put("weightTons", String.format("%d", rainbowFish.getWeightTons()));
		map.put("angry", Boolean.toString(rainbowFish.getAngry()));
		map.put("hungry", Boolean.toString(rainbowFish.getHungry()));
		map.put("sleeping", Boolean.toString(rainbowFish.getSleeping()));
		FileOutputStream fileOut = new FileOutputStream(filename);
		ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
		objOut.writeObject(map);
		objOut.close();
		fileOut.close();
	}
	
	/**
	 * Read V1 RainbowFish from file
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static RainbowFish readV1(String filename) throws IOException, ClassNotFoundException {
		Map<String, String> map = null;
		FileInputStream fileIn = new FileInputStream(filename);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		map = (Map<String, String>) objIn.readObject();
		objIn.close();
		fileIn.close();
		return new RainbowFish(map.get("name"), 
				Integer.parseInt(map.get("age")), 
				Integer.parseInt(map.get("lengthMeters")),
				Integer.parseInt(map.get("weightTons")));
	}
}
