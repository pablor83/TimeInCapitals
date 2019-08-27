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
	
	@GetMapping({"/", "/localtime", "/localtime/{timeOrDate}" })
	@ResponseBody
	public String getQueryParameter(@PathVariable(required = false) String timeOrDate) throws Exception {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/y");

		LocalDateTime localDateTime = LocalDateTime.now();

		String localTime = localDateTime.format(timeFormat);
		String localDate = localDateTime.format(dateFormat);
		String localTimeAndDate = "Local Time: " + localTime + "\nLocal date: " + localDate;

		String result = localTimeAndDate;

		if(timeOrDate == null)
			return localTimeAndDate;
		else if (timeOrDate.equals("time"))
			return localTime;
		else if(timeOrDate.equals("date"))
			return localDate;
		else throw new Exception("Bad request");
	}

}
