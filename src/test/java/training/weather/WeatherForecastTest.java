package training.weather;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class WeatherForecastTest {

	@Test
	public void existingCity() throws IOException {
		WeatherForecast weatherForecast = new WeatherForecast();
		String forecast = weatherForecast.getCityWeather("Madrid", new Date());
		System.out.println(forecast.equals(""));
	}

//	descomentar para testear para evitar
//	el error de límite de peticiones por segundo

//	@Test
//	public void nonExistingCity() throws IOException {
//		WeatherForecast weatherForecast = new WeatherForecast();
//		String forecast = weatherForecast.getCityWeather("nullCity", new Date());
//		System.out.println(forecast.equals(""));
//	}

	@Test
	public void zipMethodTest() {
		WeatherForecast weatherForecast = new WeatherForecast();
		JSONObject list1 = new JSONObject("{\"keys\": [\"key1\", \"key2\", \"key3\"]}");
		JSONObject list2 = new JSONObject("{\"values\": [\"value1\", \"value2\", \"value3\"]}");

		System.out.println(weatherForecast.zip(
				(JSONArray) list1.get("keys"),
				(JSONArray) list2.get("values"))
		);
	}

	@Test
	public void isBeforeTomorrow() {
		WeatherForecast weatherForecast = new WeatherForecast();
		System.out.println(weatherForecast.isBeforeTomorrow(new Date()));
	}

	@Test
	public void isNotBeforeTomorrow() {
		WeatherForecast weatherForecast = new WeatherForecast();
		System.out.println(weatherForecast.isBeforeTomorrow(
				Date.from(ZonedDateTime.now().plusDays(10).toInstant())
		));
	}
}