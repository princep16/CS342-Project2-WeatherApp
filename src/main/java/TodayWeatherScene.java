import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import weather.Period;

/*
Template Method Pattern (Subclass):

This class represents Scene 1 (Today's Weather).

It extends WeatherSceneTemplate, which already defines
the general steps for building a scene (layout, spacing, styling).

Here, I only focus on what makes THIS scene unique:
- title
- icon
- weather details
- button

So instead of rebuilding the layout every time,
I just override addContent() to plug in Scene 1’s components.
*/
public class TodayWeatherScene extends WeatherSceneTemplate {

    private Period today;
    private Button forecastButton;

    /*
    Constructor:
    I pass in today's weather data and the button so this class can build the UI using that information.
    */
    public TodayWeatherScene(Period today, Button forecastButton) {
        this.today = today;
        this.forecastButton = forecastButton;
    }
    /*
    This method is required by the Template Method pattern.

    The base class already created and styled the VBox.
    Now I add Scene 1 specific content into it.
    */
    @Override
    protected void addContent(VBox root) {
        // Use Adapter to convert raw API data into UI-friendly text
        WeatherAdapter todayWeather = new WeatherAdapter(today);

        // Title at the top of the screen
        Label titleLabel = new Label("Today's Weather");

        /*
        Get forecast text and convert to lowercase
        so I can easily check for keywords like "sun", "rain", etc.
        */
        String forecastText = todayWeather.getShortForecast().toLowerCase();

        /*
        Choose an icon based on the forecast.
        This makes the UI more visual and user-friendly.
        */
        Label icon;
        if (forecastText.contains("sun") || forecastText.contains("clear")) {
            icon = new Label("☼");
        } else if (forecastText.contains("cloud")) {
            icon = new Label("☁");
        } else if (forecastText.contains("rain") || forecastText.contains("shower")) {
            icon = new Label("☂");
        } else if (forecastText.contains("snow")) {
            icon = new Label("❄");
        } else if (forecastText.contains("fog") || forecastText.contains("mist")) {
            icon = new Label("≈");
        } else if (forecastText.contains("storm") || forecastText.contains("thunder")) {
            icon = new Label("⚡");
        } else {
            icon = new Label("☼"); // default icon
        }
        // Use adapter methods to get clean, formatted text
        Label tempLabel = new Label(todayWeather.getTemperatureText());
        Label forecastLabel = new Label(todayWeather.getForecastText());

        /*
        Styling:
        I make the title bold and larger, increase icon size, and keep other text readable.
        */
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        icon.setStyle("-fx-font-size: 40px;");
        tempLabel.setStyle("-fx-font-size: 18px;");
        forecastLabel.setStyle("-fx-font-size: 18px;");
        forecastButton.setStyle("-fx-font-size: 16px;");

        root.getChildren().addAll(titleLabel, icon, tempLabel, forecastLabel, forecastButton);
    }
}