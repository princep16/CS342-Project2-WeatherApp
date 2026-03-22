import javafx.application.Application;
import javafx.geometry.Insets; // adds space around the edges
import javafx.geometry.Pos; // helps center everything
import javafx.scene.control.Button; // makes a clickable button
import javafx.scene.control.Label; //shows text

import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
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
        // “I call the starter API to get weather data. If it fails, I stop the app with an error, so I know something went wrong.”
		ArrayList<Period> forecast = WeatherAPI.getForecast("LOT",77,70);
		if (forecast == null){
			throw new RuntimeException("Forecast did not load");
		}

        Period today = forecast.get(0); // This grabs the first forecast item and stores it in a variable called today.
        Label titleLabel = new Label("Today's Weather"); // shows the scene title
        String forecastText = today.shortForecast.toLowerCase();
        String windText = today.windSpeed.toLowerCase();

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
        icon.setStyle("-fx-font-size: 150px;");

        Label clothingIcon = new Label(); // Label for the icon to suggest clothing.
        if (forecastText.contains("rain") || forecastText.contains("shower") || forecastText.contains("storm")) {
            clothingIcon.setText("🧥"); // Raincoat
        } else if (forecastText.contains("snow") || today.temperature < 40) {
            clothingIcon.setText("🧣"); // Scarf/Winter wear for snow
        } else if (forecastText.contains("wind") || windText.contains("gust") || windText.contains("20") || windText.contains("30")) {
            clothingIcon.setText("🧢"); // Cap for windy days
        } else if ((forecastText.contains("sun") || forecastText.contains("clear")) && today.temperature >= 70) {
            clothingIcon.setText("👕"); // T-shirt for sunny and hot
        } else if (today.temperature > 50 && today.temperature < 70) {
            clothingIcon.setText("👖"); // Standard pants/casual for mild weather
        } else {
            clothingIcon.setText("🧥"); // Default
        }
        clothingIcon.setStyle("-fx-font-family: 'Segoe UI Emoji', 'Apple Color Emoji', 'Times New Roman'; -fx-font-size: 30px;");

        int todayRainChance = 0;
        if (today.probabilityOfPrecipitation != null) {
            todayRainChance = today.probabilityOfPrecipitation.value;
        }

        Label rainLabel = new Label("Chance of Rain: " + todayRainChance + "%");
        rainLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: darkblue; -fx-font-weight: bold;");
        //Label titleLabel = new Label("Today's Weather"); // shows the scene title
        //Label icon = new Label("☀️"); // new icon

        javafx.scene.layout.HBox iconBox = new javafx.scene.layout.HBox(30); // 30px spacing between icons
        iconBox.setAlignment(Pos.CENTER);
        iconBox.getChildren().addAll(icon, clothingIcon);

        Label tempLabel = new Label("Temperature: " + today.temperature + "°F"); // shows the temperature
        Label forecastLabel = new Label("Forecast: " + today.shortForecast); // shows the short forecast
        Button forecastButton = new Button("View 3-Day Forecast"); // makes a button the user can click later

        // “I used a VBox because I wanted my title, labels, and button stacked vertically in a simple layout.”
        VBox root = new VBox();
        root.getChildren().addAll(titleLabel,iconBox, tempLabel, rainLabel, forecastLabel,forecastButton);

        root.setSpacing(20); // adds 20 pixels between each item
        root.setAlignment(Pos.CENTER); // centers all items
        root.setPadding(new Insets(20)); // adds empty space around the inside edges
        root.setStyle("-fx-background-color: lightblue;"); // changes bg color of Scene 1
        icon.setStyle("-fx-font-size: 40px;");

        // I styled the labels and button so the title is most noticeable and the weather information is easier to read.
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        icon.setStyle("-fx-font-size: 40px;"); // set size for icon
        tempLabel.setStyle("-fx-font-size: 18px;");
        forecastLabel.setStyle("-fx-font-size: 18px;");
        forecastButton.setStyle("-fx-font-size: 16px;");

        // I created the first scene from my VBox layout, attached it to the stage, and then displayed it.
        Scene scene1 = new Scene(root, 700, 700);

        // Scene 2 Implementation starts
        VBox root2 = new VBox();
        root2.setSpacing(15);
        root2.setAlignment(Pos.CENTER);
        root2.setPadding(new Insets(20));
        root2.setStyle("-fx-background-color: lightyellow;");

        Label forecastTitle = new Label("3-Day Forecast");
        forecastTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // GUI element for Scene 2
        Label calendarIcon = new Label("📅");
        calendarIcon.setStyle("-fx-font-size: 50px;");

        root2.getChildren().addAll(forecastTitle, calendarIcon);

        // Loop to get the next 6 periods (Day & Night for 3 days)
        for (int i = 1; i <= 6 && i < forecast.size(); i++) {
            Period p = forecast.get(i);

            // Get Temperature, wind speed and wind direction
            String details = String.format("%s: %s°F | Wind: %s %s",
                    p.name, p.temperature, p.windSpeed, p.windDirection);

            Label periodLabel = new Label(details);
            periodLabel.setStyle("-fx-font-size: 16px; -fx-background-color: lightgreen; " +
                    "-fx-padding: 5px; -fx-border-color: gray;");
            root2.getChildren().add(periodLabel);
        }

        Button backButton = new Button("Back to Today");
        backButton.setStyle("-fx-font-size: 16px;");

        // Add margin after VBox
        VBox.setMargin(backButton, new Insets(20, 0, 0, 0));
        root2.getChildren().add(backButton);

        Scene scene2 = new Scene(root2, 700, 700);
        // Button Logic
        forecastButton.setOnAction(e -> primaryStage.setScene(scene2));
        backButton.setOnAction(e -> primaryStage.setScene(scene1));

        primaryStage.setScene(scene1);
        primaryStage.show();
	}

}
/*Comments: “I used a VBox to vertically arrange UI elements. I used labels for displaying weather data and a button
for navigation. I added a dynamic icon based on the forecast text using conditional logic.”
*/
/*Comments: "I implemented the probability of precipitation in scene1"*/
