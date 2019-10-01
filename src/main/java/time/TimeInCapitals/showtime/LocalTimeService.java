package time.TimeInCapitals.showtime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import time.TimeInCapitals.config.TimeInCapitalsConfig;
import time.TimeInCapitals.repository.CapitalsRepository;

@Service
public class LocalTimeService {

	private CapitalsRepository capitalsRepository;
	
	public LocalTimeService(CapitalsRepository capitalsRepository) {
		this.capitalsRepository = capitalsRepository;
	}

	public String getTime() {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		String localTime = localDateTime.format(timeFormat);

		return localTime;
	}

	public String getTimeInZone(String capital) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		Instant instant = Instant.now();
		ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getSummerUTC(capital));

		return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
	}

	public String getDate() {

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/y");
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDate = localDateTime.format(dateFormat);

		return localDate;
	}

	public String getTimeAndDate() {

		String localTimeAndDate = "Local Time: " + getTime() + "\nLocal date: " + getDate();

		return localTimeAndDate;
	}

	public boolean existsByKey(String key) {		
		return capitalsRepository.existsByKey(key);
	}
}