package training.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;

public class WeatherForecast {
    public String getCityWeather(String city, Date dateTime) throws IOException {
        dateTime = dateTime == null ? new Date() : dateTime;
        if (this.isBeforeTomorrow(dateTime)) {

            HttpRequester requester = new HttpRequester();
            JSONObject dailyResults = requester.getCityWeather(city);
            if(dailyResults == null) return "";

            HashMap<String, String> dailyResultsMap = this.zip(
                    (JSONArray) dailyResults.get("time"),
                    (JSONArray) dailyResults.get("weathercode")
            );
            String relevantWeatherCode = dailyResultsMap.get(new SimpleDateFormat("yyyy-MM-dd").format(dateTime));
            if(relevantWeatherCode == null) return "";

            return ForecastEnum.getEnumByCode(((int) Double.parseDouble(relevantWeatherCode))).getDescription();
        }
        return "";
    }

    public boolean isBeforeTomorrow(Date dateTime) {
        Instant tomorrow = ZonedDateTime.now().plusDays(1).toInstant();
        return dateTime.toInstant().isBefore(tomorrow);
    }

    public HashMap<String, String> zip(JSONArray timeStrings, JSONArray weatherCodes){
        HashMap<String, String> resultMap = new HashMap<>();
        if(timeStrings.length() != weatherCodes.length()) return resultMap;

        for(int i = 0; i < timeStrings.length() - 1 ; i++){
            resultMap.put(
                    timeStrings.get(i).toString(),
                    weatherCodes.get(i).toString()
            );
        }
        return resultMap;
    }
}
