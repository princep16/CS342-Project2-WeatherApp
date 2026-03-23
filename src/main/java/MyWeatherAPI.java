import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import weather.WeatherAPI;

// Handles fetching hourly weather data from the API and converting it
// into a simple format (HourlyWeather) that the UI can easily use.
public class MyWeatherAPI extends WeatherAPI {

    // Calls the weather.gov hourly endpoint and returns a list of hourly weather objects
    public static ArrayList<HourlyWeather> getHourlyForecast(String region, int gridx, int gridy) {

        // Build the API request URL using region + grid coordinates
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.weather.gov/gridpoints/" + region + "/" + gridx + "," + gridy + "/forecast/hourly"))
                .build();

        HttpResponse<String> response = null;

        // Send request and get response as a string
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null; // return null if request fails
        }

        // Convert raw JSON string into Java object structure
        HourlyRoot root = getHourlyObject(response.body());

        // Basic safety check to avoid crashes if parsing fails
        if (root == null || root.properties == null || root.properties.periods == null) {
            System.err.println("Failed to parse hourly JSON");
            return null;
        }

        // This will store the final processed hourly data for the UI
        ArrayList<HourlyWeather> result = new ArrayList<>();

        // Loop through each hourly period from the API
        for (HourlyPeriod p : root.properties.periods) {

            // Extract precipitation safely (API may return null sometimes)
            int precip = 0;
            if (p.probabilityOfPrecipitation != null && p.probabilityOfPrecipitation.value != null) {
                precip = p.probabilityOfPrecipitation.value;
            }

            // Convert raw timestamp into something user-friendly like "9 PM"
            String timeLabel = simplifyTime(p.startTime);

            // Create a simplified object with only the data we actually need
            result.add(new HourlyWeather(
                    timeLabel,
                    p.temperature,
                    precip,
                    p.windSpeed,
                    p.windDirection,
                    p.shortForecast
            ));
        }

        // Return processed list to be used in JavaFX UI
        return result;
    }

    // Uses Jackson to map raw JSON string into HourlyRoot object
    public static HourlyRoot getHourlyObject(String json) {
        ObjectMapper om = new ObjectMapper();
        HourlyRoot toRet = null;

        try {
            toRet = om.readValue(json, HourlyRoot.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // print error if JSON parsing fails
        }

        return toRet;
    }

    // Converts API timestamp (ISO format) into readable hour format like "9 PM"
    private static String simplifyTime(String startTime) {
        try {
            String hourPart = startTime.substring(11, 13); // extract hour from timestamp
            int hour = Integer.parseInt(hourPart);

            String suffix = (hour >= 12) ? "PM" : "AM";

            int displayHour = hour % 12;
            if (displayHour == 0) {
                displayHour = 12; // fix 0 → 12 (12 AM / 12 PM)
            }

            return displayHour + " " + suffix;

        } catch (Exception e) {
            // fallback: return original string if something goes wrong
            return startTime;
        }
    }
}