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
public class ShowTime {

	@GetMapping("/")
	@ResponseBody
	public String getTimeDate() {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:m:s");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d MM y");
		LocalDateTime localDateTime = LocalDateTime.now();

		String localTime = localDateTime.format(timeFormat);
		String localDate = localDateTime.format(dateFormat);

		return "Local Time: " + localTime + "\nLocal date: " + localDate;
	}

	@GetMapping({ "/gettime/{td}", "/gettime" })
	@ResponseBody
	public String getQueryParameter(@PathVariable(required = false) String td) {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/y");

		LocalDateTime localDateTime = LocalDateTime.now();

		String localTime = localDateTime.format(timeFormat);
		String localDate = localDateTime.format(dateFormat);
		String localTimeAndDate = "Local Time: " + localTime + "\nLocal date: " + localDate;

		String result = localTimeAndDate;

		if (td != null) {

			if (td.equals("time")) {

				result = localTime;

			} else if (td.equals("date")) {

				result = localDate;

			}
		}

		return result;
	}

}
