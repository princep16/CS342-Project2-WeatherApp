import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.util.ArrayList;

public class JavaFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chicago Weather App");

        // Load weather data from the API
        ArrayList<Period> forecast = WeatherAPI.getForecast("LOT", 77, 70);
        if (forecast == null) {
            throw new RuntimeException("Forecast did not load");
        }

        // Load hourly weather data
        ArrayList<HourlyWeather> hourlyData = MyWeatherAPI.getHourlyForecast("LOT", 77, 70);
        if (hourlyData == null) {
            throw new RuntimeException("Hourly forecast did not load");
        }

        // Get today's forecast for Scene 1
        Period today = forecast.get(0);

        // Optional debug print
        for (int i = 0; i < 5 && i < hourlyData.size(); i++) {
            System.out.println(hourlyData.get(i).timeLabel + " " +
                    hourlyData.get(i).temperature + "° " +
                    hourlyData.get(i).precipitation + "% " +
                    hourlyData.get(i).windSpeed);
        }

        // Buttons for switching between scenes
        Button forecastButton = new Button("View 3-Day Forecast");
        Button backButton = new Button("Back to Today");

        // Build Scene 1 using TodayWeatherScene
        TodayWeatherScene todaySceneBuilder = new TodayWeatherScene(today, hourlyData, forecastButton);
        Scene scene1 = todaySceneBuilder.buildScene(900, 700, "lightblue");

        // Build Scene 2 using ForecastScene
        ForecastScene forecastSceneBuilder = new ForecastScene(forecast, backButton);
        Scene scene2 = forecastSceneBuilder.buildScene(900, 700, "white");

        // Scene switching
        forecastButton.setOnAction(e -> {
            primaryStage.setScene(scene2);
            primaryStage.setMaximized(true);
        });

        backButton.setOnAction(e -> {
            primaryStage.setScene(scene1);
            primaryStage.setMaximized(true);
        });

        // Show Scene 1 first
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}