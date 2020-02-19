package com.pablor83.timeincapitals.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pablor83.timeincapitals.repository.CapitalsRepository;
import com.pablor83.timeincapitals.showtime.LocalTimeService;
import com.pablor83.timeincapitals.showtime.ServiceDST;

//@Import(TimeInCapitalsApplicationTestsConfig.class)
@ExtendWith(SpringExtension.class)
public class TimeInCapitalsApplicationTests {

	@Mock
	private CapitalsRepository capitalsRepository;

	@Mock
	private ServiceDST serviceDST;

	private Clock clock;

	@InjectMocks
	private LocalTimeService localTimeService;

	@DisplayName("Should return client time")
	public void shouldReturnClientTime() {

	}

	@DisplayName("1.Should be 2AM in Warsaw the day of winter time change")
	@Test
	public void shouldBe2AMInWarsawTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2019-10-27T01:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		when(capitalsRepository.isTimeChanged("warsaw")).thenReturn(true);
		when(serviceDST.isDSTForEurope("+1")).thenReturn(false);
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("warsaw"));
	}

	@DisplayName("2.Should be 13 in Warsaw. The beginning of the year")
	@Test
	public void shouldBe13InWarsawBeginningOfYear() {
		clock = Clock.fixed(Instant.parse("2020-01-15T12:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		when(capitalsRepository.isTimeChanged("warsaw")).thenReturn(true);
		when(serviceDST.isDSTForEurope("+1")).thenReturn(false);
		assertEquals("13:00:00 15/01/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}

	@DisplayName("3.Should be 3AM in Warsaw the day of summer time change")
	@Test
	public void shouldBe3AMInWarsawTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-03-29T01:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		when(capitalsRepository.getSummerUTC("warsaw")).thenReturn("+2");
		when(capitalsRepository.isTimeChanged("warsaw")).thenReturn(true);
		when(serviceDST.isDSTForEurope("+1")).thenReturn(true);
		assertEquals("03:00:00 29/03/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}

	@DisplayName("4.Should be 13 in Warsaw end of the year")
	@Test
	public void shouldBe13InWarsawEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-01T12:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		when(capitalsRepository.isTimeChanged("warsaw")).thenReturn(true);
		when(serviceDST.isDSTForEurope("+1")).thenReturn(false);
		assertEquals("13:00:00 01/12/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	
	@DisplayName("5.Should be 2AM in London the day of winter time change")
	@Test
	public void shouldBe2AMInLondonTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2019-10-27T02:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("london")).thenReturn("+0");
		when(capitalsRepository.isTimeChanged("london")).thenReturn(true);
		when(serviceDST.isDSTForEurope("+0")).thenReturn(false);
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("london"));
	}
	
	@DisplayName("6.Should be 3AM in London the day of winter time change")
	@Test
	public void shouldBe3AMInLondonTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-03-29T02:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("london")).thenReturn("+0");
		when(capitalsRepository.getSummerUTC("london")).thenReturn("+1");
		when(capitalsRepository.isTimeChanged("london")).thenReturn(true);
		when(serviceDST.isDSTForEurope("+0")).thenReturn(true);
		assertEquals("03:00:00 29/03/2020", localTimeService.getTimeForEuropeCapital("london"));
	}
	
	@DisplayName("7.Should be 20 in Beijing")
	@Test
	public void shouldBe20InBeijing() {
		clock = Clock.fixed(Instant.parse("2020-07-01T12:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("beijing")).thenReturn("+8");
		when(capitalsRepository.isTimeChanged("beijing")).thenReturn(false);
		assertEquals("20:00:00 01/07/2020", localTimeService.getTimeForAsiaCapital("beijing"));
	}
	
	@DisplayName("8.Should be 2AM in Ottawa the day of winter time change")
	@Test
	public void shouldBe2AMInOttawaTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-11-01T07:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		when(capitalsRepository.isTimeChanged("ottawa")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-5", "ottawa")).thenReturn(false);
		assertEquals("02:00:00 01/11/2020", localTimeService.getTimeForEuropeCapital("ottawa"));
	}
	
	@DisplayName("9.Should be 3AM in Ottawa the day of summer time change")
	@Test
	public void shouldBe3AMInOttawaTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-03-08T07:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		when(capitalsRepository.getSummerUTC("ottawa")).thenReturn("-4");
		when(capitalsRepository.isTimeChanged("ottawa")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-5", "ottawa")).thenReturn(true);
		assertEquals("03:00:00 08/03/2020", localTimeService.getTimeForNorthAmericaCapital("ottawa"));
	}
	
	@DisplayName("10.Should be 10:05:01AM in Ottawa end of the year")
	@Test
	public void shouldBe100501AMInOttawaEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		when(capitalsRepository.isTimeChanged("ottawa")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-5", "ottawa")).thenReturn(false);
		assertEquals("10:05:01 31/12/2020", localTimeService.getTimeForEuropeCapital("ottawa"));
	}
	
	@DisplayName("11.Should be 5AM in Ottawa The beginning of the year")
	@Test
	public void shouldBe05AMInOttawaTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		when(capitalsRepository.getSummerUTC("ottawa")).thenReturn("-4");
		when(capitalsRepository.isTimeChanged("ottawa")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-5", "ottawa")).thenReturn(false);
		assertEquals("05:00:00 01/01/2020", localTimeService.getTimeForNorthAmericaCapital("ottawa"));
	}
	
	@DisplayName("12.Should be 17 in Ottawa")
	@Test
	public void shouldBe17InOttawa() {
		clock = Clock.fixed(Instant.parse("2020-10-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		when(capitalsRepository.getSummerUTC("ottawa")).thenReturn("-4");
		when(capitalsRepository.isTimeChanged("ottawa")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-5", "ottawa")).thenReturn(true);
		assertEquals("17:00:00 01/10/2020", localTimeService.getTimeForNorthAmericaCapital("ottawa"));
	}
	
	@DisplayName("13.Should be 2AM in Mexico the day of winter time change")
	@Test
	public void shouldBe2AMInMexicoTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-25T08:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(false);
		assertEquals("02:00:00 25/10/2020", localTimeService.getTimeForEuropeCapital("mexico"));
	}
	
	@DisplayName("14.Should be 3AM in Mexico the day of summer time change")
	@Test
	public void shouldBe3AMInMexicoTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-05T08:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.getSummerUTC("mexico")).thenReturn("-5");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(true);
		assertEquals("03:00:00 05/04/2020", localTimeService.getTimeForNorthAmericaCapital("mexico"));
	}
	
	@DisplayName("15.Should be 09:05:01AM in Mexico end of the year")
	@Test
	public void shouldBe090501AMInMexicoEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(false);
		assertEquals("09:05:01 31/12/2020", localTimeService.getTimeForEuropeCapital("mexico"));
	}
	
	@DisplayName("16.Should be 4AM in Mexico The beginning of the year")
	@Test
	public void shouldBe4AMInMexicoTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(false);
		assertEquals("04:00:00 01/01/2020", localTimeService.getTimeForNorthAmericaCapital("mexico"));
	}
	
	@DisplayName("17.Should be 16 in Mexico")
	@Test
	public void shouldBe16InMexico() {
		clock = Clock.fixed(Instant.parse("2020-10-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.getSummerUTC("mexico")).thenReturn("-5");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(true);
		assertEquals("16:00:00 01/10/2020", localTimeService.getTimeForNorthAmericaCapital("mexico"));
	}
	
	@DisplayName("18.Should be 12PM in BuenosAires end of the year")
	@Test
	public void shouldBe120501PMInBuenosAiresEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("buenos_aires")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("buenos_aires")).thenReturn(false);		
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForSouthAmericaCapital("buenos_aires"));
	}
	
	@DisplayName("19.Should be 7AM in BuenosAires The beginning of the year")
	@Test
	public void shouldBe7AMInBuenosAiresTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("buenos_aires")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("buenos_aires")).thenReturn(false);
		assertEquals("07:00:00 01/01/2020", localTimeService.getTimeForSouthAmericaCapital("buenos_aires"));
	}
	
	@DisplayName("20.Should be 18 in Mexico")
	@Test
	public void shouldBe18InBuenosAires() {
		clock = Clock.fixed(Instant.parse("2020-10-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("buenos_aires")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("buenos_aires")).thenReturn(false);
		assertEquals("18:00:00 01/10/2020", localTimeService.getTimeForSouthAmericaCapital("buenos_aires"));
	}
	
	@DisplayName("21.Should be 12AM in Santiago the day of winter time change")
	@Test
	public void shouldBe12AMInSantiagoTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-05T04:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(false);
		assertEquals("00:00:00 05/04/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("22.Should be 1AM in Santiago the day of summer time change")
	@Test
	public void shouldBe1AMInSantiagoTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-05T04:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("santiago")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(true);
		assertEquals("01:00:00 05/04/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("23.Should be 12PM in Santiago end of the year")
	@Test
	public void shouldBe120501PMInSantiagoEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("santiago")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(true);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("24.Should be 7AM in Santiago The beginning of the year")
	@Test
	public void shouldBe7AMInSantiagoTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("santiago")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(true);
		assertEquals("07:00:00 01/01/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("25.Should be 18 in Santiago")
	@Test
	public void shouldBe18InSantiago() {
		clock = Clock.fixed(Instant.parse("2020-09-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("santiago")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(true);
		assertEquals("18:00:00 01/09/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("26.Should be 12AM in Asuncion the day of winter time change")
	@Test
	public void shouldBe12AMInAsuncionTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-03-22T04:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(false);
		assertEquals("00:00:00 22/03/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("27.Should be 1AM in Asuncion the day of summer time change")
	@Test
	public void shouldBe1AMInAsuncionTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-04T04:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("asuncion")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(true);
		assertEquals("01:00:00 04/10/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("28.Should be 12:05:01PM in Asuncion end of the year")
	@Test
	public void shouldBe120501PMInAsuncionEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("asuncion")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(true);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("29.Should be 7AM in Asuncion The beginning of the year")
	@Test
	public void shouldBe7AMInAsuncionTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("asuncion")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(true);
		assertEquals("07:00:00 01/01/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("30.Should be 18 in Asuncion")
	@Test
	public void shouldBe18InAsuncion() {
		clock = Clock.fixed(Instant.parse("2020-09-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("asuncion")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(true);
		assertEquals("18:00:00 01/09/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("31.Should be 2AM in Canberra the day of winter time change")
	@Test
	public void shouldBe2AMInCanberraTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-03T16:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("canberra")).thenReturn("+10");
		when(capitalsRepository.isTimeChanged("canberra")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+10", "canberra")).thenReturn(false);
		assertEquals("02:00:00 04/10/2020", localTimeService.getTimeForAustraliaCapital("canberra"));
	}
	
	@DisplayName("32.Should be 3AM in Canberra the day of summer time change")
	@Test
	public void shouldBe03AMInCanberraTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-04T16:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("canberra")).thenReturn("+10");
		when(capitalsRepository.getSummerUTC("canberra")).thenReturn("+11");
		when(capitalsRepository.isTimeChanged("canberra")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+10", "canberra")).thenReturn(true);
		assertEquals("03:00:00 05/04/2020", localTimeService.getTimeForAustraliaCapital("canberra"));
	}
	
	@DisplayName("33.Should be 12:05:01PM in Canberra end of the year")
	@Test
	public void shouldBe120501PMInCanberraEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T02:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("canberra")).thenReturn("+10");
		when(capitalsRepository.isTimeChanged("canberra")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+10", "canberra")).thenReturn(false);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForAustraliaCapital("canberra"));
	}
	
	@DisplayName("34.Should be 10 in Canberra The beginning of the year")
	@Test
	public void shouldBe10AMInCanberraTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T00:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("canberra")).thenReturn("+10");
		when(capitalsRepository.isTimeChanged("canberra")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+10", "canberra")).thenReturn(false);
		assertEquals("10:00:00 01/01/2020", localTimeService.getTimeForAustraliaCapital("canberra"));
	}
	
	@DisplayName("35.Should be 8 AM in Canberra")
	@Test
	public void shouldBe8AMAMInCanberra() {
		clock = Clock.fixed(Instant.parse("2020-09-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("canberra")).thenReturn("+10");
		when(capitalsRepository.getSummerUTC("canberra")).thenReturn("+11");
		when(capitalsRepository.isTimeChanged("canberra")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+10", "canberra")).thenReturn(true);
		assertEquals("08:00:00 02/09/2020", localTimeService.getTimeForAustraliaCapital("canberra"));
	}
	
	@DisplayName("36.Should be 2AM in Suva the day of winter time change")
	@Test
	public void shouldBe2AMInSuvaTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-01-11T14:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("suva")).thenReturn("+12");
		when(capitalsRepository.isTimeChanged("suva")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "suva")).thenReturn(false);
		assertEquals("02:00:00 12/01/2020", localTimeService.getTimeForAustraliaCapital("suva"));
	}
	
	@DisplayName("37.Should be 3AM in Suva the day of summer time change")
	@Test
	public void shouldBe03AMInSuvaTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-31T14:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("suva")).thenReturn("+12");
		when(capitalsRepository.getSummerUTC("suva")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("suva")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "suva")).thenReturn(true);
		assertEquals("03:00:00 01/11/2020", localTimeService.getTimeForAustraliaCapital("suva"));
	}
	
	@DisplayName("38.Should be 12:05:01PM in Suva end of the year")
	@Test
	public void shouldBe120501PMInSuvaEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-30T23:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("suva")).thenReturn("+12");
		when(capitalsRepository.getSummerUTC("suva")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("suva")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "suva")).thenReturn(true);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForAustraliaCapital("suva"));
	}
	
	@DisplayName("39.Should be 13 in Suva The beginning of the year")
	@Test
	public void shouldBe13InSuvaTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T00:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("suva")).thenReturn("+12");
		when(capitalsRepository.getSummerUTC("suva")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("suva")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "suva")).thenReturn(true);
		assertEquals("13:00:00 01/01/2020", localTimeService.getTimeForAustraliaCapital("suva"));
	}
	
	@DisplayName("40.Should be 10 AM in Suva")
	@Test
	public void shouldBe17AMInSuva() {
		clock = Clock.fixed(Instant.parse("2020-09-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("suva")).thenReturn("+12");
		when(capitalsRepository.getSummerUTC("suva")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("suva")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "suva")).thenReturn(true);
		assertEquals("10:00:00 02/09/2020", localTimeService.getTimeForAustraliaCapital("suva"));
	}
	
	@DisplayName("41.Should be 2AM in Wellington the day of winter time change")
	@Test
	public void shouldBe2AMInWellingtonTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-04T14:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("wellington")).thenReturn("+12");
		when(capitalsRepository.isTimeChanged("wellington")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "wellington")).thenReturn(false);
		assertEquals("02:00:00 05/04/2020", localTimeService.getTimeForAustraliaCapital("wellington"));
	}
	
	@DisplayName("42.Should be 3AM in Wellington the day of summer time change")
	@Test
	public void shouldBe03AMInWellingtonTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-26T14:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("wellington")).thenReturn("+12");
		when(capitalsRepository.getSummerUTC("wellington")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("wellington")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "wellington")).thenReturn(true);
		assertEquals("03:00:00 27/10/2020", localTimeService.getTimeForAustraliaCapital("wellington"));
	}
	
	@DisplayName("43.Should be 12:05:01PM in Wellington end of the year")
	@Test
	public void shouldBe120501PMInWellingtonEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-30T23:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("wellington")).thenReturn("+12");
		when(capitalsRepository.getSummerUTC("wellington")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("wellington")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "wellington")).thenReturn(true);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForAustraliaCapital("wellington"));
	}
	
	@DisplayName("44.Should be 13 in Wellington The beginning of the year")
	@Test
	public void shouldBe13InWellingtonTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T00:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("wellington")).thenReturn("+12");
		when(capitalsRepository.getSummerUTC("wellington")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("wellington")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "wellington")).thenReturn(true);
		assertEquals("13:00:00 01/01/2020", localTimeService.getTimeForAustraliaCapital("wellington"));
	}
	
	@DisplayName("45.Should be 10 AM in Wellington")
	@Test
	public void shouldBe17AMInWellington() {
		clock = Clock.fixed(Instant.parse("2020-09-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("wellington")).thenReturn("+12");
		when(capitalsRepository.getSummerUTC("wellington")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("wellington")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+12", "wellington")).thenReturn(true);
		assertEquals("10:00:00 02/09/2020", localTimeService.getTimeForAustraliaCapital("wellington"));
	}
	
	@DisplayName("41.Should be 2AM in Apia the day of winter time change")
	@Test
	public void shouldBe2AMInApiaTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-04T14:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("apia")).thenReturn("+13");
		when(capitalsRepository.isTimeChanged("apia")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+13", "apia")).thenReturn(false);
		assertEquals("03:00:00 05/04/2020", localTimeService.getTimeForAustraliaCapital("apia"));
	}
	
	@DisplayName("42.Should be 3AM in Apia the day of summer time change")
	@Test
	public void shouldBe03AMInApiaTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-26T14:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("apia")).thenReturn("+13");
		when(capitalsRepository.getSummerUTC("apia")).thenReturn("+14");
		when(capitalsRepository.isTimeChanged("apia")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+13", "apia")).thenReturn(true);
		assertEquals("04:00:00 27/10/2020", localTimeService.getTimeForAustraliaCapital("apia"));
	}
	
	@DisplayName("43.Should be 12:05:01PM in Apia end of the year")
	@Test
	public void shouldBe120501PMInApiaEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-30T22:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("apia")).thenReturn("+13");
		when(capitalsRepository.getSummerUTC("apia")).thenReturn("+14");
		when(capitalsRepository.isTimeChanged("apia")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+13", "apia")).thenReturn(true);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForAustraliaCapital("apia"));
	}
	
	@DisplayName("44.Should be 14 in Apia The beginning of the year")
	@Test
	public void shouldBe14InApiaTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T00:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("apia")).thenReturn("+13");
		when(capitalsRepository.getSummerUTC("apia")).thenReturn("+14");
		when(capitalsRepository.isTimeChanged("apia")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+13", "apia")).thenReturn(true);
		assertEquals("14:00:00 01/01/2020", localTimeService.getTimeForAustraliaCapital("apia"));
	}
	
	@DisplayName("45.Should be 10 AM in Apia")
	@Test
	public void shouldBe17AMInApia() {
		clock = Clock.fixed(Instant.parse("2020-09-01T20:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("apia")).thenReturn("+13");
		when(capitalsRepository.getSummerUTC("apia")).thenReturn("+14");
		when(capitalsRepository.isTimeChanged("apia")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+13", "apia")).thenReturn(true);
		assertEquals("10:00:00 02/09/2020", localTimeService.getTimeForAustraliaCapital("apia"));
	}
	
	@DisplayName("46.Should be 2AM in Kingston the day of winter time change")
	@Test
	public void shouldBe2AMInKingstonTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-04T15:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("kingston")).thenReturn("+11");
		when(capitalsRepository.isTimeChanged("kingston")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+11", "kingston")).thenReturn(false);
		assertEquals("02:00:00 05/04/2020", localTimeService.getTimeForAustraliaCapital("kingston"));
	}
	
	@DisplayName("47.Should be 3AM in Kingston the day of summer time change")
	@Test
	public void shouldBe03AMInKingstonTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-03T15:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("kingston")).thenReturn("+11");
		when(capitalsRepository.getSummerUTC("kingston")).thenReturn("+12");
		when(capitalsRepository.isTimeChanged("kingston")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+11", "kingston")).thenReturn(true);
		assertEquals("03:00:00 04/10/2020", localTimeService.getTimeForAustraliaCapital("kingston"));
	}
	
	@DisplayName("48.Should be 12:05:01PM in Kingston end of the year")
	@Test
	public void shouldBe120501PMInKingstonEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T00:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("kingston")).thenReturn("+11");
		when(capitalsRepository.getSummerUTC("kingston")).thenReturn("+12");
		when(capitalsRepository.isTimeChanged("kingston")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+11", "kingston")).thenReturn(true);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForAustraliaCapital("kingston"));
	}
	
	@DisplayName("49.Should be 14 in Kingston The beginning of the year")
	@Test
	public void shouldBe14InKingstonTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T02:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("kingston")).thenReturn("+11");
		when(capitalsRepository.getSummerUTC("kingston")).thenReturn("+12");
		when(capitalsRepository.isTimeChanged("kingston")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+11", "kingston")).thenReturn(true);
		assertEquals("14:00:00 01/01/2020", localTimeService.getTimeForAustraliaCapital("kingston"));
	}
	
	@DisplayName("50.Should be 10 AM in Kingston")
	@Test
	public void shouldBe17AMInKingston() {
		clock = Clock.fixed(Instant.parse("2020-09-01T22:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("kingston")).thenReturn("+11");
		when(capitalsRepository.getSummerUTC("kingston")).thenReturn("+12");
		when(capitalsRepository.isTimeChanged("kingston")).thenReturn(true);
		when(serviceDST.isDSTForAustralia("+11", "kingston")).thenReturn(true);
		assertEquals("10:00:00 02/09/2020", localTimeService.getTimeForAustraliaCapital("kingston"));
	}
	
	@DisplayName("51.Should be 13 in Tunis")
	@Test
	public void shouldBe13InTunis() {
		clock = Clock.fixed(Instant.parse("2020-07-01T12:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("tunis")).thenReturn("+1");
		when(capitalsRepository.isTimeChanged("tunis")).thenReturn(false);
		assertEquals("13:00:00 01/07/2020", localTimeService.getTimeForAsiaCapital("tunis"));
	}
}
