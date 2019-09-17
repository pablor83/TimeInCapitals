package time.TimeInCapitals.showtime;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocalTimeService {

	public String getTime() {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		String localTime = localDateTime.format(timeFormat);

		return localTime;
	}

	public String getTimeInZone(String regionAndCity) {

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		Instant instant = Instant.now();
		ZonedDateTime zoneDateTime = instant.atZone(ZoneId.of(regionAndCity));
		return dateTimeFormatter.format(zoneDateTime);
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

	public String changeTheFirstLetterToUppercase(String word) {

		String[] regexResults = word.split("_");

		StringBuilder stringBuilder = new StringBuilder();

		if (regexResults.length == 1) {

			String wordAfterChange = (stringBuilder.append(word.substring(0, 1).toUpperCase())
					.append(word.substring(1))).toString();
			return wordAfterChange;
		} else {

			String wordAfterChange = (stringBuilder.append(regexResults[0].substring(0, 1).toUpperCase())
					.append(regexResults[0].substring(1))).append("_")
							.append(regexResults[1].substring(0, 1).toUpperCase()).append(regexResults[1].substring(1))
							.toString();

			return wordAfterChange;
		}

	}

}
