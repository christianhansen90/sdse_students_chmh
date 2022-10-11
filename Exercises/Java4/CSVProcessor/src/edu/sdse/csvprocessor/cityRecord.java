package edu.sdse.csvprocessor;

public class cityRecord extends CityCSVProcessor {
	int id;
	int year;
	String city;
	int population;
	
	public cityRecord(int id, int year, String city, int population) {
		this.id = id;
		this.year = year;
		this.city = city;
		this.population = population;
	}
	
	@Override
    public String toString() {
        return "id: " + this.id + ", year: " + this.year + ", city: " + this.city + ", population: " + this.population;
      //System.out.println("id: " + id + ", year: " + year + ", city: " + city + ", population: " + population);
	}
}