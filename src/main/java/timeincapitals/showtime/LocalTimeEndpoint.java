package timeincapitals.showtime;

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

	@GetMapping("/{capitalCity}")
	@ResponseBody
	public String getTimeForCapital(@PathVariable String capitalCity) {

		if (localTimeService.existsByKey(capitalCity))
			return localTimeService.getTimeForCapital(capitalCity);			
		else
			throw new IllegalArgumentException("Bad request. Incorrect capital name.");
	}
}
