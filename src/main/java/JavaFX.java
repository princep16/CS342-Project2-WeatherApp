import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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

        // Get today's forecast for Scene 1
        Period today = forecast.get(0);

        // Button used in Scene 1
        Button forecastButton = new Button("View 3-Day Forecast");

        // Build Scene 1 using your HW4 Template Method structure
        TodayWeatherScene sceneBuilder = new TodayWeatherScene(today, forecastButton);
        Scene scene1 = sceneBuilder.buildScene(700, 700, "lightblue");

        // ---------------- Scene 2 ----------------
        VBox root2 = new VBox();
        root2.setSpacing(15);
        root2.setAlignment(Pos.CENTER);
        root2.setPadding(new Insets(20));
        root2.setStyle("-fx-background-color: lightyellow;");

        Label forecastTitle = new Label("3-Day Forecast");
        forecastTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label calendarIcon = new Label("📅");
        calendarIcon.setStyle("-fx-font-size: 50px;");

        root2.getChildren().addAll(forecastTitle, calendarIcon);

        // Show next 6 periods (day/night for 3 days)
        for (int i = 1; i <= 6 && i < forecast.size(); i++) {
            Period p = forecast.get(i);

            String details = String.format(
                    "%s: %s°F | Wind: %s %s",
                    p.name,
                    p.temperature,
                    p.windSpeed,
                    p.windDirection
            );

            Label periodLabel = new Label(details);
            periodLabel.setStyle(
                    "-fx-font-size: 16px; " +
                            "-fx-background-color: lightgreen; " +
                            "-fx-padding: 5px; " +
                            "-fx-border-color: gray;"
            );

            root2.getChildren().add(periodLabel);
        }

        Button backButton = new Button("Back to Today");
        backButton.setStyle("-fx-font-size: 16px;");
        VBox.setMargin(backButton, new Insets(20, 0, 0, 0));
        root2.getChildren().add(backButton);

        Scene scene2 = new Scene(root2, 700, 700);

        // Scene switching
        forecastButton.setOnAction(e -> primaryStage.setScene(scene2));
        backButton.setOnAction(e -> primaryStage.setScene(scene1));

        /*forecastButton.setOnAction(e -> {
            primaryStage.setScene(scene2);
            primaryStage.setMaximized(true);
        });

        backButton.setOnAction(e -> {
            primaryStage.setScene(scene1);
            primaryStage.setMaximized(true);
        });*/
        // Show Scene 1 first
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}