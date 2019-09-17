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

	@GetMapping("/america/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeAmerica(@PathVariable String capitalCity) throws Exception {

		String capitalsOfEurope = "America/" + localTimeService.changeTheFirstLetterToUppercase(capitalCity);

		try {
			return localTimeService.getTimeInZone(capitalsOfEurope);
		} catch (Exception e) {
			throw new Exception("Bad request");
		}

	}

	@GetMapping("/asia/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeAsia(@PathVariable String capitalCity) throws Exception {

		String capitalsOfEurope = "Asia/" + localTimeService.changeTheFirstLetterToUppercase(capitalCity);

		try {
			return localTimeService.getTimeInZone(capitalsOfEurope);
		} catch (Exception e) {
			throw new Exception("Bad request");
		}

	}

	@GetMapping("/europe/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeEurope(@PathVariable String capitalCity) {

		String capitalsOfEurope = "Europe/" + localTimeService.changeTheFirstLetterToUppercase(capitalCity);

		return localTimeService.getTimeInZone(capitalsOfEurope);

	}
}
