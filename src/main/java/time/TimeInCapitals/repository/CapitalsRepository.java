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

	public String isTimeChangeForEurope(String capital) {
		return capitalsData.getEuropeDataUTC(capital).getIsTimeChange();
	}

	public String getEuropeSummerUTC(String capital) {
		return capitalsData.getEuropeDataUTC(capital).getSummerTime();
	}

	public String getEuropeWinterUTC(String capital) {
		return capitalsData.getEuropeDataUTC(capital).getWinterTime();
	}

	public boolean existsByKey(String key) {
		return capitalsData.existsByKey(key);
	}

}