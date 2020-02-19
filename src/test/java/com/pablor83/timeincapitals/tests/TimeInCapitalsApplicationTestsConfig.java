package com.pablor83.timeincapitals.tests;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TimeInCapitalsApplicationTestsConfig {
					
//	@Bean
//	@Primary
//	Clock getTestClock() {
//		return Clock.fixed(LocalDateTime.of(2019, 10, 27, 02, 00, 00).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
//	}
}
