package com.iluwatar.etl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * */
public class Transform {
	
	public void transform(List<List<String>> lines) throws IOException  {
		
		lines.remove(0);
		
		JsonObject json = createPersonsJSON(lines);
		// Get the path to the resources folder
        Path resourceDirectory = Paths.get("target");
        Path filePath = resourceDirectory.resolve("output.txt");

		// Write the string to the file
        Path path = Files.writeString(filePath, json.toString(), StandardCharsets.UTF_8);
        System.out.println("File written successfully at" + path.toString());		
	}
	
	private static Map<String, List<List<String>>> createPersonMap(List<List<String>> lines) {
		Map<String, List<List<String>>> namedMap = new HashMap();
		lines.stream().forEach(list -> {
			String key = list.get(1);
			if(namedMap.containsKey(key)) {
				List<List<String>> values = namedMap.get(key);
				values.add(list);
				namedMap.put(key, values);
			} else {
				List<List<String>> values = new ArrayList();
				values.add(list);
				namedMap.put(key, values);
			}
			
		});
		
		return namedMap;
	}
	
	private static JsonObject createPersonsJSON(List<List<String>> lines) {
		Map<String, List<List<String>>> namedMap = createPersonMap(lines);

		JsonArrayBuilder outerPersonsBuilder = Json.createArrayBuilder();

		namedMap.entrySet().forEach(person -> 
		outerPersonsBuilder.add(createPersonBuilder(person)));
				
		return Json.createObjectBuilder().add("persons", outerPersonsBuilder).build() ;
		
	}
	
	private static JsonObjectBuilder createPersonBuilder(Entry<String, List<List<String>>> person) {

		 JsonArrayBuilder outerPersonBuilder = Json.createArrayBuilder();

		 JsonObjectBuilder nameBuilder = Json.createObjectBuilder()
	                .add("name", person.getKey());
		 
		 JsonArrayBuilder outerMediaBuilder = createMediaBuilder(person);
		 JsonObjectBuilder mediaBuilder = Json.createObjectBuilder()
	                .add("media", outerMediaBuilder);
		 
		 outerPersonBuilder.add(nameBuilder).add(mediaBuilder);			
		 return Json.createObjectBuilder().add("person", outerPersonBuilder); 
	
	}
	
	private static JsonArrayBuilder createMediaBuilder(Entry<String, List<List<String>>> person) {
		 JsonArrayBuilder outerMediaBuilder = Json.createArrayBuilder();

		person.getValue().forEach(medium -> {
				if(medium.contains("Twitter")) 
					outerMediaBuilder.add(createMediumBuilder(medium, "Twitter"));
				if(medium.contains("Instagram")) 
					outerMediaBuilder.add(createMediumBuilder(medium, "Instagram"));
				if(medium.contains("TikTok")) 
					outerMediaBuilder.add(createMediumBuilder(medium, "TikTok"));
				if(medium.contains("Twitch")) 
					outerMediaBuilder.add(createMediumBuilder(medium, "Twitch"));
				if(medium.contains("YouTube")) 
					outerMediaBuilder.add(createMediumBuilder(medium, "YouTube"));
			 });
	 
		return outerMediaBuilder;
	}
	
	private static JsonObjectBuilder createMediumBuilder(List<String> medium, String mediumName) {

		JsonObjectBuilder postsBuilder = Json.createObjectBuilder()
                .add("posts", medium.get(3));
		JsonObjectBuilder followerBuilder = Json.createObjectBuilder()
                .add("followers", medium.get(2));
		
		JsonArrayBuilder outerBuilder = Json.createArrayBuilder()
				.add(postsBuilder).add(followerBuilder);
		return Json.createObjectBuilder().add(mediumName,outerBuilder);		
	
	}


}
