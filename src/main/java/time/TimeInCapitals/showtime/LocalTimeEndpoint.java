package time.TimeInCapitals.showtime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

}
