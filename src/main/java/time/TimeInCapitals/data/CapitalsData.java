package time.TimeInCapitals.data;

import java.util.Map;

public class CapitalsData {

	private Map<String, EuropeCapitalsUTC> europeUTC;

	public CapitalsData(Map<String, EuropeCapitalsUTC> europeUTC) {
		this.europeUTC = europeUTC;
	}

	public EuropeCapitalsUTC getEuropeDataUTC(String capital) {
		return europeUTC.get(capital);
	}

	public boolean existsByKey(String key) {
		return europeUTC.containsKey(key);
	}

}