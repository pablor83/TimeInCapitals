package time.TimeInCapitals;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import time.TimeInCapitals.showtime.LocalTimeService;

public class TimeInCapitalsApplicationTestsConfig {
			
	@Bean
	@Primary
	Clock getTestClock() {
		return Clock.fixed(LocalDateTime.of(2019, 10, 27, 02, 00, 00).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
	}
}
