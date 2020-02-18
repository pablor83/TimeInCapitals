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

	@DisplayName("3.Should be 3 AM in Warsaw the day of summer time change")
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

	@DisplayName("4.Should be 14 in Warsaw end of the year")
	@Test
	public void shouldBe14InWarsawEndOfTheYear() {
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
	
	@DisplayName("6.Should be 2AM in London the day of winter time change")
	@Test
	public void shouldBe2AMInLondonTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-03-29T02:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("london")).thenReturn("+0");
		when(capitalsRepository.getSummerUTC("london")).thenReturn("+1");
		when(capitalsRepository.isTimeChanged("london")).thenReturn(true);
		when(serviceDST.isDSTForEurope("+0")).thenReturn(true);
		assertEquals("03:00:00 29/03/2020", localTimeService.getTimeForEuropeCapital("london"));
	}
	
	@DisplayName("7.Should be 14 in Beijing")
	@Test
	public void shouldBe14InBeijing() {
		clock = Clock.fixed(Instant.parse("2020-07-01T12:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("beijing")).thenReturn("+8");
		assertEquals("20:00:00 01/07/2020", localTimeService.getTimeForAsiaCapital("beijing"));
	}
	
	@DisplayName("8.Should be 2AM in Ottawa the day of winter time change")
	@Test
	public void shouldBe14InOttawaTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-11-01T07:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		when(capitalsRepository.isTimeChanged("ottawa")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-5", "ottawa")).thenReturn(false);
		assertEquals("02:00:00 01/11/2020", localTimeService.getTimeForEuropeCapital("ottawa"));
	}
	
	@DisplayName("9.Should be 2AM in Ottawa the day of summer time change")
	@Test
	public void shouldBe03AMInOttawaTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-03-08T07:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		when(capitalsRepository.getSummerUTC("ottawa")).thenReturn("-4");
		when(capitalsRepository.isTimeChanged("ottawa")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-5", "ottawa")).thenReturn(true);
		assertEquals("03:00:00 08/03/2020", localTimeService.getTimeForNorthAmericaCapital("ottawa"));
	}
	
	@DisplayName("10.Should be 2AM in Ottawa end of the year")
	@Test
	public void shouldBe100501InOttawaEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		when(capitalsRepository.isTimeChanged("ottawa")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-5", "ottawa")).thenReturn(false);
		assertEquals("10:05:01 31/12/2020", localTimeService.getTimeForEuropeCapital("ottawa"));
	}
	
	@DisplayName("11.Should be 2AM in Ottawa The beginning of the year")
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
	
	@DisplayName("12.Should be 2AM in Ottawa")
	@Test
	public void shouldBe17AMInOttawa() {
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
	public void shouldBe14InMexicoTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-25T08:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(false);
		assertEquals("02:00:00 25/10/2020", localTimeService.getTimeForEuropeCapital("mexico"));
	}
	
	@DisplayName("14.Should be 2AM in Mexico the day of summer time change")
	@Test
	public void shouldBe03AMInMexicoTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-05T08:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.getSummerUTC("mexico")).thenReturn("-5");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(true);
		assertEquals("03:00:00 05/04/2020", localTimeService.getTimeForNorthAmericaCapital("mexico"));
	}
	
	@DisplayName("15.Should be 2AM in Mexico end of the year")
	@Test
	public void shouldBe100501InMexicoEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(false);
		assertEquals("09:05:01 31/12/2020", localTimeService.getTimeForEuropeCapital("mexico"));
	}
	
	@DisplayName("16.Should be 2AM in Mexico The beginning of the year")
	@Test
	public void shouldBe05AMInMexicoTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(false);
		assertEquals("04:00:00 01/01/2020", localTimeService.getTimeForNorthAmericaCapital("mexico"));
	}
	
	@DisplayName("17.Should be 2AM in Mexico")
	@Test
	public void shouldBe17AMInMexico() {
		clock = Clock.fixed(Instant.parse("2020-10-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		when(capitalsRepository.getSummerUTC("mexico")).thenReturn("-5");
		when(capitalsRepository.isTimeChanged("mexico")).thenReturn(true);
		when(serviceDST.isDSTForNorthAmerica("-6", "mexico")).thenReturn(true);
		assertEquals("16:00:00 01/10/2020", localTimeService.getTimeForNorthAmericaCapital("mexico"));
	}
	
	@DisplayName("18.Should be 2AM in BuenosAires end of the year")
	@Test
	public void shouldBe100501InBuenosAiresEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("buenos_aires")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("buenos_aires")).thenReturn(false);		
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForSouthAmericaCapital("buenos_aires"));
	}
	
	@DisplayName("19.Should be 2AM in BuenosAires The beginning of the year")
	@Test
	public void shouldBe05AMInBuenosAiresTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("buenos_aires")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("buenos_aires")).thenReturn(false);
		assertEquals("07:00:00 01/01/2020", localTimeService.getTimeForSouthAmericaCapital("buenos_aires"));
	}
	
	@DisplayName("20.Should be 2AM in Mexico")
	@Test
	public void shouldBe17AMInBuenosAires() {
		clock = Clock.fixed(Instant.parse("2020-10-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("buenos_aires")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("buenos_aires")).thenReturn(false);
		assertEquals("18:00:00 01/10/2020", localTimeService.getTimeForSouthAmericaCapital("buenos_aires"));
	}
	
	@DisplayName("21.Should be 2AM in Santiago the day of winter time change")
	@Test
	public void shouldBe14InSantiagoTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-05T04:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(false);
		assertEquals("00:00:00 05/04/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("22.Should be 2AM in Santiago the day of summer time change")
	@Test
	public void shouldBe03AMInSantiagoTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-04-05T04:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("santiago")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(true);
		assertEquals("01:00:00 05/04/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("23.Should be 2AM in Santiago end of the year")
	@Test
	public void shouldBe100501InSantiagoEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("santiago")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(true);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("24.Should be 2AM in Santiago The beginning of the year")
	@Test
	public void shouldBe05AMInSantiagoTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("santiago")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(true);
		assertEquals("07:00:00 01/01/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("25.Should be 2AM in Santiago")
	@Test
	public void shouldBe17AMInSantiago() {
		clock = Clock.fixed(Instant.parse("2020-09-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("santiago")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("santiago")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("santiago")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "santiago")).thenReturn(true);
		assertEquals("18:00:00 01/09/2020", localTimeService.getTimeForSouthAmericaCapital("santiago"));
	}
	
	@DisplayName("26.Should be 2AM in Asuncion the day of winter time change")
	@Test
	public void shouldBe14InAsuncionTheDayOfWinterTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-03-22T04:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(false);
		assertEquals("00:00:00 22/03/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("27.Should be 2AM in Asuncion the day of summer time change")
	@Test
	public void shouldBe03AMInAsuncionTheDayOfSummerTimeChange() {
		clock = Clock.fixed(Instant.parse("2020-10-04T04:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("asuncion")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(true);
		assertEquals("01:00:00 04/10/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("28.Should be 2AM in Asuncion end of the year")
	@Test
	public void shouldBe100501InAsuncionEndOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-12-31T15:05:01.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("asuncion")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(true);
		assertEquals("12:05:01 31/12/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("29.Should be 2AM in Asuncion The beginning of the year")
	@Test
	public void shouldBe05AMInAsuncionTheBeginningOfTheYear() {
		clock = Clock.fixed(Instant.parse("2020-01-01T10:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("asuncion")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(true);
		assertEquals("07:00:00 01/01/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}
	
	@DisplayName("30.Should be 2AM in Asuncion")
	@Test
	public void shouldBe17AMInAsuncion() {
		clock = Clock.fixed(Instant.parse("2020-09-01T21:00:00.00Z"), ZoneId.of("Z"));
		localTimeService = new LocalTimeService(capitalsRepository, serviceDST, clock);
		when(capitalsRepository.getWinterUTC("asuncion")).thenReturn("-4");
		when(capitalsRepository.getSummerUTC("asuncion")).thenReturn("-3");
		when(capitalsRepository.isTimeChanged("asuncion")).thenReturn(true);
		when(serviceDST.isDSTForSouthAmerica("-4", "asuncion")).thenReturn(true);
		assertEquals("18:00:00 01/09/2020", localTimeService.getTimeForSouthAmericaCapital("asuncion"));
	}	
}
