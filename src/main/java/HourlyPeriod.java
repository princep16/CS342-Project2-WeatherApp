import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Represents one hour of weather data from the API.
// Jackson automatically maps JSON fields (time, temp, wind, etc.) into this object.
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyPeriod {
    public String startTime; // raw timestamp from API
    public int temperature; // temperature for that hour
    public String windSpeed;
    public String windDirection;
    public String shortForecast; // shorter weather description
    public HourlyPrecipitation probabilityOfPrecipitation; // nested precipitation data
}