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

	@GetMapping("/africa/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeForAfrica(@PathVariable String capitalCity) throws Exception {

		if (localTimeService.existsByKey(capitalCity) && localTimeService.isCorrectContinent(capitalCity, "africa")) {
			return localTimeService.getTimeForAfricaCapital(capitalCity);
		} else {
			throw new IllegalArgumentException("Bad request. Incorrect capital name, or incorrect capital for this continent");
		}
	}
	
	@GetMapping("/asia/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeForAsia(@PathVariable String capitalCity) throws Exception {

		if (localTimeService.existsByKey(capitalCity) && localTimeService.isCorrectContinent(capitalCity, "asia")) {
			return localTimeService.getTimeForAsiaCapital(capitalCity);
		} else {
			throw new IllegalArgumentException("Bad request. Incorrect capital name, or incorrect capital for this continent");
		}
	}
	
	@GetMapping("/australia/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeForAustralia(@PathVariable String capitalCity) throws Exception {

		if (localTimeService.existsByKey(capitalCity) && localTimeService.isCorrectContinent(capitalCity, "australia")) {
			return localTimeService.getTimeForAustraliaCapital(capitalCity);
		} else {
			throw new IllegalArgumentException("Bad request. Incorrect capital name, or incorrect capital for this continent");
		}
	}
	
	@GetMapping("/europe/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeForEurope(@PathVariable String capitalCity) throws Exception {

		if (localTimeService.existsByKey(capitalCity) && localTimeService.isCorrectContinent(capitalCity, "europe")) {
			return localTimeService.getTimeForEuropeCapital(capitalCity);
		} else {
			throw new IllegalArgumentException("Bad request. Incorrect capital name, or incorrect capital for this continent");
		}
	}
	
	@GetMapping("/northamerica/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeForNorthAmerica(@PathVariable String capitalCity) throws Exception {

		if (localTimeService.existsByKey(capitalCity) && localTimeService.isCorrectContinent(capitalCity, "north_america")) {
			return localTimeService.getTimeForNorthAmericaCapital(capitalCity);
		} else {
			throw new IllegalArgumentException("Bad request. Incorrect capital name, or incorrect capital for this continent");
		}
	}
	
	@GetMapping("/southamerica/{capitalCity}")
	@ResponseBody
	public String getCapitalsTimeForSouthAmerica(@PathVariable String capitalCity) throws Exception {

		if (localTimeService.existsByKey(capitalCity) && localTimeService.isCorrectContinent(capitalCity, "south_america")) {
			return localTimeService.getTimeForSouthAmericaCapital(capitalCity);
		} else {
			throw new IllegalArgumentException("Bad request. Incorrect capital name, or incorrect capital for this continent");
		}
	}
}
