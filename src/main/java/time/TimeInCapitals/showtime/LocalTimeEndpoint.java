package time.TimeInCapitals.showtime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocalTimeEndpoint {

	private LocalTimeService localTimeService = new LocalTimeService();

	@GetMapping({ "/localtime", "/localtime/{timeOrDate}" })
	@ResponseBody
	public String get(@PathVariable(required = false) String timeOrDate) throws Exception {

		if (timeOrDate == null) {
			return localTimeService.getTimeAndDate();

		} else if (timeOrDate.equals("time")) {
			return localTimeService.getTime();

		} else if (timeOrDate.equals("date")) {
			return localTimeService.getDate();

		} else {
			throw new Exception("Bad request");
		}

	}
	
	@GetMapping("/europe/{capitalCity}")
	@ResponseBody
	public String getCapitalsTime(@PathVariable String capitalCity) {
		
		String capitalsOfEurope = "Europe/"+changeTheFirstLetterToUppercase(capitalCity);
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		Instant instant = Instant.now();
		ZonedDateTime zoneDataTime = instant.atZone(ZoneId.of(capitalsOfEurope));
		return zoneDataTime.format(dateTimeFormatter);
		
	}
	
	public String changeTheFirstLetterToUppercase(String word) {
		
		StringBuilder stringBuilder = new StringBuilder();		
		String wordAfterChange = (stringBuilder.append(word.substring(0, 1).toUpperCase()).append(word.substring(1))).toString();		
		return wordAfterChange;
	}

}
