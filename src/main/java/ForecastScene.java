import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import weather.Period;

import java.util.ArrayList;

public class ForecastScene extends WeatherSceneTemplate {

    private ArrayList<Period> forecast;
    private Button backButton;

    public ForecastScene(ArrayList<Period> forecast, Button backButton) {
        this.forecast = forecast;
        this.backButton = backButton;
    }

    @Override
    protected void addContent(VBox root) {

        // ---------------- HEADER ----------------
        Label titleLabel = new Label("3-Day Forecast");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        Label iconLabel = new Label("📅");
        iconLabel.setStyle("-fx-font-size: 42px;");

        VBox headerBox = new VBox(10, titleLabel, iconLabel);
        headerBox.setAlignment(Pos.CENTER);

        // ---------------- TABS ----------------
        Label dayTab = new Label("Day & Night");
        dayTab.setStyle("-fx-border-color: transparent transparent #4285F4 transparent; -fx-border-width: 0 0 3 0; -fx-font-size: 14px;");

        Label tempTab = new Label("Temperature");
        tempTab.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");

        Label windTab = new Label("Wind");
        windTab.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");

        HBox tabs = new HBox(25, dayTab, tempTab, windTab);
        tabs.setAlignment(Pos.CENTER_LEFT);
        tabs.setPadding(new Insets(10, 0, 5, 0));

        Separator line = new Separator();

        // ---------------- FORECAST CARDS ----------------
        VBox cardsContainer = new VBox(15);
        cardsContainer.setAlignment(Pos.TOP_CENTER);
        cardsContainer.setPadding(new Insets(10, 0, 10, 0));

        for (int i = 1; i <= 6 && i < forecast.size(); i++) {
            Period p = forecast.get(i);

            WeatherAdapter weather = new WeatherAdapter(p);

            String shortForecast = weather.getShortForecast().toLowerCase();

            Label smallIcon = new Label();
            if (shortForecast.contains("sun") || shortForecast.contains("clear")) {
                smallIcon.setText("☀");
                smallIcon.setStyle("-fx-text-fill: orange; -fx-font-size: 28px;");
            } else if (shortForecast.contains("rain") || shortForecast.contains("shower")) {
                smallIcon.setText("☂");
                smallIcon.setStyle("-fx-text-fill: #4285F4; -fx-font-size: 28px;");
            } else if (shortForecast.contains("snow")) {
                smallIcon.setText("❄");
                smallIcon.setStyle("-fx-text-fill: #5dade2; -fx-font-size: 28px;");
            } else {
                smallIcon.setText("☁");
                smallIcon.setStyle("-fx-text-fill: gray; -fx-font-size: 28px;");
            }

            Label nameLabel = new Label(weather.getNameText());
            nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label tempLabel = new Label(weather.getTemperatureText());
            tempLabel.setStyle("-fx-font-size: 15px;");

            Label forecastLabel = new Label(weather.getForecastText());
            forecastLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #202124;");
            forecastLabel.setWrapText(true);
            forecastLabel.setMaxWidth(260);

            Label windLabel = new Label(weather.getWindText());
            windLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

            VBox textBox = new VBox(5, nameLabel, tempLabel, forecastLabel, windLabel);
            textBox.setAlignment(Pos.CENTER_LEFT);

            HBox card = new HBox(20, smallIcon, textBox);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(15));
            card.setMaxWidth(500);
            card.setStyle(
                    "-fx-background-color: #f1f3f4; " +
                            "-fx-background-radius: 14; " +
                            "-fx-border-radius: 14;"
            );

            cardsContainer.getChildren().add(card);
        }

        // ---------------- BACK BUTTON ----------------
        backButton.setText("Back to Today");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a73e8; -fx-font-size: 16px;");

        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(25, 0, 0, 0));

        // ---------------- FINAL ROOT ----------------
        root.getChildren().clear();
        root.getChildren().addAll(headerBox, tabs, line, cardsContainer, buttonBox);

        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: white; -fx-font-family: Arial;");
    }
}