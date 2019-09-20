package time.TimeInCapitals.showtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class LocalTimeService {

	public LocalTimeService() {
		getDataFromFile();
		setSummerUTCForCapitals();
	}

	private String[] regexResults;
	private HashMap<String, String> utcSummerCapitals = new HashMap<>();

	public String getTime() {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		String localTime = localDateTime.format(timeFormat);

		return localTime;
	}

	public String getTimeInZone(String capital) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		Instant instant = Instant.now();
		ZoneOffset zoneOffset = ZoneOffset.of(utcSummerCapitals.get(capital));

		return instant.atZone(ZoneId.ofOffset("UTC", zoneOffset)).format(dateTimeFormatter);
	}

	public String getDate() {

		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/y");
		LocalDateTime localDateTime = LocalDateTime.now();
		String localDate = localDateTime.format(dateFormat);

		return localDate;
	}

	public String getTimeAndDate() {

		String localTimeAndDate = "Local Time: " + getTime() + "\nLocal date: " + getDate();

		return localTimeAndDate;
	}

	public void getDataFromFile() {

		File file = new File("D:/java/europe_capitals.txt");
		StringBuilder stringBuilder = new StringBuilder();
		String dataFromFile = null;

		try (BufferedReader buffer = new BufferedReader(new FileReader(file))) {

			while (buffer.ready()) {

				stringBuilder.append(buffer.readLine()).append(" ");
			}

		} catch (IOException e1) {

			e1.printStackTrace();
		}

		dataFromFile = stringBuilder.toString();
		regexResults = dataFromFile.split(" ");

	}

	public boolean checkKey(String key) {

		return utcSummerCapitals.containsKey(key);
	}

	public void setSummerUTCForCapitals() {

		for (int i = 1; i < regexResults.length; i += 5) {

			utcSummerCapitals.put(regexResults[i], regexResults[i + 1]);
		}
	}

}
