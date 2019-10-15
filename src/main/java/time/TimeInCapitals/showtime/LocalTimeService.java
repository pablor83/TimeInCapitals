package time.TimeInCapitals.showtime;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.springframework.stereotype.Service;

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

	public String getTimeForEuropeCapital(String capital) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyy");
		LocalDateTime localDateTime = LocalDateTime.now();
		Instant instant = Instant.now();

		LocalDate endOfSummerZone = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atEndOfMonth()
				.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
		LocalDate endOfWinterZone = YearMonth.of(YearMonth.now().getYear(), Month.MARCH).atEndOfMonth()
				.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

		boolean isBeforeWinterZone = localDateTime.isBefore(LocalDateTime.of(endOfSummerZone, LocalTime.of(02, 59)));
		boolean isBeforeSummerZone = localDateTime.isBefore(LocalDateTime.of(endOfWinterZone, LocalTime.of(01, 59)));

		if (capitalsRepository.isTimeChangedForEurope(capital) && isBeforeWinterZone
				&& !isBeforeSummerZone) {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getEuropeSummerUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		} else {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getEuropeWinterUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		}

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
