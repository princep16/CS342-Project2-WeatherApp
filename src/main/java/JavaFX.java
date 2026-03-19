import javafx.application.Application;
import javafx.geometry.Insets; // adds space around the edges
import javafx.geometry.Pos; // helps center everything
import javafx.scene.control.Button; // makes a clickable button
import javafx.scene.control.Label; //shows text

import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import weather.Period;
import weather.WeatherAPI;

import java.util.ArrayList;

public class JavaFX extends Application {
	//TextField temperature,weather;

	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Chicago Weather App");
        // “I call the starter API to get weather data. If it fails, I stop the app with an error so I know something went wrong.”
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

        Period today = forecast.get(0); // This grabs the first forecast item and stores it in a variable called today.
        Label titleLabel = new Label("Today's Weather"); // shows the scene title
        String forecastText = today.shortForecast.toLowerCase();

        Label icon;
        if (forecastText.contains("sun") || forecastText.contains("clear")) {
            icon = new Label("☀");
        } else if (forecastText.contains("cloud")) {
            icon = new Label("☁");
        } else if (forecastText.contains("rain") || forecastText.contains("shower")) {
            icon = new Label("☂");
        } else if (forecastText.contains("snow")) {
            icon = new Label("❄");
        } else if (forecastText.contains("fog") || forecastText.contains("mist")) {
            icon = new Label("🌫");
        } else if (forecastText.contains("storm") || forecastText.contains("thunder")) {
            icon = new Label("⛈");
        } else {
            icon = new Label("🌤");
        }
        //Label titleLabel = new Label("Today's Weather"); // shows the scene title
        //Label icon = new Label("☀️"); // new icon
        Label tempLabel = new Label("Temperature: " + today.temperature + "°F"); // shows the temperature
        Label forecastLabel = new Label("Forecast: " + today.shortForecast); // shows the short forecast
        Button forecastButton = new Button("View 3-Day Forecast"); // makes a button the user can click later

        // “I used a VBox because I wanted my title, labels, and button stacked vertically in a simple layout.”
        VBox root = new VBox();
        root.getChildren().addAll(titleLabel,icon, tempLabel,forecastLabel,forecastButton);

        root.setSpacing(20); // adds 20 pixels between each item
        root.setAlignment(Pos.CENTER); // centers all items
        root.setPadding(new Insets(20)); // adds empty space around the inside edges
        root.setStyle("-fx-background-color: lightblue;"); // changes bg color of Scene 1
        icon.setStyle("-fx-font-size: 40px;");

        // “I styled the labels and button so the title is most noticeable and the weather information is easier to read.”
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        icon.setStyle("-fx-font-size: 40px;"); // make icon big
        tempLabel.setStyle("-fx-font-size: 18px;");
        forecastLabel.setStyle("-fx-font-size: 18px;");
        forecastButton.setStyle("-fx-font-size: 16px;");

        // “I created the first scene from my VBox layout, attached it to the stage, and then displayed it.”
        Scene scene1 = new Scene(root, 700, 700);
        primaryStage.setScene(scene1);
        primaryStage.show();
	}

}
/*Comments: “I used a VBox to vertically arrange UI elements. I used labels for displaying weather data and a button
for navigation. I added a dynamic icon based on the forecast text using conditional logic.”
*/