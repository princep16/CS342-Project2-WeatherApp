public class HourlyWeather {
    public String timeLabel;
    public int temperature;
    public int precipitation;
    public String windSpeed;
    public String windDirection;
    public String shortForecast;

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