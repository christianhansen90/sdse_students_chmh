package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class CityCSVProcessor {
	
	public void readAndProcess(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			//Discard header row
			br.readLine();
			
			String line;
			
			/* 
			 * Create Map. 
			 * Key is City Name <String>, Value will be list of city records List<cityRecord>
			 * The map is called recordMap
			 * note: Map is like a Python dict
			 */
			Map<String, List<cityRecord>> recordMap = new HashMap<>();
			
			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");
				
				// Define each variable
				int id = convertToInt(rawValues[0]);
				int year = convertToInt(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToInt(rawValues[3]);
				
				// Create (instantiating) class object
				cityRecord obj = new cityRecord(id, year, city, population);
				
				// Check if city is a key in the Map
				if (!recordMap.containsKey(city)) { 
					/* 
					 * If the city is NOT a key, create a Map entry
					 * with the city as a key, and
					 * a NEW empty ArrayList as the value associated to the city key 
					 */
					recordMap.put(city, new ArrayList<cityRecord>()); 
				}
				/* 
				 * Then add 'obj', which is a city record containing:
				 * id, year, city, population
				 * to the City's Value (which is an ArrayList)
				 * Each Key (city) now essentially contains a list
				 * of lists as its Value
				 */
				recordMap.get(city).add(obj); 
			}
			 	// Now iterate through each Key Value pair in the Map
				// call this 'entry'
			for (Map.Entry<String, List<cityRecord>> entry : recordMap.entrySet()) {
				int i = 0; 									// Counter for entries
				int totalPop = 0; 							// Sum of populations
				List<Integer> years = new ArrayList<>(); 	// Initialize list for the years
				
				// Go through each cityRecord for each City
				for (cityRecord record : entry.getValue()) {
					i++; 									// Increment entry count
					years.add(record.year); 				// Add year to list
					totalPop += record.population; 			// Increment sum of populations
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
