package time.TimeInCapitals.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import time.TimeInCapitals.data.CapitalsData;

@Repository
public class CapitalsRepository {
	
	CapitalsData capitalsData;
	
	@Autowired
	public CapitalsRepository(CapitalsData capitalsData) {
		this.capitalsData = capitalsData;		
	}
	
	public String getSummerUTC(String capital) {
		return capitalsData.getUtcSummerCapitals().get(capital);
	}
	
	public boolean existsByKey(String key) {
		
		return capitalsData.getUtcSummerCapitals().containsKey(key);
	}

}
