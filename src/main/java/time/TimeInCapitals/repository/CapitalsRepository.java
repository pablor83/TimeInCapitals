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
	
	public String getSummerUTC(String capital) {
		return capitalsData.getUtcSummerCapitals(capital);
	}
	
	public boolean existsByKey(String key) {		
		return capitalsData.existsByKey(key);
	}

}