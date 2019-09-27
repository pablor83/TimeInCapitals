package time.TimeInCapitals.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import time.TimeInCapitals.data.CapitalsData;

@Configuration
public class TimeInCapitalsConfig {

	@Bean(name = "getCapitalsData")
	public CapitalsData getDataFromFile() {

		String[] regexResults;
		StringBuilder stringBuilder = new StringBuilder();
		HashMap<String, String> utcSummerCapitals = new HashMap<>();
		File file = new File("europe_capitals.txt");

		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {
			while (buffer.ready()) {
				stringBuilder.append(buffer.readLine()).append(" ");
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		regexResults = stringBuilder.toString().split(" ");

		for (int i = 1; i < regexResults.length; i += 5) {
			utcSummerCapitals.put(regexResults[i], regexResults[i + 1]);
		}

		return new CapitalsData(utcSummerCapitals);
	}
}