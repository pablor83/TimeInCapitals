package time.TimeInCapitals.data;

import java.util.HashMap;

public class CapitalsData {
	
	private HashMap<String, String> utcSummerCapitals;
	
	public CapitalsData(HashMap<String, String> utcSummerCapitals) {
		this.utcSummerCapitals = utcSummerCapitals;
	}

	public HashMap<String, String> getUtcSummerCapitals() {
		return utcSummerCapitals;
	}

}
