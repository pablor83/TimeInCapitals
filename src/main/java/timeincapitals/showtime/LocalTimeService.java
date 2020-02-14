package timeincapitals.showtime;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import timeincapitals.repository.CapitalsRepository;

@Service
public class LocalTimeService {

	private CapitalsRepository capitalsRepository;

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyy");

	private ServiceDST serviceDST;

	private Clock clock;

	@Autowired
	public LocalTimeService(CapitalsRepository capitalsRepository, ServiceDST serviceDST, Clock clock) {
		this.capitalsRepository = capitalsRepository;
		this.serviceDST = serviceDST;
		this.clock = clock;
	}

	public String getTimeForCapital(String capital) {

		String continent = capitalsRepository.getContinentName(capital);

		if (continent.equals("africa"))
			return getTimeForAfricaCapital(capital);
		else if (continent.equals("asia"))
			return getTimeForAsiaCapital(capital);
		else if (continent.equals("australia"))
			return getTimeForAustraliaCapital(capital);
		else if (continent.equals("europe"))
			return getTimeForEuropeCapital(capital);
		else if (continent.equals("north_america"))
			return getTimeForNorthAmericaCapital(capital);
		else if (continent.equals("south_america"))
			return getTimeForSouthAmericaCapital(capital);
		else
			throw new IllegalArgumentException("Incorrect capital name");

	}

	public String getTimeForAfricaCapital(String capital) {

		Instant instant = Instant.now(clock);
		ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getWinterUTC(capital));
		return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
	}

	public String getTimeForAsiaCapital(String capital) {

		Instant instant = Instant.now(clock);
		ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getWinterUTC(capital));
		return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
	}

	public String getTimeForAustraliaCapital(String capital) {

		if (capitalsRepository.isTimeChanged(capital)
				&& serviceDST.isDSTForAustralia(capitalsRepository.getWinterUTC(capital), capital)) {
			return dateTimeFormatter.withZone(ZoneId.of("UTC" + capitalsRepository.getSummerUTC(capital)))
					.format(Instant.now(clock));
		} else {
			return dateTimeFormatter.withZone(ZoneId.of("UTC" + capitalsRepository.getWinterUTC(capital)))
					.format(Instant.now(clock));
		}

	}

	public String getTimeForEuropeCapital(String capital) {

		if (capitalsRepository.isTimeChanged(capital)
				&& serviceDST.isDSTForEurope(capitalsRepository.getWinterUTC(capital))) {
			return dateTimeFormatter.withZone(ZoneId.of("UTC" + capitalsRepository.getSummerUTC(capital)))
					.format(Instant.now(clock));
		} else {
			return dateTimeFormatter.withZone(ZoneId.of("UTC" + capitalsRepository.getWinterUTC(capital)))
					.format(Instant.now(clock));
		}
	}

	public String getTimeForNorthAmericaCapital(String capital) {

		if (capitalsRepository.isTimeChanged(capital)
				&& serviceDST.isDSTForNorthAmerica(capitalsRepository.getWinterUTC(capital), capital)) {
			return dateTimeFormatter.withZone(ZoneId.of("UTC" + capitalsRepository.getSummerUTC(capital)))
					.format(Instant.now(clock));
		} else {
			return dateTimeFormatter.withZone(ZoneId.of("UTC" + capitalsRepository.getWinterUTC(capital)))
					.format(Instant.now(clock));
		}

	}

	public String getTimeForSouthAmericaCapital(String capital) {

		if (capitalsRepository.isTimeChanged(capital)
				&& serviceDST.isDSTForSouthAmerica(capitalsRepository.getWinterUTC(capital), capital)) {
			return dateTimeFormatter.withZone(ZoneId.of("UTC" + capitalsRepository.getSummerUTC(capital)))
					.format(Instant.now(clock));
		} else {
			return dateTimeFormatter.withZone(ZoneId.of("UTC" + capitalsRepository.getWinterUTC(capital)))
					.format(Instant.now(clock));
		}

	}

	public boolean isCorrectContinent(String capital, String continent) {
		return capitalsRepository.getContinentName(capital).equals(continent);
	}

	public String getContinentName(String capital) {

		return capitalsRepository.getContinentName(capital);
	}

	public boolean existsByKey(String key) {
		return capitalsRepository.existsByKey(key);
	}
}
