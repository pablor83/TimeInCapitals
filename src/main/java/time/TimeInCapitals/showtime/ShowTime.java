package time.TimeInCapitals.showtime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
		
		return "Local Time: "+localTime+"\n Local date: "+localDate;
	}

}
