import weather.Period;
/*
Adapter Pattern:
This class acts like a bridge between the weather API and my UI.

The API gives me a Period object with raw data (temperature, wind, forecast, etc.),
but that format is not directly user-friendly.

So instead of using Period everywhere in my UI,
I created this adapter to convert that raw data into clean, readable text
that I can directly display in labels.
*/
public class WeatherAdapter {
    private Period period;

    /*
    Constructor:
    I pass in a Period object from the API and store it.
    This allows all methods in this class to access and format its data.
    */
    public WeatherAdapter(Period period) {
        this.period = period;
    }

    /*
    This method returns the temperature in a clean format
    that I can directly show in the UI.
    Example output: "Temperature: 72°F"
    */
    public String getTemperatureText() {
        return "Temperature: " + period.temperature + "°F";
    }

    /*
    This method returns the weather description in a readable format.
    Example output: "Forecast: Partly Cloudy"
    */
    public String getForecastText() {
        return "Forecast: " + period.shortForecast;
    }

    /*
    This method combines wind speed and direction into one string.
    Example output: "Wind: 10 mph NW"
    */
    public String getWindText() {
        return "Wind: " + period.windSpeed + " " + period.windDirection;
    }

    /*
    This returns the name of the time period,
    like "Today", "Tonight", or "Wednesday".
    */
    public String getNameText() {
        return period.name;
    }

    /*
    This returns the raw forecast text.
    I use this specifically for logic, like deciding which icon to show.
    */
    public String getShortForecast() {
        return period.shortForecast;
    }
}