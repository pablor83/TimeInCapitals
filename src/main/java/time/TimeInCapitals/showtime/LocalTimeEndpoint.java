package time.TimeInCapitals.showtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocalTimeEndpoint {

	private LocalTimeService localTimeService;

	@Autowired
	public LocalTimeEndpoint(LocalTimeService localTimeService) {
		this.localTimeService = localTimeService;
	}

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
			throw new IllegalArgumentException("Bad request");
		}

	}

	@GetMapping("/europe/{capitalCity}")
	@ResponseBody
	public String getCapitalsTime(@PathVariable String capitalCity) throws Exception {

		if (localTimeService.existsByKey(capitalCity)) {
			return localTimeService.getTimeForEuropeCapital(capitalCity);
		} else {
			throw new IllegalArgumentException("Bad request. Incorrect capital name");
		}
	}
}