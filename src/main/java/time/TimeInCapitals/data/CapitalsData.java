package time.TimeInCapitals.data;

import java.util.HashMap;

public class CapitalsData {
	
	private HashMap<String, String> utcSummerCapitals;
	
	public CapitalsData(HashMap<String, String> utcSummerCapitals) {
		this.utcSummerCapitals = utcSummerCapitals;
	}

	public String getUtcSummerCapitals(String capital) {
		return utcSummerCapitals.get(capital);
	}
	
	public boolean existsByKey(String key) {		
		return utcSummerCapitals.containsKey(key);
	}

}