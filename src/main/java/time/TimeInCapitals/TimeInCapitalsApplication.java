package time.TimeInCapitals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import time.TimeInCapitals.config.TimeInCapitalsConfig;
import time.TimeInCapitals.data.CapitalsData;

@SpringBootApplication
public class TimeInCapitalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeInCapitalsApplication.class, args);
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TimeInCapitalsConfig.class);
		
		CapitalsData timeInCapitalsConfig = (CapitalsData) context.getBean("getCapitalsData");
	}

}
