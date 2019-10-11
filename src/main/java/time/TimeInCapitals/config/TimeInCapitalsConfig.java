package time.TimeInCapitals.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import time.TimeInCapitals.data.CapitalsData;
import time.TimeInCapitals.data.EuropeCapitalsUTC;

@Configuration
@PropertySource("classpath:pathToTheEuropaFile.properties")
//@ConfigurationProperties(prefix = "source")
public class TimeInCapitalsConfig {

	private final int country = 0;
	private final int capital = 1;
	private final int summerTime = 2;
	private final int winterTime = 3;
	private final int isTimeChange = 4;

	@Bean(name = "getCapitalsData")
	public CapitalsData getDataFromFile(@Value("${source.pathToEurope}") String pathToEurope) {

		List<String> textFromFile = null;
		Map<String, EuropeCapitalsUTC> europeUTC = new HashMap<>();

		try {
			textFromFile = Files.readAllLines(Path.of(pathToEurope));
		} catch (IOException e) {
			e.printStackTrace();
		}

		europeUTC = textFromFile.stream().map(this::getUTCForEuropeCapitals)
				.collect(Collectors.toMap(EuropeCapitalsUTC::getCapital, europeCapitalsUTC -> europeCapitalsUTC));

		return new CapitalsData(europeUTC);
	}

	private EuropeCapitalsUTC getUTCForEuropeCapitals(String dataLines) {

		String[] dataFromFile = dataLines.split(" ");

		return new EuropeCapitalsUTC(dataFromFile[country], dataFromFile[capital], dataFromFile[summerTime],
				dataFromFile[winterTime], dataFromFile[isTimeChange]);
	}
}