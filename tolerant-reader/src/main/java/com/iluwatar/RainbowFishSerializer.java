package com.iluwatar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class RainbowFishSerializer {

	public void write(RainbowFish rainbowFish, String filename) {
		Map<String, String> map = new HashMap<>();
		map.put("name", rainbowFish.getName());
		map.put("age", String.format("%d", rainbowFish.getAge()));
		map.put("lengthMeters", String.format("%d", rainbowFish.getLengthMeters()));
		map.put("weightTons", String.format("%d", rainbowFish.getWeightTons()));
		try {
			FileOutputStream fileOut = new FileOutputStream("fish.ser");
			ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			objOut.writeObject(map);
			objOut.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public RainbowFish read(String filename) {
//	}
}
