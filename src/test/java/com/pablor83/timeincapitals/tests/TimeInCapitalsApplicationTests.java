package com.pablor83.timeincapitals.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import com.pablor83.timeincapitals.repository.CapitalsRepository;
import com.pablor83.timeincapitals.showtime.LocalTimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@Import(TimeInCapitalsApplicationTestsConfig.class)
@ExtendWith(SpringExtension.class)
public class TimeInCapitalsApplicationTests{
	
	@Mock
	private CapitalsRepository capitalsRepository;
		
	private Clock clock;
	
	@InjectMocks
	private LocalTimeService localTimeService;
	
	@DisplayName("Should return client time")
	public void shouldReturnClientTime() {
		
	}
	//shouldBe2AMInLondonServerInWarsawOnTheDayOfWinterTimeChange
	@DisplayName("1.Sprawdzić czy w Londynie będzie 2 w nocy czasu letniego gdy w Warszasie będzie 2 czasu zimowego w dniu zmianu czasu na zimowy")
	@Test
	public void powinnaByc2AMWLondynieSerwerWWarszawieDzienZmianyCzasuNaZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2019, 10, 27, 2, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("london")).thenReturn("+0");
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("london"));
	}
	//shouldBe2AMInWarsawTheServerInLondonOnTheDayOfWinterTimeChange
	@DisplayName("2.Serwer w Londynie 27.10.2019r. 01:00:00 +0 (czas zimowy), czy w Warszawie będzie już czas zimowy godzina 2 w dniu zmianu czasu na zimowy")
	@Test
	public void powinnaByc2AMWWarszawieSerwerWLondynieDzienZmianyCzasuNaZimowy() {		
		clock = Clock.fixed(LocalDateTime.of(2019, 10, 27, 2, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("warsaw"));
		
	}	
	//shouldBe2AMWinterZoneTheServerInWarsawTheDayOfWinterTimeChange
	@DisplayName("3.Serwer w Warszawie, sprawdzić czy w Warszawie wyświetli się godzina 2 w nocy UTC +1 w dniu zmianu czasu na zimowy")
	@Test
	public void powinnaByc2AMCzasuZimowegoSerwerWWarszawieDzienZmianyCzasuNaZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2019, 10, 27, 2, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe2AMWinterZoneWhileTheClocHas3AMDSTInWarsaw
	@DisplayName("4.Sprawdzić czy w Warszawie wyświetli się godzina 2 (czas zimowy), gdy na zegarze będzie godzina 3 czasu letniego w dniu zmianu czasu na zimowy")
	@Test
	public void powinnaByc2AMCzasZimowyPodczasGdyNaZegarzeJest3AMCzasuLetniegoSerwerWWarszawie() {
		clock = Clock.fixed(LocalDateTime.of(2019, 10, 27, 3, 0, 0).toInstant(ZoneOffset.of("+2")), ZoneId.of("+2"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe3AMInWarsawTheDayOfSummerTimeChangeTheServerInWarsaw
	@DisplayName("5.Sprawdzić czy Warszawie będzie 3 godzina 29.03.2020r. 02:00:00 +1 (czas zimowy) w dniu zmianu czasu na letni")
	@Test
	public void powinnaByc3AMWWarszawieCzasuLetniegoDzienZmianyCzasuNaLetniSerwerWWarszawie() {
		clock = Clock.fixed(LocalDateTime.of(2020, 3, 29, 2, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("03:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe1AMInLondonWhenInWarsawItWillBe3AMDSTTheServerInWarsaw
	@DisplayName("6.Sprawdzić czy Londyn będzie 2 godziny za Warszawą gdy Polsce będzie 29.03.2020r. 03:00:00 +2 (czas letni) w dniu zmianu czasu na letni")
	@Test
	public void powinnaByc1AMWLondynieGdyWWarszawieBedzie3AMDSTSerwerWWarszawie() {
		clock = Clock.fixed(LocalDateTime.of(2020, 3, 29, 3, 0, 0).toInstant(ZoneOffset.of("+2")), ZoneId.of("+2"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("london")).thenReturn("+0");
		assertEquals("01:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("london"));
	}
	//shouldBe13InWarsawTheServerInPlus8TimeZone
	@DisplayName("7.Sprawdzić czy uruchomienie serwera strefie czasowej +8 UTC będzie powodowało wyświetlanie poprawnego czasu dla Warszawy +1 UTC")
	@Test
	public void powinnaByc13PMWWarszawieSerwerWStrefieCzasowejPlus8UTC() {
		clock = Clock.fixed(LocalDateTime.of(2020, 1, 15, 20, 0, 0).toInstant(ZoneOffset.of("+8")), ZoneId.of("+8"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("13:00:00 15/01/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe14InWarsawTheServerInPlus8UTCZone
	@DisplayName("8.Sprawdzić czy uruchomienie serwera strefie czasowej +8 UTC będzie powodowało wyświetlanie poprawnego czasu dla Warszawy +2 UTC")
	@Test
	public void powinnaByc14PMWWarszawieSerwerPlus8UTC() {
		clock = Clock.fixed(LocalDateTime.of(2020, 7, 1, 20, 0, 0).toInstant(ZoneOffset.of("+8")), ZoneId.of("+8"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("14:00:00 01/07/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe14InOttawaTheServerInWarsaw
	@DisplayName("9.Sprawdzenie poprawności czasu dla ujemnego UTC względem Warszawy (czas zimowy)")
	@Test
	public void powinnaByc14PMWOttawieSerwerWWarszawie() {
		clock = Clock.fixed(LocalDateTime.of(2020, 1, 2, 20, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		assertEquals("14:00:00 02/01/2020", localTimeService.getTimeForEuropeCapital("ottawa"));
	}
	//shouldBe2AMInWarsawTheServerInOttawa
	@DisplayName("10.Sprawdzenie poprawności czasu dla ujemnego UTC serwera,  Warszawy +1 (czas zimowy)")
	@Test
	public void powinnaByc02AMWWarszawieSerwerWOttawie() {
		clock = Clock.fixed(LocalDateTime.of(2020, 1, 2, 20, 0, 0).toInstant(ZoneOffset.of("-5")), ZoneId.of("-5"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("02:00:00 03/01/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe3AMInOttawaTheDayToChangeToDSTInOttawaTheServerInWarsaw
	@DisplayName("11.Sprawdzenie zmiany czasu z zimowego na letni w Ottawie, gdy Serwer jest w Warszawie i ma czas zimowy. W dniu zmianu czasu na letni w Ottawie")
	@Test
	public void powinnaByc3AMWOttawieDzienZmianyCzasuNaLetniWOttawieSerwerWWarszawie() {
		clock = Clock.fixed(LocalDateTime.of(2020, 3, 8, 8, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("ottawa")).thenReturn("-5");
		assertEquals("03:00:00 08/03/2020", localTimeService.getTimeForEuropeCapital("ottawa"));
	}
	//shouldBe1130AMForKabulTheServerInWarsaw
	@DisplayName("12.Sprawdzić wyświetlanie czasu dla Kabulu (UTC +04:30) wg przykładowego czasu zimowego w Warszawie")
	@Test
	public void powinnaByc1130DlaKabuluSerwerWWarszawie() {
		clock = Clock.fixed(LocalDateTime.of(2020, 1, 2, 8, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("kabul")).thenReturn("+04:30");
		assertEquals("11:30:00 02/01/2020", localTimeService.getTimeForEuropeCapital("kabul"));
	}
	//shouldBe1030AMInKabulTheServerInWarsaw
	@DisplayName("13.Sprawdzić wyświetlanie czasu dla Kabulu (UTC +04:30) wg przykładowego czasu letniego w Warszawie")
	@Test
	public void powinnaByc1030WKabuluSerwerWWarszawie() {
		clock = Clock.fixed(LocalDateTime.of(2020, 7, 1, 8, 0, 0).toInstant(ZoneOffset.of("+2")), ZoneId.of("+2"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("kabul")).thenReturn("+04:30");
		assertEquals("10:30:00 01/07/2020", localTimeService.getTimeForEuropeCapital("kabul"));
	}
	//shouldBe0630AMInWarsawTheServerInKabul
	@DisplayName("14.Sprawdzić wyświetlanie czasu dla Warszawy (czas zimowy), podczas gdy serwer znajduje się w strefie czasowej Kabulu")
	@Test
	public void powinnaByc0630WWarszawieSerwerWKabulu() {
		clock = Clock.fixed(LocalDateTime.of(2020, 1, 2, 10, 0, 0).toInstant(ZoneOffset.of("+04:30")), ZoneId.of("+04:30"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("06:30:00 02/01/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe0730AMInWarsawWithDSTTheServerInKabul
	@DisplayName("15.Sprawdzić wyświetlanie czasu dla Warszawy (czas letni), podczas gdy serwer znajduje się w strefie czasowej Kabulu")
	@Test
	public void powinnaByc0730WWarszawieCzasLetniSerwerWKabulu() {
		clock = Clock.fixed(LocalDateTime.of(2020, 7, 1, 10, 0, 0).toInstant(ZoneOffset.of("+04:30")), ZoneId.of("+04:30"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("07:30:00 01/07/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe2AMInMinskTheServerInWarsawTheDayToChangeToWinterZone
	@DisplayName("16.Serwer w Warszawie, sprawdzić czy w dniu zmiany czasu na zimowy dla Warszawy godz. 01:00 w nocy (czas letni UTC +2), czas dla Mińska będzie wyświetlany poprawnie (Mińsk brak czasu letniego)")
	@Test
	public void powinnaByc02AMWMinskuSerwerWWarszawieDzienZmianyCzasuNaZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2019, 10, 27, 1, 0, 0).toInstant(ZoneOffset.of("+2")), ZoneId.of("+2"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("minsk")).thenReturn("+3");
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("minsk"));
	}
	//shouldBe4AMInMinskTheServerInWarsawTheDayToChangeToWinterZone
	@DisplayName("17.Serwer w Warszawie, sprawdzić czy w dniu zmiany czasu na zimowy dla Warszawy godz. 02:00 w nocy (czas zimowy UTC +1), czas dla Mińska będzie wyświetlany poprawnie (Mińsk brak czasu letniego)")
	@Test
	public void powinnaByc04AMWMinskuSerwerWWarszawieZmianaCzasuNaZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2019, 10, 27, 2, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("minsk")).thenReturn("+3");
		assertEquals("04:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("minsk"));
	}
	//shouldBe4AMInMinskTheServerInWarsawTheDayToChangeToDST
	@DisplayName("18.Serwer w Warszawie, sprawdzić czy w dniu zmiany czasu na letni dla Warszawy godz. 03:00 w nocy (czas letni UTC +2), czas dla Mińska będzie wyświetlany poprawnie (Mińsk brak czasu letniego)")
	@Test
	public void powinnaByc04AMWMinskuSerwerWWarszawieDzienZmianyCzasuNaLetni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 3, 29, 3, 0, 0).toInstant(ZoneOffset.of("+2")), ZoneId.of("+2"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("minsk")).thenReturn("+3");
		assertEquals("04:00:00 29/03/2020", localTimeService.getTimeForEuropeCapital("minsk"));
	}
	//shouldBe2AMInMinskTheServerInWarsawTheDayToChangeToDST
	@DisplayName("19.Serwer w Warszawie, sprawdzić czy w dniu zmiany czasu na letni dla Warszawy godz. 00:00 w nocy (czas zimowy UTC +1), czas dla Mińska będzie wyświetlany poprawnie (Mińsk brak czasu letniego)")
	@Test
	public void powinnaByc02AMWMinskuSerwerWWarszawieDzienZmianuCzasuNaLetni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 3, 29, 0, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("minsk")).thenReturn("+3");
		assertEquals("02:00:00 29/03/2020", localTimeService.getTimeForEuropeCapital("minsk"));
	}
	//shouldBe2AMInMexicoTheServerInWarsawWinterZone
	@DisplayName("20.Sprawdzić czy poprawnie jest zmieniany czas na zimowy dla Meksyku, serwer w Warszasie UTC +1")
	@Test
	public void powinnaByc02AMWMeksykuSerwerWWarszawieCzasZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2019, 10, 27, 9, 0, 0).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		assertEquals("02:00:00 27/10/2019", localTimeService.getTimeForEuropeCapital("mexico"));
	}
	//shouldBe3AMForMexicoTheServerInWarsawWithDST
	@DisplayName("21.Sprawdzić czy poprawnie jest zmieniany czas na letni dla Meksyku, serwer w Warszasie UTC +2")
	@Test
	public void powinnaByc03AMDlaMeksykuSerwerWWarszawieCzasLetni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 4, 5, 8, 0, 0).toInstant(ZoneOffset.of("+2")), ZoneId.of("+2"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		assertEquals("03:00:00 05/04/2020", localTimeService.getTimeForEuropeCapital("mexico"));
	}
	//shouldBe4AMInMexicoTheServerInWarsawWithDST
	@DisplayName("22.Sprawdzić czy poprawnie jest wyświetlany czas dla Meksyku gdy Warszawa już przeszła na czas letni, a Meksyk jest przed zmianą")
	@Test
	public void powinnaByc04AMWMeksykuSerwerWWarszawieCzasLetni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 4, 1, 10, 0, 0).toInstant(ZoneOffset.of("+2")), ZoneId.of("+2"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("mexico")).thenReturn("-6");
		assertEquals("04:00:00 01/04/2020", localTimeService.getTimeForEuropeCapital("mexico"));
	}
	//shouldBe2AMInCanberraTheServerInCanberraTheDayToChangeToWinterZone
	@DisplayName("23.Sprawdzić czy poprawnie jest wyświetlany czas zimowy dla Autralii, serwer w Australii. Dzień zmiany czasu na zimowy")
	@Test
	public void powinnaByc02AMWCanberraSerwerWCanberraZmianaNaCzasZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2019, 10, 06, 2, 0, 0).toInstant(ZoneOffset.of("+10")), ZoneId.of("+10"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("canberra")).thenReturn("+10");
		assertEquals("02:00:00 06/10/2019", localTimeService.getTimeForEuropeCapital("canberra"));
	}
	//shouldBe3AMInCanberraTheServerInCanberraTheDayToChangeToDST
	@DisplayName("24.Sprawdzić czy poprawnie jest wyświetlany czas letni dla Autralii, serwer w Australii. Dzień zmiany czasu na letni")
	@Test
	public void powinnaByc03AMWCanberraSerwerWCanberraZmianaNaCzasLetni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 4, 5, 2, 0, 0).toInstant(ZoneOffset.of("+10")), ZoneId.of("+10"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("canberra")).thenReturn("+10");
		assertEquals("03:00:00 05/04/2020", localTimeService.getTimeForEuropeCapital("canberra"));
	}
	//shouldBe2AMInSuvaTheServerInSuvaTheDayToChangeToWinterZone
	@DisplayName("25.Sprawdzić czy poprawnie jest wyświetlany czas zimowy dla Fidżi, serwer w Fidżi. Dzień zmiany czasu na zimowy")
	@Test
	public void powinnaByc2AMSuvaSerwerSuvaZmianaCzasuNaZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2020, 1, 12, 2, 0, 0).toInstant(ZoneOffset.of("+12")), ZoneId.of("+12"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("suva")).thenReturn("+12");
		assertEquals("02:00:00 12/01/2020", localTimeService.getTimeForEuropeCapital("suva"));
	}
	//shouldBe3AMInSuvaTheServerInSuvaTheDayToChangeToDST
	@DisplayName("26.Sprawdzić czy poprawnie jest wyświetlany czas letni dla Fidżi, serwer w Fidżi. Dzień zmiany czasu na letni")
	@Test
	public void powinnaByc3AMSuvaSerwerSuvaZmianaCzasuNaletni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 11, 1, 2, 0, 0).toInstant(ZoneOffset.of("+12")), ZoneId.of("+12"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("suva")).thenReturn("+12");
		assertEquals("03:00:00 01/11/2020", localTimeService.getTimeForEuropeCapital("suva"));
	}
	//shouldBe2AMInWellingtonTheServerInWellingtonTheDayToChangeToWinterZone
	@DisplayName("27.Sprawdzić czy poprawnie jest wyświetlany czas zimowy dla Nowej Zelandii, serwer w Nowej Zelandi. Dzień zmiany czasu na zimowy")
	@Test
	public void powinnaByc2AMWellingotSerwerWellingtonZmianaCzasuNaZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2020, 4, 5, 2, 0, 0).toInstant(ZoneOffset.of("+12")), ZoneId.of("+12"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("wellington")).thenReturn("+12");
		assertEquals("02:00:00 05/04/2020", localTimeService.getTimeForEuropeCapital("wellington"));
	}
	//shouldBe3AMInWellingtonTheServerInWellingtonTheDayToChangeToDST
	@DisplayName("28.Sprawdzić czy poprawnie jest wyświetlany czas letni dla Nowej Zelandii, serwer w Nowej Zelandii")
	@Test
	public void powinnaByc3AMWellingotSerwerWellingtonZmianaCzasuNaLetni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 9, 27, 2, 0, 0).toInstant(ZoneOffset.of("+12")), ZoneId.of("+12"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("wellington")).thenReturn("+12");
		assertEquals("03:00:00 27/09/2020", localTimeService.getTimeForEuropeCapital("wellington"));
	}
	//shouldBe3AMInApiaTheServerInApiaTheDayToChangeToWinterZone
	@DisplayName("29.Sprawdzić czy poprawnie jest wyświetlany czas zimowy dla Samoa, serwer w Samoa")
	@Test
	public void powinnaByc3AMApiaSerwerApiaZmianaCzasuNaZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2020, 4, 5, 3, 0, 0).toInstant(ZoneOffset.of("+13")), ZoneId.of("+13"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("apia")).thenReturn("+13");
		assertEquals("03:00:00 05/04/2020", localTimeService.getTimeForEuropeCapital("apia"));
	}
	//shouldBe4AMInApiaTheServerInApiaTheDayToChangeToDST
	@DisplayName("30.Sprawdzić czy poprawnie jest wyświetlany czas letni dla Samoa, serwer w Samoa")
	@Test
	public void powinnaByc4AMApiaSerwerApiaZmianaCzasuNaLetni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 9, 27, 3, 0, 0).toInstant(ZoneOffset.of("+13")), ZoneId.of("+13"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("apia")).thenReturn("+13");
		assertEquals("04:00:00 27/09/2020", localTimeService.getTimeForEuropeCapital("apia"));
	}
	//shouldBe2AMInKingstoneTheServerInKingstoneTheDayToChangeToWinterZone
	@DisplayName("31.Sprawdzić czy poprawnie jest wyświetlany czas zimowy dla Norfolk, serwer w Norfolk")
	@Test
	public void powinnaByc2AMKingstoneSerwerKingstoneZmianaCzasuNaZimowy() {
		clock = Clock.fixed(LocalDateTime.of(2020, 4, 5, 2, 0, 0).toInstant(ZoneOffset.of("+11")), ZoneId.of("+11"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("kingstone")).thenReturn("+11");
		assertEquals("02:00:00 05/04/2020", localTimeService.getTimeForEuropeCapital("kingstone"));
	}
	//shouldBe2AMInKingstoneTheServerInKingstoneTheDayToChangeToDST
	@DisplayName("32.Sprawdzić czy poprawnie jest wyświetlany czas letni dla Norfolk, serwer w Norfolk")
	@Test
	public void powinnaByc2AMKingstoneSerwerKingstoneZmianaCzasuNaLetni() {
		clock = Clock.fixed(LocalDateTime.of(2020, 10, 4, 2, 0, 0).toInstant(ZoneOffset.of("+11")), ZoneId.of("+11"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("kingstone")).thenReturn("+11");
		assertEquals("03:00:00 04/10/2020", localTimeService.getTimeForEuropeCapital("kingstone"));
	}
	//shouldBe120201CheckForHoursMinutesSeconds
	@DisplayName("33.Sprawdzić czy poprawnie jest wyświetlany czas dla minut i sekund innych niż zero. Serwer w Warszawie, czas zimowy")
	@Test
	public void powinnaByc120201SprawdzenieDlaGodzinMinutSekund() {
		clock = Clock.fixed(LocalDateTime.of(2020, 1, 2, 12, 2, 1).toInstant(ZoneOffset.of("+1")), ZoneId.of("+1"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("12:02:01 02/01/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
	//shouldBe215915CheckForHoursMinutesSeconds
	@DisplayName("34.Sprawdzić czy poprawnie jest wyświetlany czas dla minut i sekund innych niż zero. Serwer w Warszawie, czas letni")
	@Test
	public void powinnaByc215915SprawdzenieDlaGodzinMinutSekund() {
		clock = Clock.fixed(LocalDateTime.of(2020, 5, 12, 21, 59, 15).toInstant(ZoneOffset.of("+2")), ZoneId.of("+2"));
		localTimeService = new LocalTimeService(capitalsRepository, clock);
		when(capitalsRepository.getWinterUTC("warsaw")).thenReturn("+1");
		assertEquals("21:59:15 12/05/2020", localTimeService.getTimeForEuropeCapital("warsaw"));
	}
}
