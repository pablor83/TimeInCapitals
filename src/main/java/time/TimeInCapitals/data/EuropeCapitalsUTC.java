package time.TimeInCapitals.data;

public class EuropeCapitalsUTC {

	private String country;
	private String capital;
	private String summerTime;
	private String winterTime;
	private String isTimeChange;

	public EuropeCapitalsUTC(String country, String capital, String summerTime, String winterTime,
			String isTimeChange) {
		this.country = country;
		this.capital = capital;
		this.summerTime = summerTime;
		this.winterTime = winterTime;
		this.isTimeChange = isTimeChange;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getSummerTime() {
		return summerTime;
	}

	public void setSummerTime(String summerTime) {
		this.summerTime = summerTime;
	}

	public String getWinterTime() {
		return winterTime;
	}

	public void setWinterTime(String winterTime) {
		this.winterTime = winterTime;
	}

	public String getIsTimeChange() {
		return isTimeChange;
	}

	public void setIsTimeChange(String istimeChange) {
		this.isTimeChange = istimeChange;
	}
}