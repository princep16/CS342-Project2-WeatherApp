import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

// Holds the list of hourly forecast entries returned by the API.
// Each item in 'periods' represents one hour of weather data.
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyProperties {
    public ArrayList<HourlyPeriod> periods;
}