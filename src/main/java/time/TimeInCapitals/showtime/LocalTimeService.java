package time.TimeInCapitals.showtime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LocalTimeService {
	
	private LocalDateTime localDateTime = LocalDateTime.now();
	
	public String getTime() {
		
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		String localTime = localDateTime.format(timeFormat);
		
		return localTime;
		
	}
	
	public String getDate() {
		
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/y");
		String localDate = localDateTime.format(dateFormat);
		
		return localDate;
	}

	
	public String getTimeAndDate() {
		
		String localTimeAndDate = "Local Time: " + getTime() + "\nLocal date: " + getDate();

		return localTimeAndDate;
	}
	
}
