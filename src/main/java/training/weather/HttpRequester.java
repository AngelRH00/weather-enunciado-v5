package training.weather;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import models.CityCoordinates;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class HttpRequester {
    private HttpRequestFactory requestFactory;
    private static final String coordsRequestString = "https://geocode.xyz/%s?json=1";
    private static final String weatherRequestString
            = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=weathercode&current_weather=true&timezone=Europe%%2FBerlin";

    public HttpRequester(){
        this.requestFactory = new NetHttpTransport().createRequestFactory();
    }

    public CityCoordinates getCityCoordinates(String city) throws IOException {
        HttpRequest request = this.requestFactory.buildGetRequest(
                new GenericUrl(String.format(coordsRequestString, city))
        );

        JSONObject jsonObject = new JSONObject(request.execute().parseAsString());
        if(jsonObject.has("error")) {
            return null;
        }
        return new CityCoordinates(
                city,
                jsonObject.get("longt").toString(),
                jsonObject.get("latt").toString()
        );
    }

    public JSONObject getCityWeather(String city) throws IOException {
        CityCoordinates coordinates = this.getCityCoordinates(city);
        if(coordinates == null){
            return null;
        }
        HttpRequest request = this.requestFactory.buildGetRequest(
                new GenericUrl(String.format(weatherRequestString, coordinates.getLatitude(), coordinates.getLongitude()))
        );
        return (JSONObject) new JSONObject(request.execute().parseAsString()).get("daily");
    }
}
