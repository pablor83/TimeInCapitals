package time.TimeInCapitals.showtime;

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
	public String getCapitalsTimeAmerica(@PathVariable String capitalCity) throws Exception {

		if (localTimeService.checkKey(capitalCity)) {
			return localTimeService.getTimeInZone(capitalCity);
		} else {
			throw new Exception("Bad request. Incorrect capital name");			
		}

	}
}
