import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import weather.WeatherAPI;

public class MyWeatherAPI extends WeatherAPI {

    public static ArrayList<HourlyWeather> getHourlyForecast(String region, int gridx, int gridy) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.gov/gridpoints/" + region + "/" + gridx + "," + gridy + "/forecast/hourly"))
                .build();

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        HourlyRoot root = getHourlyObject(response.body());
        if (root == null || root.properties == null || root.properties.periods == null) {
            System.err.println("Failed to parse hourly JSON");
            return null;
        }

        ArrayList<HourlyWeather> result = new ArrayList<>();

        for (HourlyPeriod p : root.properties.periods) {
            int precip = 0;
            if (p.probabilityOfPrecipitation != null && p.probabilityOfPrecipitation.value != null) {
                precip = p.probabilityOfPrecipitation.value;
            }

            String timeLabel = simplifyTime(p.startTime);

            result.add(new HourlyWeather(
                    timeLabel,
                    p.temperature,
                    precip,
                    p.windSpeed,
                    p.windDirection,
                    p.shortForecast
            ));
        }

        return result;
    }

    public static HourlyRoot getHourlyObject(String json) {
        ObjectMapper om = new ObjectMapper();
        HourlyRoot toRet = null;
        try {
            toRet = om.readValue(json, HourlyRoot.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return toRet;
    }

    // Turns a full ISO time into something shorter like "9 PM"
    private static String simplifyTime(String startTime) {
        try {
            String hourPart = startTime.substring(11, 13);
            int hour = Integer.parseInt(hourPart);

            String suffix = (hour >= 12) ? "PM" : "AM";
            int displayHour = hour % 12;
            if (displayHour == 0) {
                displayHour = 12;
            }

            return displayHour + " " + suffix;
        } catch (Exception e) {
            return startTime;
        }
    }
}