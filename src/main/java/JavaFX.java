import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.util.ArrayList;

/*
Main JavaFX entry point for the weather app.

This class is responsible for starting the application,
loading weather data from the API, creating the main scenes,
and setting up navigation between them.

In simple terms:
- it gets the weather data
- builds the UI screens
- connects the buttons so the user can move between screens
*/
public class JavaFX extends Application {

    /*
    JavaFX applications start here.
    launch(args) tells JavaFX to create the app window
    and then call the start(...) method below.
    */
    public static void main(String[] args) {
        launch(args);
    }

    /*
    start(...) is the main setup method for a JavaFX app.

    JavaFX automatically gives us the primaryStage,
    which is the main window of the application.

    In this method, we:
    1. Set the window title
    2. Load forecast and hourly weather data
    3. Create buttons for scene navigation
    4. Build the two scenes
    5. Define what happens when buttons are clicked
    6. Show the first scene to the user
    */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chicago Weather App");

        // Load the regular forecast data from the provided WeatherAPI.
        // This gives us the main forecast periods used for today and the 3-day forecast screen.
        ArrayList<Period> forecast = WeatherAPI.getForecast("LOT", 77, 70);
        // If the forecast did not load, stop the program immediately.
        // There is no point continuing if we have no weather data to show.
        if (forecast == null) {
            throw new RuntimeException("Forecast did not load");
        }

        // Load the hourly weather data using our custom API helper.
        // This is separate from the normal forecast because the hourly data
        // is used for the temperature / precipitation / wind tab section.
        ArrayList<HourlyWeather> hourlyData = MyWeatherAPI.getHourlyForecast("LOT", 77, 70);
        // Again, stop if hourly data could not be loaded.
        // The main scene depends on this data for the hourly tab feature.
        if (hourlyData == null) {
            throw new RuntimeException("Hourly forecast did not load");
        }

        // The first forecast period represents today's weather.
        // We pass this into TodayWeatherScene so it can build the main screen.
        Period today = forecast.get(0);

        // Create the buttons that will be used to move between scenes.
        // These buttons are passed into the scene classes so each scene can display them.
        Button forecastButton = new Button("View 3-Day Forecast");
        Button backButton = new Button("Back to Today");

        // Build Scene 1: the main "today" screen.
        // This scene shows current weather, hourly tabs, clothing icon, temperature toggle, etc.
        //
        // We pass:
        // - today's forecast data
        // - hourly weather data
        // - the button that moves to the forecast screen
        TodayWeatherScene todaySceneBuilder = new TodayWeatherScene(today, hourlyData, forecastButton);
        Scene scene1 = todaySceneBuilder.buildScene(900, 700, "lightblue");

        // Build Scene 2: the 3-day forecast screen.
        // This scene shows multiple day/night forecast cards and includes a back button.
        ForecastScene forecastSceneBuilder = new ForecastScene(forecast, backButton);
        Scene scene2 = forecastSceneBuilder.buildScene(900, 700, "white");

        // Set up scene switching:
        // When user clicks the forecast button on Scene 1,
        // switch to Scene 2 and minimize the window.
        forecastButton.setOnAction(e -> {
            primaryStage.setScene(scene2);
            primaryStage.setMaximized(false);
        });

        // When user clicks the back button on Scene 2,
        // switch back to Scene 1 and keep the window minimized.
        backButton.setOnAction(e -> {
            primaryStage.setScene(scene1);
            primaryStage.setMaximized(false);
        });

        // Show the TodayWeatherScene first when the app starts.
        primaryStage.setScene(scene1);

        // Make the application window visible to the user.
        primaryStage.show();
    }
}