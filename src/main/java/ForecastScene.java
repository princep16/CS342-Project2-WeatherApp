import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import weather.Period;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;

public class ForecastScene extends WeatherSceneTemplate {

    // Stores the forecast data returned from the WeatherAPI
    // Each "Period" represents either a day or night forecast block
    private ArrayList<Period> forecast;

    // Button that allows the user to navigate back to the main (today) screen
    private Button backButton;

    // Constructor receives data + button from JavaFX main class
    // This keeps UI logic separate from data fetching logic (good design practice)
    public ForecastScene(ArrayList<Period> forecast, Button backButton) {
        this.forecast = forecast;
        this.backButton = backButton;
    }

    @Override
    protected void addContent(VBox root) {

        // ================= HEADER =================
        // Title gives context to the user about what this page shows
        Label titleLabel = new Label("3-Day Forecast");
        titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

        // Simple calendar emoji to visually reinforce "forecast view"
        Label iconLabel = new Label("📅");
        iconLabel.setStyle("-fx-font-size: 42px;");

        // VBox stacks title and icon vertically
        VBox headerBox = new VBox(10, titleLabel, iconLabel);
        headerBox.setAlignment(Pos.CENTER);

        // ================= TABS =================
        // Currently only one tab, but structure allows expansion later (good scalability)
        Label dayTab = new Label("Day & Night");

        // Bottom border acts as an "active tab indicator"
        dayTab.setStyle(
                "-fx-border-color: transparent transparent #4285F4 transparent; " +
                        "-fx-border-width: 0 0 3 0; -fx-font-size: 14px;"
        );

        HBox tabs = new HBox(25, dayTab);
        tabs.setAlignment(Pos.CENTER_LEFT);
        tabs.setPadding(new Insets(10, 0, 5, 0));

        // Separator visually divides tabs from content below
        Separator line = new Separator();

        // ================= FORECAST CARDS CONTAINER =================
        // This VBox will hold multiple "cards" (one per forecast period)
        VBox cardsContainer = new VBox(15);
        cardsContainer.setAlignment(Pos.TOP_CENTER);
        cardsContainer.setPadding(new Insets(10, 0, 10, 0));

        // ================= LOOP THROUGH FORECAST =================
        // We start from index 1 because index 0 = today's current forecast
        // We go up to 6 because API returns day + night pairs (3 days = 6 periods)
        for (int i = 1; i <= 6 && i < forecast.size(); i++) {

            Period p = forecast.get(i);

            // Adapter pattern:
            // Converts raw API object into cleaner, UI-friendly values
            WeatherAdapter weather = new WeatherAdapter(p);

            // Lowercase helps simplify text matching (avoid case issues)
            String shortForecast = weather.getShortForecast().toLowerCase();

            // ================= WEATHER ICON LOGIC =================
            // Choose an emoji based on keywords in the forecast text
            // This gives quick visual understanding to the user
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
                // Default fallback (cloudy/unknown conditions)
                smallIcon.setText("☁");
                smallIcon.setStyle("-fx-text-fill: gray; -fx-font-size: 28px;");
            }

            // ================= TEXT CONTENT =================
            // Name usually contains "Monday", "Monday Night", etc.
            Label nameLabel = new Label(weather.getNameText());
            nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            // Temperature string comes pre-formatted from adapter
            Label tempLabel = new Label(weather.getTemperatureText());
            tempLabel.setStyle("-fx-font-size: 15px;");

            // Forecast description (can be long → wrap it)
            Label forecastLabel = new Label(weather.getForecastText());
            forecastLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #202124;");
            forecastLabel.setWrapText(true);
            forecastLabel.setMaxWidth(260);

            // Wind info is secondary → lighter color for visual hierarchy
            Label windLabel = new Label(weather.getWindText());
            windLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

            // Group all text into one vertical box
            VBox textBox = new VBox(5, nameLabel, tempLabel, forecastLabel, windLabel);
            textBox.setAlignment(Pos.CENTER_LEFT);

            // ================= CARD LAYOUT =================
            // Each forecast item is shown as a "card"
            // Icon on left, text on right
            HBox card = new HBox(20, smallIcon, textBox);
            card.setAlignment(Pos.CENTER_LEFT);
            card.setPadding(new Insets(15));
            card.setMaxWidth(500);

            // Styling makes it look like a material card
            card.setStyle(
                    "-fx-background-color: #f1f3f4; " +
                            "-fx-background-radius: 14; " +
                            "-fx-border-radius: 14;"
            );

            // Add this card to the container
            cardsContainer.getChildren().add(card);
        }

        // ================= BACK BUTTON =================
        // Lets user navigate back to the main scene
        backButton.setText("Back to Today");

        // Transparent background gives it a link-like look
        backButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #1a73e8; " +
                        "-fx-font-size: 16px;"
        );

        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(25, 0, 0, 0));

        // ================= MAIN CONTENT LAYOUT =================
        // This VBox holds everything on the screen
        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(30));

        // White background for clean UI
        content.setStyle("-fx-background-color: white; -fx-font-family: Arial;");

        content.getChildren().addAll(headerBox, tabs, line, cardsContainer, buttonBox);

        // ================= SCROLL SUPPORT =================
        // Important: ensures UI doesn't break if content is too long
        ScrollPane scrollPane = new ScrollPane(content);

        // Makes scroll width match content width (clean layout)
        scrollPane.setFitToWidth(true);

        // Remove default gray scroll background
        scrollPane.setStyle("-fx-background: white;");

        // Replace root content with scrollable layout
        root.getChildren().clear();
        root.getChildren().add(scrollPane);
    }
}