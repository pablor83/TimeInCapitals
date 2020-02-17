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
}
