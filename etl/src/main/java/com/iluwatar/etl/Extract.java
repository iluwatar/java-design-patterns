package com.iluwatar.etl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Extract {

	public List<List<String>> extract() throws IOException {
		URL resourceUrl = getClass().getClassLoader().getResource("SocialMediaTop100.csv");

		List<List<String>> result = new ArrayList<>();

		if (resourceUrl != null) {
			InputStream is = resourceUrl.openStream();
			result = convert(is);

		} else {
			System.out.println("Resource not found");
		}

		return result;
	}

	public static List<List<String>> convert(InputStream inputStream) throws IOException {
		List<List<String>> result = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(","); // Assuming comma-separated values
				result.add(Arrays.asList(values));
			}
		}

		return result;
	}

}
