import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

// Top-level object for the hourly forecast JSON response.
// Contains the 'properties' field, which holds the actual hourly data.
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyRoot {
    public HourlyProperties properties;
}