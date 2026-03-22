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

        /*Period today = forecast.get(0); // This grabs the first forecast item and stores it in a variable called today.
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
        Scene scene1 = new Scene(root, 700, 700);*/

        Period today = forecast.get(0);

        // 1. TOP LEFT: Big Icon, Big Temp, F/C Toggle, and Details
        Label bigIcon = new Label();
        String forecastText = today.shortForecast.toLowerCase();
        if (forecastText.contains("sun") || forecastText.contains("clear")) {
            bigIcon.setText("☀");
            bigIcon.setStyle("-fx-text-fill: orange; -fx-font-size: 70px;");
        } else if (forecastText.contains("rain") || forecastText.contains("shower")) {
            bigIcon.setText("☂");
            bigIcon.setStyle("-fx-text-fill: #4285F4; -fx-font-size: 70px;"); // Google Blue
        } else {
            bigIcon.setText("☁");
            bigIcon.setStyle("-fx-text-fill: gray; -fx-font-size: 70px;");
        }

        // Temperatures for math
        int fTemp = today.temperature;
        int cTemp = (int) Math.round((fTemp - 32) * 5.0 / 9.0);

        Label bigTemp = new Label(String.valueOf(fTemp));
        bigTemp.setStyle("-fx-font-size: 64px; -fx-font-family: 'Arial';");

        Label fToggle = new Label("°F");
        Label separator = new Label(" | ");
        Label cToggle = new Label("°C");

        // Default styling for toggles
        String activeToggleStyle = "-fx-font-size: 18px; -fx-text-fill: black; -fx-font-weight: bold; -fx-cursor: hand;";
        String inactiveToggleStyle = "-fx-font-size: 18px; -fx-text-fill: gray; -fx-cursor: hand;";
        fToggle.setStyle(activeToggleStyle);
        cToggle.setStyle(inactiveToggleStyle);
        separator.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");

        javafx.scene.layout.HBox toggleBox = new javafx.scene.layout.HBox(fToggle, separator, cToggle);
        toggleBox.setAlignment(Pos.TOP_LEFT);
        toggleBox.setPadding(new Insets(15, 0, 0, 5));

        // Precipitation and Wind details
        int rainChance = (today.probabilityOfPrecipitation != null)
                ? today.probabilityOfPrecipitation.value : 0;

        Label precipDetail = new Label("Precipitation: " + rainChance + "%");
        Label windDetail = new Label("Wind: " + today.windSpeed);
        precipDetail.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
        windDetail.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
        VBox detailsBox = new VBox(2, precipDetail, windDetail);
        detailsBox.setPadding(new Insets(15, 0, 0, 20));

        javafx.scene.layout.HBox topSection = new javafx.scene.layout.HBox(10, bigIcon, bigTemp, toggleBox, detailsBox);
        topSection.setAlignment(Pos.CENTER_LEFT);

        // 2. TABS: Temperature, Precipitation, Wind
        Label tempTab = new Label("Temperature");
        tempTab.setStyle("-fx-font-size: 14px; -fx-padding: 0 0 5 0; -fx-border-color: transparent transparent #FBBC04 transparent; -fx-border-width: 0 0 3 0;"); // Yellow bottom border
        Label precipTab = new Label("Precipitation");
        precipTab.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        Label windTab = new Label("Wind");
        windTab.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        javafx.scene.layout.HBox tabs = new javafx.scene.layout.HBox(30, tempTab, precipTab, windTab);
        tabs.setPadding(new Insets(20, 0, 10, 0));

        // A subtle gray line under the tabs to separate sections
        javafx.scene.control.Separator line = new javafx.scene.control.Separator();

        // We grab the next full day (Day and Night) to show High/Low
        Period nextDay = forecast.get(1);
        Period nextNight = forecast.get(2);

        VBox dayCard = new VBox(5);
        dayCard.setAlignment(Pos.CENTER);
        dayCard.setStyle("-fx-background-color: #f1f3f4; -fx-background-radius: 15; -fx-padding: 15;"); // Rounded gray box
        dayCard.setMaxWidth(80);

        Label dayName = new Label(nextDay.name.substring(0, Math.min(nextDay.name.length(), 3))); // E.g., "Mon"
        dayName.setStyle("-fx-font-size: 16px;");
        Label dayIcon = new Label(bigIcon.getText()); // Reuse the icon logic for simplicity
        dayIcon.setStyle("-fx-font-size: 30px; -fx-text-fill: " + (bigIcon.getText().equals("☀") ? "orange" : "gray") + ";");

        Label highLow = new Label(nextDay.temperature + "°  " + nextNight.temperature + "°");
        highLow.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        dayCard.getChildren().addAll(dayName, dayIcon, highLow);

        // Put the day card inside an HBox so it sits on the left
        javafx.scene.layout.HBox bottomSection = new javafx.scene.layout.HBox(dayCard);
        bottomSection.setPadding(new Insets(20, 0, 0, 0));

        // Navigation Button
        Button forecastButton = new Button("View Full 3-Day Forecast");
        forecastButton.setStyle("-fx-background-color: transparent; -fx-text-fill: blue; -fx-cursor: hand;"); // Google link style
        javafx.scene.layout.HBox buttonContainer = new javafx.scene.layout.HBox(forecastButton);
        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.setPadding(new Insets(50, 0, 0, 0));

        // Using an array so we can change the boolean inside the lambda
        final boolean[] isFahrenheit = {true};

        cToggle.setOnMouseClicked(e -> {
            if (isFahrenheit[0]) {
                bigTemp.setText(String.valueOf(cTemp));
                cToggle.setStyle(activeToggleStyle);
                fToggle.setStyle(inactiveToggleStyle);
                isFahrenheit[0] = false;
            }
        });

        fToggle.setOnMouseClicked(e -> {
            if (!isFahrenheit[0]) {
                bigTemp.setText(String.valueOf(fTemp));
                fToggle.setStyle(activeToggleStyle);
                cToggle.setStyle(inactiveToggleStyle);
                isFahrenheit[0] = true;
            }
        });

        // Assemble the final root
        VBox root1 = new VBox(topSection, tabs, line, bottomSection, buttonContainer);
        root1.setPadding(new Insets(40));
        root1.setStyle("-fx-background-color: white; -fx-font-family: 'Arial';");

        Scene scene1 = new Scene(root1, 700, 700);

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
