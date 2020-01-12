package time.TimeInCapitals;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import time.TimeInCapitals.showtime.LocalTimeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(TimeInCapitalsApplicationTestsConfig.class)
public class TimeInCapitalsApplicationTests {
		
	
	@Autowired
	private LocalTimeService localTimeService;
	

	@DisplayName("Should return client time")

	public void shouldReturnClientTime() {
		
	}
	
	@DisplayName("Should return summer time for London 02:00:00")
	@Test
	public void shouldReturnSummerTimeForLondon2AM() {
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("london"));
		
	}

	@DisplayName("Should return summer time for London 02:00:00")
	@Test
	public void shouldReturnWinterTimeForWarsaw2AM() {
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	
//	@DisplayName("Should return winter time for Mexico")
//	@Test
//	public void shouldReturnWinterTimeForMexico() {
//		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("warsaw"));
//	}

}
