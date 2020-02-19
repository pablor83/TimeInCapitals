package time.TimeInCapitals.data;

import java.util.Map;

public class CapitalsData {

	private Map<String, CapitalsUTC> capitalsUTC;

	public CapitalsData(Map<String, CapitalsUTC> capitalUTC) {
		this.capitalsUTC = capitalUTC;
	}
	
	public String getContinentName(String capital) {
		return capitalsUTC.get(capital).getContinent();
	}

	public CapitalsUTC getCapitalUTC(String capital) {
		return capitalsUTC.get(capital);
	}

	public boolean existsByKey(String key) {
		return capitalsUTC.containsKey(key);
	}
}
