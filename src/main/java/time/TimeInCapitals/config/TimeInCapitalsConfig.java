package time.TimeInCapitals.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import time.TimeInCapitals.data.CapitalsData;

@Configuration
@PropertySource("classpath:pathToTheEuropaFile.properties")
//@ConfigurationProperties(prefix = "source")
public class TimeInCapitalsConfig {

	@Bean(name = "getCapitalsData")
	public CapitalsData getDataFromFile(@Value("${source.pathToEurope}") String pathToEurope) {
		
		String[] dataFromFile;
		StringBuilder stringBuilder = new StringBuilder();
		HashMap<String, String> utcSummerCapitals = new HashMap<>();
		File file = new File(pathToEurope);

		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
			while (buffer.ready()) {
				stringBuilder.append(buffer.readLine()).append(" ");
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		dataFromFile = stringBuilder.toString().split(" ");

		for (int i = 1; i < dataFromFile.length; i += 5) {
			utcSummerCapitals.put(dataFromFile[i], dataFromFile[i + 1]);
		}

		return new CapitalsData(utcSummerCapitals);
	}
}