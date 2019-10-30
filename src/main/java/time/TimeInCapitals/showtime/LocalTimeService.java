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

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyy");

	public LocalTimeService(CapitalsRepository capitalsRepository) {
		this.capitalsRepository = capitalsRepository;
	}

	public String getTime() {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		String localTime = localDateTime.format(timeFormat);

		return localTime;
	}

	public String getTimeForAfricaCapital(String capital) {

		Instant instant = Instant.now();
		ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getWinterUTC(capital));
		return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
	}

	public String getTimeForAsiaCapital(String capital) {

		Instant instant = Instant.now();
		ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getWinterUTC(capital));
		return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
	}

	public String getTimeForAustraliaCapital(String capital) {

		LocalDateTime localDateTime = LocalDateTime.now()
				.atZone(ZoneId.ofOffset("UTC", ZoneOffset.of(capitalsRepository.getWinterUTC(capital))))
				.toLocalDateTime();
		Instant instant = Instant.now();

		LocalDate endOfSummerZone = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atDay(1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));
		LocalDate endOfWinterZone = YearMonth.of(YearMonth.now().getYear(), Month.APRIL).atDay(1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

		boolean isBeforeWinterZone = localDateTime.isBefore(LocalDateTime.of(endOfSummerZone, LocalTime.of(02, 00)));
		boolean isBeforeSummerZone = localDateTime.isBefore(LocalDateTime.of(endOfWinterZone, LocalTime.of(02, 00)));

		if (capitalsRepository.isTimeChanged(capital) && isBeforeWinterZone && !isBeforeSummerZone) {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getSummerUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		} else {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getWinterUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		}

	}

	public String getTimeForEuropeCapital(String capital) {

		LocalDateTime localDateTime = LocalDateTime.now()
				.atZone(ZoneId.ofOffset("UTC", ZoneOffset.of(capitalsRepository.getWinterUTC(capital))))
				.toLocalDateTime();
		Instant instant = Instant.now();

		LocalDate endOfSummerZone = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atEndOfMonth()
				.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
		LocalDate endOfWinterZone = YearMonth.of(YearMonth.now().getYear(), Month.MARCH).atEndOfMonth()
				.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

		boolean isBeforeWinterZone = localDateTime.isBefore(LocalDateTime.of(endOfSummerZone, LocalTime.of(02, 00)));
		boolean isBeforeSummerZone = localDateTime.isBefore(LocalDateTime.of(endOfWinterZone, LocalTime.of(02, 00)));

		if (capitalsRepository.isTimeChanged(capital) && isBeforeWinterZone && !isBeforeSummerZone) {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getSummerUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		} else {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getWinterUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		}

	}

	public String getTimeForNorthAmericaCapital(String capital) {

		LocalDateTime localDateTime = LocalDateTime.now()
				.atZone(ZoneId.ofOffset("UTC", ZoneOffset.of(capitalsRepository.getWinterUTC(capital))))
				.toLocalDateTime();
		Instant instant = Instant.now();

		LocalDate endOfSummerZone = YearMonth.of(YearMonth.now().getYear(), Month.NOVEMBER).atDay(1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));
		LocalDate endOfWinterZone = YearMonth.of(YearMonth.now().getYear(), Month.MARCH).atDay(1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY)).with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

		boolean isBeforeWinterZone = localDateTime.isBefore(LocalDateTime.of(endOfSummerZone, LocalTime.of(02, 00)));
		boolean isBeforeSummerZone = localDateTime.isBefore(LocalDateTime.of(endOfWinterZone, LocalTime.of(02, 00)));

		LocalDate endOfSummerZoneMexico = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atEndOfMonth()
				.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
		LocalDate endOfWinterZoneMexico = YearMonth.of(YearMonth.now().getYear(), Month.APRIL).atDay(1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

		boolean isBeforeWinterZoneMexico = localDateTime
				.isBefore(LocalDateTime.of(endOfSummerZoneMexico, LocalTime.of(02, 00)));
		boolean isBeforeSummerZoneMexico = localDateTime
				.isBefore(LocalDateTime.of(endOfWinterZoneMexico, LocalTime.of(02, 00)));

		if (!capital.equals("mexico") && capitalsRepository.isTimeChanged(capital) && isBeforeWinterZone
				&& !isBeforeSummerZone) {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getSummerUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		} else if (capitalsRepository.isTimeChanged(capital) && isBeforeWinterZoneMexico && !isBeforeSummerZoneMexico) {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getSummerUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		} else {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getWinterUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		}

	}

	public String getTimeForSouthAmericaCapital(String capital) {

		LocalDateTime localDateTime = LocalDateTime.now()
				.atZone(ZoneId.ofOffset("UTC", ZoneOffset.of(capitalsRepository.getWinterUTC(capital))))
				.toLocalDateTime();
		Instant instant = Instant.now();

		LocalDate endOfSummerZoneChile = YearMonth.of(YearMonth.now().getYear(), Month.APRIL).atDay(1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));
		LocalDate endOfWinterZoneChile = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atDay(1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

		boolean isBeforeWinterZoneChile = localDateTime
				.isBefore(LocalDateTime.of(endOfSummerZoneChile, LocalTime.of(00, 00)));
		boolean isBeforeSummerZoneChile = localDateTime
				.isBefore(LocalDateTime.of(endOfWinterZoneChile, LocalTime.of(00, 00)));

		LocalDate endOfSummerZoneParaguay = YearMonth.of(YearMonth.now().getYear(), Month.MARCH).atDay(1)
				.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.SUNDAY)).minusDays(1);
		LocalDate endOfWinterZoneParaguay = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atDay(1)
				.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

		boolean isBeforeWinterZoneParaguay = localDateTime
				.isBefore(LocalDateTime.of(endOfSummerZoneParaguay, LocalTime.of(23, 00)));
		boolean isBeforeSummerZoneParaguay = localDateTime
				.isBefore(LocalDateTime.of(endOfWinterZoneParaguay, LocalTime.of(00, 00)));

		if (capital.equals("santiago") && capitalsRepository.isTimeChanged(capital)
				&& (isBeforeWinterZoneChile || isBeforeSummerZoneChile)
				&& (!isBeforeWinterZoneChile || !isBeforeSummerZoneChile)) {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getSummerUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);

		} else if (capital.equals("asuncion") && capitalsRepository.isTimeChanged(capital)
				&& (isBeforeWinterZoneParaguay && isBeforeSummerZoneParaguay)
				|| (!isBeforeWinterZoneChile && !isBeforeSummerZoneChile)) {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getSummerUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);

		} else {
			ZoneOffset zoneOffset = ZoneOffset.of(capitalsRepository.getWinterUTC(capital));
			return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
		}

	}

	public boolean isCorrectContinent(String capital, String continent) {
		return capitalsRepository.getContinentName(capital).equals(continent);
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
