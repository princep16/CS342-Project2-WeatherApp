import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import weather.Period;

public class TodayWeatherScene extends WeatherSceneTemplate {

    private Period today;
    private Button forecastButton;

    public TodayWeatherScene(Period today, Button forecastButton) {
        this.today = today;
        this.forecastButton = forecastButton;
    }

    @Override
    protected void addContent(VBox root) {

        WeatherAdapter todayWeather = new WeatherAdapter(today);
        String forecastText = todayWeather.getShortForecast().toLowerCase();

        // ---------------- ICON ----------------
        Label bigIcon = new Label();

        if (forecastText.contains("sun") || forecastText.contains("clear")) {
            bigIcon.setText("☀");
            bigIcon.setStyle("-fx-text-fill: orange; -fx-font-size: 55px;");
        } else if (forecastText.contains("rain") || forecastText.contains("shower")) {
            bigIcon.setText("☂");
            bigIcon.setStyle("-fx-text-fill: #4285F4; -fx-font-size: 55px;");
        } else if (forecastText.contains("snow")) {
            bigIcon.setText("❄");
            bigIcon.setStyle("-fx-text-fill: #5dade2; -fx-font-size: 55px;");
        } else {
            bigIcon.setText("☁");
            bigIcon.setStyle("-fx-text-fill: gray; -fx-font-size: 55px;");
        }

        // ---------------- TEMP ----------------
        int fTemp = today.temperature;
        int cTemp = (int) Math.round((fTemp - 32) * 5.0 / 9.0);

        Label bigTemp = new Label(String.valueOf(fTemp));
        bigTemp.setStyle("-fx-font-size: 48px; -fx-font-family: Arial;");

        Label fToggle = new Label("°F");
        Label sep = new Label(" | ");
        Label cToggle = new Label("°C");

        String active = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;";
        String inactive = "-fx-font-size: 16px; -fx-text-fill: gray; -fx-cursor: hand;";

        fToggle.setStyle(active);
        cToggle.setStyle(inactive);

        HBox toggleBox = new HBox(fToggle, sep, cToggle);
        toggleBox.setAlignment(Pos.TOP_LEFT);
        toggleBox.setPadding(new Insets(10, 0, 0, 5));

        // ---------------- DETAILS ----------------
        int rainChance = (today.probabilityOfPrecipitation != null)
                ? today.probabilityOfPrecipitation.value : 0;

        Label forecastLabel = new Label(todayWeather.getForecastText());
        forecastLabel.setStyle("-fx-font-size: 16px;");
        forecastLabel.setWrapText(true);
        forecastLabel.setMaxWidth(300); // 🔥 prevents overflow

        Label precip = new Label("Precipitation: " + rainChance + "%");
        Label wind = new Label("Wind: " + today.windSpeed + " " + today.windDirection);

        precip.setStyle("-fx-text-fill: gray;");
        wind.setStyle("-fx-text-fill: gray;");

        VBox detailsBox = new VBox(5, forecastLabel, precip, wind);
        detailsBox.setPadding(new Insets(10, 0, 0, 10));

        // 🔥 allow right side to expand properly
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        // ---------------- TOP ROW ----------------
        HBox topSection = new HBox(20, bigIcon, bigTemp, toggleBox, detailsBox);
        topSection.setAlignment(Pos.CENTER_LEFT);

        // ---------------- TABS ----------------
        Label tempTab = new Label("Temperature");
        tempTab.setStyle("-fx-border-color: transparent transparent #FBBC04 transparent; -fx-border-width: 0 0 3 0;");

        Label precipTab = new Label("Precipitation");
        Label windTab = new Label("Wind");

        precipTab.setStyle("-fx-text-fill: gray;");
        windTab.setStyle("-fx-text-fill: gray;");

        HBox tabs = new HBox(25, tempTab, precipTab, windTab);
        tabs.setPadding(new Insets(20, 0, 10, 0));

        Separator line = new Separator();

        // ---------------- CARD ----------------
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: #f1f3f4; -fx-background-radius: 12; -fx-padding: 15;");

        Label name = new Label(today.name);
        Label icon = new Label(bigIcon.getText());
        Label temp = new Label(today.temperature + "°");

        icon.setStyle("-fx-font-size: 28px;");
        temp.setStyle("-fx-text-fill: gray;");

        card.getChildren().addAll(name, icon, temp);

        HBox bottom = new HBox(card);
        bottom.setPadding(new Insets(20, 0, 0, 0));

        // ---------------- BUTTON ----------------
        forecastButton.setText("View Full 3-Day Forecast");
        forecastButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #1a73e8;");

        HBox btnBox = new HBox(forecastButton);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(40, 0, 0, 0));

        // ---------------- TOGGLE LOGIC ----------------
        final boolean[] isF = {true};

        cToggle.setOnMouseClicked(e -> {
            if (isF[0]) {
                bigTemp.setText(String.valueOf(cTemp));
                cToggle.setStyle(active);
                fToggle.setStyle(inactive);
                isF[0] = false;
            }
        });

        fToggle.setOnMouseClicked(e -> {
            if (!isF[0]) {
                bigTemp.setText(String.valueOf(fTemp));
                fToggle.setStyle(active);
                cToggle.setStyle(inactive);
                isF[0] = true;
            }
        });

        // ---------------- FINAL ROOT ----------------
        root.getChildren().clear();
        root.getChildren().addAll(topSection, tabs, line, bottom, btnBox);

        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: white;");
    }
}