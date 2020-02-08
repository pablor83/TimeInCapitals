package com.pablor83.timeincapitals.data;

public class CapitalsUTC {

	private String country;
	private String capital;
	private String summerTime;
	private String winterTime;
	private String continent;

	public CapitalsUTC(String country, String capital, String summerTime, String winterTime, String continent) {
		this.country = country;
		this.capital = capital;
		this.summerTime = summerTime;
		this.winterTime = winterTime;
		this.continent = continent;
	}

	public String getCountry() {
		return country;
	}

	public String getCapital() {
		return capital;
	}

	public String getSummerTime() {
		return summerTime;
	}

	public String getWinterTime() {
		return winterTime;
	}

	public String getContinent() {
		return continent;
	}
}
