package time.TimeInCapitals.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import time.TimeInCapitals.data.CapitalsData;

@Repository
public class CapitalsRepository {

	private CapitalsData capitalsData;

	@Autowired
	public CapitalsRepository(CapitalsData capitalsData) {
		this.capitalsData = capitalsData;
	}
	
	public String getContinentName(String capital) {
		return capitalsData.getContinentName(capital);
	}

	public boolean isTimeChanged(String capital) {
		return !capitalsData.getCapitalUTC(capital).getSummerTime().equals("no");
	}

	public String getSummerUTC(String capital) {
		return capitalsData.getCapitalUTC(capital).getSummerTime();
	}

	public String getWinterUTC(String capital) {
		return capitalsData.getCapitalUTC(capital).getWinterTime();
	}

	public boolean existsByKey(String key) {
		return capitalsData.existsByKey(key);
	}
}
