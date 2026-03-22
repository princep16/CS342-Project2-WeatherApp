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

        ArrayList<Period> forecast = WeatherAPI.getForecast("LOT", 77, 70);
        if (forecast == null) {
            throw new RuntimeException("Forecast did not load");
        }

        Period today = forecast.get(0);

        Button forecastButton = new Button("View 3-Day Forecast");

        TodayWeatherScene sceneBuilder = new TodayWeatherScene(today, forecastButton);
        Scene scene1 = sceneBuilder.buildScene(700, 700, "lightblue");

        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}