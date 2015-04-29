package com.iluwatar;

import java.io.IOException;

public class App {
	
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
    	RainbowFish fishV1 = new RainbowFish("Zed", 10, 11, 12);
    	RainbowFishSerializer.write(fishV1, "fish1.out");
    	RainbowFish deserializedFishV1 = RainbowFishSerializer.read("fish1.out");
    	System.out.println(String.format("deserializedFishV1 name=%s age=%d length=%d weight=%d", deserializedFishV1.getName(), 
    			deserializedFishV1.getAge(), deserializedFishV1.getLengthMeters(), deserializedFishV1.getWeightTons()));
    	RainbowFishV2 fishV2 = new RainbowFishV2("Scar", 5, 12, 15, true, true, true);
    	RainbowFishSerializer.write(fishV2, "fish2.out");
    	RainbowFish deserializedFishV2 = RainbowFishSerializer.read("fish2.out");
    	System.out.println(String.format("deserializedFishV2 name=%s age=%d length=%d weight=%d", deserializedFishV2.getName(), 
    			deserializedFishV2.getAge(), deserializedFishV2.getLengthMeters(), deserializedFishV2.getWeightTons()));
    }
}
