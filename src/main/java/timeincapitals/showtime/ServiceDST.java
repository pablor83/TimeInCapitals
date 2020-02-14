package timeincapitals.showtime;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceDST {

	private Clock clock;

	@Autowired
	public ServiceDST(Clock clock) {
		this.clock = clock;
	}

	public boolean isDSTForAustralia(String winterZone, String capital) {

		String winterUTC = "UTC" + winterZone;

		LocalDateTime localDateTime = LocalDateTime.now(clock.withZone(ZoneId.of(winterUTC)));

		if (capital.equals("suva")) {
			LocalDate startWinterZoneSuva = YearMonth.of(YearMonth.now().getYear(), Month.JANUARY).atDay(1)
					.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.SUNDAY));

			LocalDate startSummerZoneSuva = YearMonth.of(YearMonth.now().getYear(), Month.NOVEMBER).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			boolean isSumerTimeSuva = localDateTime
					.isAfter(LocalDateTime.of(startWinterZoneSuva, LocalTime.of(2, 0).minusSeconds(1)));
			boolean isWinterTimeSuva = localDateTime
					.isAfter(LocalDateTime.of(startSummerZoneSuva, LocalTime.of(2, 0).minusSeconds(1)));

			return (!isSumerTimeSuva && !isWinterTimeSuva) || (isSumerTimeSuva && isWinterTimeSuva);

		}

		else if (capital.equals("wellington") || capital.equals("apia")) {

			LocalDate startWinterZoneWellingtonAndApia = YearMonth.of(YearMonth.now().getYear(), Month.APRIL).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			LocalDate startSummerZoneWellingtonAndApia = YearMonth.of(YearMonth.now().getYear(), Month.SEPTEMBER)
					.atEndOfMonth().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

			if (capital.equals("wellington")) {
				boolean isSumerTimeWellington = localDateTime.isAfter(
						LocalDateTime.of(startWinterZoneWellingtonAndApia, LocalTime.of(2, 0).minusSeconds(1)));
				boolean isWinterTimeWellington = localDateTime.isAfter(
						LocalDateTime.of(startSummerZoneWellingtonAndApia, LocalTime.of(2, 0).minusSeconds(1)));

				return (isSumerTimeWellington && isWinterTimeWellington) || (isSumerTimeWellington && isWinterTimeWellington);
			}

			else {
				boolean isSumerTimeApia = localDateTime.isAfter(
						LocalDateTime.of(startWinterZoneWellingtonAndApia, LocalTime.of(3, 0).minusSeconds(1)));
				boolean isWinterTimeApia = localDateTime.isAfter(
						LocalDateTime.of(startSummerZoneWellingtonAndApia, LocalTime.of(3, 0).minusSeconds(1)));

				return (!isSumerTimeApia && !isWinterTimeApia) || (isSumerTimeApia && isWinterTimeApia);
			}

		}

		else if (capital.equals("kingstone")) {
			LocalDate startWinterZoneKingston = YearMonth.of(YearMonth.now().getYear(), Month.APRIL).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			LocalDate startSummerZoneKingston = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			boolean isSumerTimeKingston = localDateTime
					.isAfter(LocalDateTime.of(startWinterZoneKingston, LocalTime.of(2, 0).minusSeconds(1)));
			boolean isWinterTimeKingston = localDateTime
					.isAfter(LocalDateTime.of(startSummerZoneKingston, LocalTime.of(2, 0).minusSeconds(1)));

			return (!isSumerTimeKingston && !isWinterTimeKingston) || (isSumerTimeKingston && isWinterTimeKingston);

		}

		else {
			LocalDate startWinterZoneAustralia = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			LocalDate startSummerZoneAustralia = YearMonth.of(YearMonth.now().getYear(), Month.APRIL).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			boolean isSumerTimeAustralia = localDateTime
					.isAfter(LocalDateTime.of(startWinterZoneAustralia, LocalTime.of(2, 0).minusSeconds(1)));
			boolean isWinterTimeAustralia = localDateTime
					.isAfter(LocalDateTime.of(startSummerZoneAustralia, LocalTime.of(2, 0).minusSeconds(1)));

			return isSumerTimeAustralia && !isWinterTimeAustralia;

		}
	}

	public boolean isDSTForEurope(String winterZone) {

		String winterUTC = "UTC" + winterZone;

		LocalDateTime localDateTime = LocalDateTime.now(clock.withZone(ZoneId.of(winterUTC)));

		LocalDate startWinterZone = YearMonth
				.of(YearMonth.now(clock.withZone(ZoneId.of(winterUTC))).getYear(), Month.OCTOBER).atEndOfMonth()
				.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

		LocalDate startSummerZone = YearMonth
				.of(YearMonth.now(clock.withZone(ZoneId.of(winterUTC))).getYear(), Month.MARCH).atEndOfMonth()
				.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

		boolean isSummerTime = localDateTime
				.isAfter(LocalDateTime.of(startWinterZone, LocalTime.of(2, 0).minusSeconds(1)));
		boolean isWinterTime = localDateTime
				.isAfter(LocalDateTime.of(startSummerZone, LocalTime.of(2, 0).minusSeconds(1)));

		return isSummerTime && !isWinterTime;
	}

	public boolean isDSTForNorthAmerica(String winterZone, String capital) {

		String winterUTC = "UTC" + winterZone;

		LocalDateTime localDateTime = LocalDateTime.now(clock.withZone(ZoneId.of(winterUTC)));

		if (capital.equals("mexico")) {

			LocalDate startWinterZoneMexico = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atEndOfMonth()
					.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));

			LocalDate startSummerZoneMexico = YearMonth.of(YearMonth.now().getYear(), Month.APRIL).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			boolean isSummerTimeMexico = localDateTime
					.isAfter(LocalDateTime.of(startWinterZoneMexico, LocalTime.of(2, 0).minusSeconds(1)));

			boolean isWinterTimeMexico = localDateTime
					.isAfter(LocalDateTime.of(startSummerZoneMexico, LocalTime.of(2, 0).minusSeconds(1)));

			return isSummerTimeMexico && !isWinterTimeMexico;
		}

		else {

			LocalDate startWinterZone = YearMonth.of(YearMonth.now().getYear(), Month.NOVEMBER).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			LocalDate startSummerZone = YearMonth.of(YearMonth.now().getYear(), Month.MARCH).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY))
					.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

			boolean isSummerTime = localDateTime
					.isAfter(LocalDateTime.of(startWinterZone, LocalTime.of(2, 0).minusSeconds(1)));
			boolean isWinterTime = localDateTime
					.isAfter(LocalDateTime.of(startSummerZone, LocalTime.of(2, 0).minusSeconds(1)));

			return isSummerTime && !isWinterTime;
		}
	}

	public boolean isDSTForSouthAmerica(String winterZone, String capital) {

		String winterUTC = "UTC" + winterZone;

		LocalDateTime localDateTime = LocalDateTime.now(clock.withZone(ZoneId.of(winterUTC)));

		if (capital.equals("santiago")) {
			LocalDate startWinterZoneSantiago = YearMonth.of(YearMonth.now().getYear(), Month.APRIL).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			LocalDate startSummerZoneSantiago = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			boolean isSummerTimeSantiago = localDateTime
					.isAfter(LocalDateTime.of(startWinterZoneSantiago, LocalTime.of(0, 0).minusSeconds(1)));
			boolean isWinterTimeSantiago = localDateTime
					.isAfter(LocalDateTime.of(startSummerZoneSantiago, LocalTime.of(0, 0).minusSeconds(1)));

			return isSummerTimeSantiago && isWinterTimeSantiago;
		} else {
			LocalDate startWinterZoneAsuncion = YearMonth.of(YearMonth.now().getYear(), Month.MARCH).atDay(1)
					.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.SUNDAY)).minusDays(1);

			LocalDate startSummerZoneAsuncion = YearMonth.of(YearMonth.now().getYear(), Month.OCTOBER).atDay(1)
					.with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));

			boolean isSummerTimeAsuncion = localDateTime
					.isAfter(LocalDateTime.of(startWinterZoneAsuncion, LocalTime.of(23, 0).minusSeconds(1)));
			boolean isWinterTimeAsuncion = localDateTime
					.isAfter(LocalDateTime.of(startSummerZoneAsuncion, LocalTime.of(0, 0).minusSeconds(1)));

			return isSummerTimeAsuncion && isWinterTimeAsuncion;
		}
	}

}
