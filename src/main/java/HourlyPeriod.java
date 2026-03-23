import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyPeriod {
    public String startTime;
    public int temperature;
    public String windSpeed;
    public String windDirection;
    public String shortForecast;
    public HourlyPrecipitation probabilityOfPrecipitation;
}