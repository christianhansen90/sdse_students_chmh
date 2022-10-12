package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;

public class CityCSVProcessor {
	
	public void readAndProcess(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			//Discard header row
			br.readLine();
			
			String line;
			
			Map<String, List<cityRecord>> recordMap = new HashMap<>();
			List<cityRecord> allRecords = new ArrayList<>();
			
			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");
				
				int id = convertToInt(rawValues[0]);
				int year = convertToInt(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToInt(rawValues[3]);
					
				cityRecord obj = new cityRecord(id, year, city, population); // Create (instantiating) class object
				if (!recordMap.containsKey(city)) { // Check if city is a key
					recordMap.put(city, new ArrayList<cityRecord>()); // Create empty array if city is not a key
				}
				recordMap.get(city).add(obj); // add obj to value array
			}
			
			for (Map.Entry<String, List<cityRecord>> entry : recordMap.entrySet()) {
				int i = 0; // Counter for entries
				int totalPop = 0; // Sum of populations
				List<Integer> years = new ArrayList<>(); // Initialise list for the years
				
				for (cityRecord record : entry.getValue()) {
					i++; // Increment entry count
					years.add(record.year); // Add year to list
					totalPop += record.population; // Increment sum of populations
					}
				// Do calculations
				int min = Collections.min(years); 
				int max = Collections.max(years);
				int avgPop = totalPop/i;
				
				System.out.println("Average population of " + entry.getKey() + " (" + min + " - " + max + "; " + i + " entries): " + avgPop);
				}
			
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}
	
	private String cleanRawValue(String rawValue) {
		return rawValue.trim();
	}
	
	private int convertToInt(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		return Integer.parseInt(rawValue);
	}
	
	private String convertToString(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		
		return rawValue;
	}
	
	
	public static final void main(String[] args) {
		CityCSVProcessor reader = new CityCSVProcessor();
		
		File dataDirectory = new File("data/");
		File csvFile = new File(dataDirectory, "Cities.csv");
		
		reader.readAndProcess(csvFile);
	}
}
