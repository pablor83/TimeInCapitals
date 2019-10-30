package time.TimeInCapitals.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import time.TimeInCapitals.data.CapitalsData;
import time.TimeInCapitals.data.CapitalsUTC;

@Configuration
@PropertySource("classpath:pathToContinentsFile.properties")
public class TimeInCapitalsConfig {

	private final int COUNTRY = 0;
	private final int CAPITAL = 1;
	private final int SUMMER_TIME = 2;
	private final int WINTER_TIME = 3;
	private final int CONTINENT = 4;

	@Bean(name = "getCapitalsData")
	public CapitalsData getDataFromFile(
			@Value("${source.pathToAfrica}") String pathToAfrica,
			@Value("${source.pathToAsia}") String pathToAsia,
			@Value("${source.pathToAustralia}") String pathToAustralia,
			@Value("${source.pathToEurope}") String pathToEurope,
			@Value("${source.pathToNorthAmerica}") String pathToNorthAmerica,
			@Value("${source.pathToSouthAmerica}") String pathToSouthAmerica) {

		List<String> textFromFile = new ArrayList<>();

		try {
			textFromFile.addAll(Files.readAllLines(Path.of(pathToAfrica)));
			textFromFile.addAll(Files.readAllLines(Path.of(pathToAsia)));
			textFromFile.addAll(Files.readAllLines(Path.of(pathToAustralia)));
			textFromFile.addAll(Files.readAllLines(Path.of(pathToEurope)));
			textFromFile.addAll(Files.readAllLines(Path.of(pathToNorthAmerica)));
			textFromFile.addAll(Files.readAllLines(Path.of(pathToSouthAmerica)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, CapitalsUTC> capitalsUTC = textFromFile
				.stream()
				.map(this::getUTCForCapitals)
				.collect(Collectors.toMap(CapitalsUTC::getCapital, europeCapitalsUTC -> europeCapitalsUTC));

		return new CapitalsData(capitalsUTC);
	}

	private CapitalsUTC getUTCForCapitals(String dataLines) {

		String[] dataFromFile = dataLines.split(" ");
		
		return new CapitalsUTC(dataFromFile[COUNTRY], dataFromFile[CAPITAL], dataFromFile[SUMMER_TIME],
				dataFromFile[WINTER_TIME], dataFromFile[CONTINENT]);
	}
}
