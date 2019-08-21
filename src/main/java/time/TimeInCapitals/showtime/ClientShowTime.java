package time.TimeInCapitals.showtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ClientShowTime {

	public static void main(String[] args) {
		
		try {
			URL url = new URL("http://localhost:8080/");
			URLConnection urlConnection = url.openConnection();
			
			String text;
			
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			while((text = bufReader.readLine()) != null)
				System.out.println(text);
			
			bufReader.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
