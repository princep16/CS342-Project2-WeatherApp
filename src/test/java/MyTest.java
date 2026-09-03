import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import weather.Period;

class WeatherAdapterTest {

    private WeatherAdapter adapter;

    @BeforeEach
    void setUp() {
        Period period = new Period();
        period.name = "Today";
        period.temperature = 72;
        period.shortForecast = "Partly Cloudy";
        period.windSpeed = "10 mph";
        period.windDirection = "NW";

        adapter = new WeatherAdapter(period);
    }

    @Test
    void formatsTemperature() {
        assertEquals("Temperature: 72°F", adapter.getTemperatureText());
    }

    @Test
    void formatsForecast() {
        assertEquals("Forecast: Partly Cloudy", adapter.getForecastText());
    }

    @Test
    void formatsWindInformation() {
        assertEquals("Wind: 10 mph NW", adapter.getWindText());
    }

    @Test
    void returnsPeriodName() {
        assertEquals("Today", adapter.getNameText());
    }
}
