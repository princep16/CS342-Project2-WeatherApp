import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Represents precipitation probability for a given hour (e.g., 10% chance of rain).
// This maps to the nested JSON field: "probabilityOfPrecipitation": { "value": ... }
@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyPrecipitation {
    public Integer value; // percentage chance of precipitation
}