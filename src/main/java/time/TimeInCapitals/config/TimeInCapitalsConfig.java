package time.TimeInCapitals.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class TimeInCapitalsConfig {

	private final int COUNTRY = 0;
	private final int CAPITAL = 1;
	private final int SUMMER_TIME = 2;
	private final int WINTER_TIME = 3;
	private final int IS_TIME_CHANGED = 4;

	@Bean(name = "getCapitalsData")
	public CapitalsData getDataFromFile(@Value("${source.pathToEurope}") String pathToEurope) {

		List<String> textFromFile = null;

		try {
			textFromFile = Files.readAllLines(Path.of(pathToEurope));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, EuropeCapitalsUTC> europeUTC = textFromFile
				.stream()
				.map(this::getUTCForEuropeCapitals)
				.collect(Collectors.toMap(EuropeCapitalsUTC::getCapital, europeCapitalsUTC -> europeCapitalsUTC));

		return new CapitalsData(europeUTC);
	}

	private EuropeCapitalsUTC getUTCForEuropeCapitals(String dataLines) {

		String[] dataFromFile = dataLines.split(" ");

		return new EuropeCapitalsUTC(dataFromFile[COUNTRY], dataFromFile[CAPITAL], dataFromFile[SUMMER_TIME],
				dataFromFile[WINTER_TIME], toTimeChanged(dataFromFile[IS_TIME_CHANGED]));
	}

	private boolean toTimeChanged(String isTimeChanged) {
		
		if(isTimeChanged.equals("yes"))
			return true;
		else if (isTimeChanged.equals("no"))
			return false;
		else
			throw new IllegalArgumentException("Wrong argument, only \"yes\" or \"no\"");
	}
}
