package time.TimeInCapitals.data;

public class EuropeCapitalsUTC {

	private String country;
	private String capital;
	private String summerTime;
	private String winterTime;
	private boolean timeChanged;

	public EuropeCapitalsUTC(String country, String capital, String summerTime, String winterTime,
			boolean isTimeChanged) {
		this.country = country;
		this.capital = capital;
		this.summerTime = summerTime;
		this.winterTime = winterTime;
		this.timeChanged = isTimeChanged;
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

	public boolean isTimeChanged() {
		return timeChanged;
	}	
}
