package time.TimeInCapitals.showtime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

	@RequestMapping("/gettime")
	@ResponseBody
	public String getQueryParameter(@RequestParam(required = false) String td) {
		
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/y");

		LocalDateTime localDateTime = LocalDateTime.now();

		String localTime = localDateTime.format(timeFormat);
		String localDate = localDateTime.format(dateFormat);
		String locadTimeAndDate = "Local Time: " + localTime + "\nLocal date: " + localDate;
		
		HashMap<String, String> timeAndDate = new HashMap<>();
		
		timeAndDate.put("time", localTime);
		timeAndDate.put("date", localDate);
				
		return td == null ? locadTimeAndDate : timeAndDate.get(td) + " " + td;
	}

}
