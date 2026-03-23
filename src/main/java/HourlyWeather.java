// Simplified data model for one hour of weather used by the UI.
// Combines relevant fields (time, temp, precipitation, wind, etc.) into one object.
public class HourlyWeather {
    public String timeLabel;
    public int temperature;
    public int precipitation;
    public String windSpeed;
    public String windDirection;
    public String shortForecast;

    // Constructor to initialize all hourly weather values at once
    public HourlyWeather(String timeLabel, int temperature, int precipitation,
                         String windSpeed, String windDirection, String shortForecast) {
        this.timeLabel = timeLabel;
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.shortForecast = shortForecast;
    }
}