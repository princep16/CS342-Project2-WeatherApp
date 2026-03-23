import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import weather.Period;

import java.util.ArrayList;

public class TodayWeatherScene extends WeatherSceneTemplate {

    private Period today;
    private ArrayList<HourlyWeather> hourlyData;
    private Button forecastButton;
    private boolean isFahrenheit = true;

    public TodayWeatherScene(Period today, ArrayList<HourlyWeather> hourlyData, Button forecastButton) {
        this.today = today;
        this.hourlyData = hourlyData;
        this.forecastButton = forecastButton;
    }

    @Override
    protected void addContent(VBox root) {

        WeatherAdapter todayWeather = new WeatherAdapter(today);
        String forecastText = todayWeather.getShortForecast().toLowerCase();
        String windText = (today.windSpeed + " " + today.windDirection).toLowerCase();

        String bgColor = getBackgroundColor(forecastText, today.isDaytime);
        String mainTextColor = today.isDaytime ? "#222222" : "white";
        String subTextColor = today.isDaytime ? "gray" : "#d1d5db";
        String cardColor = today.isDaytime ? "#f1f3f4" : "#4b5563";

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

        // ---------------- CLOTHING ICON ----------------
        Label clothingIcon = new Label();

        if (forecastText.contains("rain") || forecastText.contains("shower") || forecastText.contains("storm")) {
            clothingIcon.setText("🧥");
        } else if (forecastText.contains("snow") || today.temperature < 40) {
            clothingIcon.setText("🧣");
        } else if (forecastText.contains("wind") || windText.contains("gust") || windText.contains("20") || windText.contains("30")) {
            clothingIcon.setText("🧢");
        } else if ((forecastText.contains("sun") || forecastText.contains("clear")) && today.temperature >= 70) {
            clothingIcon.setText("👕");
        } else if (today.temperature > 50 && today.temperature < 70) {
            clothingIcon.setText("👖");
        } else {
            clothingIcon.setText("🧥");
        }

        clothingIcon.setStyle("-fx-font-family: 'Segoe UI Emoji', 'Apple Color Emoji', sans-serif; "
                + "-fx-font-size: 80px;");

        // ---------------- TEMP ----------------
        int fTemp = today.temperature;
        int cTemp = (int) Math.round((fTemp - 32) * 5.0 / 9.0);

        Label bigTemp = new Label(String.valueOf(fTemp));
        bigTemp.setStyle("-fx-font-size: 48px; -fx-font-family: Arial; -fx-text-fill: " + mainTextColor + ";");

        Label fToggle = new Label("°F");
        Label sep = new Label(" | ");
        Label cToggle = new Label("°C");

        String active = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand; -fx-text-fill: " + mainTextColor + ";";
        String inactive = "-fx-font-size: 16px; -fx-cursor: hand; -fx-text-fill: " + subTextColor + ";";

        fToggle.setStyle(active);
        cToggle.setStyle(inactive);
        sep.setStyle("-fx-font-size: 16px; -fx-text-fill: " + subTextColor + ";");

        HBox toggleBox = new HBox(fToggle, sep, cToggle);
        toggleBox.setAlignment(Pos.TOP_LEFT);
        toggleBox.setPadding(new Insets(10, 0, 0, 5));

        // ---------------- DETAILS ----------------
        int rainChance = (today.probabilityOfPrecipitation != null)
                ? today.probabilityOfPrecipitation.value : 0;

        Label forecastLabel = new Label(todayWeather.getForecastText());
        forecastLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: " + mainTextColor + ";");
        forecastLabel.setWrapText(true);
        forecastLabel.setMaxWidth(300);

        Label precip = new Label("Precipitation: " + rainChance + "%");
        Label wind = new Label("Wind: " + today.windSpeed + " " + today.windDirection);

        precip.setStyle("-fx-text-fill: " + subTextColor + ";");
        wind.setStyle("-fx-text-fill: " + subTextColor + ";");

        VBox detailsBox = new VBox(5, forecastLabel, precip, wind);
        detailsBox.setPadding(new Insets(10, 0, 0, 10));

        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        // ---------------- TOP ROW ----------------
        HBox topLeft = new HBox(20, bigIcon, bigTemp, toggleBox, detailsBox);
        topLeft.setAlignment(Pos.CENTER_LEFT);

        HBox topSection = new HBox();
        topSection.setAlignment(Pos.CENTER_LEFT);
        topSection.setSpacing(20);

        HBox.setHgrow(topLeft, Priority.ALWAYS);
        topSection.getChildren().addAll(topLeft, clothingIcon);

        // ---------------- TABS ----------------
        Label tempTab = new Label("Temperature");
        Label precipTab = new Label("Precipitation");
        Label windTab = new Label("Wind");

        String activeTabStyle = "-fx-border-color: transparent transparent #FBBC04 transparent; "
                + "-fx-border-width: 0 0 3 0; -fx-font-size: 14px; -fx-cursor: hand; "
                + "-fx-text-fill: " + mainTextColor + ";";
        String inactiveTabStyle = "-fx-font-size: 14px; -fx-cursor: hand; -fx-text-fill: " + subTextColor + ";";

        tempTab.setStyle(activeTabStyle);
        precipTab.setStyle(inactiveTabStyle);
        windTab.setStyle(inactiveTabStyle);

        HBox tabs = new HBox(25, tempTab, precipTab, windTab);
        tabs.setPadding(new Insets(20, 0, 10, 0));

        Separator line = new Separator();

        // ---------------- HOURLY CONTENT ----------------
        VBox hourlyContent = new VBox(12);
        hourlyContent.setAlignment(Pos.TOP_LEFT);
        hourlyContent.setPadding(new Insets(15, 0, 0, 0));

        // ---------------- CARD ----------------
        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: " + cardColor + "; -fx-background-radius: 12; -fx-padding: 15;");

        Label name = new Label(today.name);
        Label icon = new Label(bigIcon.getText());
        Label temp = new Label(today.temperature + "°");

        name.setStyle("-fx-text-fill: " + mainTextColor + ";");
        icon.setStyle("-fx-font-size: 28px;");
        temp.setStyle("-fx-text-fill: " + subTextColor + ";");

        card.getChildren().addAll(name, icon, temp);

        HBox bottom = new HBox(card);
        bottom.setPadding(new Insets(20, 0, 0, 0));

        // ---------------- BUTTON ----------------
        forecastButton.setText("View Full 3-Day Forecast");
        forecastButton.setStyle(
                "-fx-background-color: rgba(255,255,255,0.1);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 16;"
        );
        HBox btnBox = new HBox(forecastButton);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(40, 0, 0, 0));

        // ---------------- TOGGLE LOGIC ----------------
        cToggle.setOnMouseClicked(e -> {
            if (isFahrenheit) {
                bigTemp.setText(String.valueOf(cTemp));
                cToggle.setStyle(active);
                fToggle.setStyle(inactive);
                isFahrenheit = false;
                showTemperatureData(hourlyContent, mainTextColor, subTextColor);
            }
        });

        fToggle.setOnMouseClicked(e -> {
            if (!isFahrenheit) {
                bigTemp.setText(String.valueOf(fTemp));
                fToggle.setStyle(active);
                cToggle.setStyle(inactive);
                isFahrenheit = true;
                showTemperatureData(hourlyContent, mainTextColor, subTextColor);
            }
        });

        // ---------------- DEFAULT TAB CONTENT ----------------
        showTemperatureData(hourlyContent, mainTextColor, subTextColor);

        // ---------------- TAB CLICK LOGIC ----------------
        tempTab.setOnMouseClicked(e -> {
            tempTab.setStyle(activeTabStyle);
            precipTab.setStyle(inactiveTabStyle);
            windTab.setStyle(inactiveTabStyle);
            showTemperatureData(hourlyContent, mainTextColor, subTextColor);
        });

        precipTab.setOnMouseClicked(e -> {
            tempTab.setStyle(inactiveTabStyle);
            precipTab.setStyle(activeTabStyle);
            windTab.setStyle(inactiveTabStyle);
            showPrecipitationData(hourlyContent, subTextColor);
        });

        windTab.setOnMouseClicked(e -> {
            tempTab.setStyle(inactiveTabStyle);
            precipTab.setStyle(inactiveTabStyle);
            windTab.setStyle(activeTabStyle);
            showWindData(hourlyContent, mainTextColor, subTextColor);
        });

        // ---------------- FINAL ROOT ----------------
        root.getChildren().clear();
        root.getChildren().addAll(topSection, tabs, line, hourlyContent, bottom, btnBox);

        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("-fx-background-color: " + bgColor + ";");
    }

    private void showTemperatureData(VBox hourlyContent, String mainTextColor, String subTextColor) {
        hourlyContent.getChildren().clear();

        if (hourlyData == null || hourlyData.isEmpty()) {
            Label noData = new Label("No hourly temperature data available.");
            noData.setStyle("-fx-text-fill: " + mainTextColor + ";");
            hourlyContent.getChildren().add(noData);
            return;
        }

        HBox row = new HBox(25);
        row.setAlignment(Pos.CENTER_LEFT);

        int limit = Math.min(8, hourlyData.size());
        for (int i = 0; i < limit; i++) {
            HourlyWeather h = hourlyData.get(i);

            VBox item = new VBox(5);
            item.setAlignment(Pos.CENTER);

            int displayTemp = h.temperature;
            if (!isFahrenheit) {
                displayTemp = (int) Math.round((h.temperature - 32) * 5.0 / 9.0);
            }

            Label temp = new Label(displayTemp + "°");
            temp.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + mainTextColor + ";");

            Label time = new Label(h.timeLabel);
            time.setStyle("-fx-text-fill: white;");

            item.getChildren().addAll(temp, time);
            row.getChildren().add(item);
        }

        hourlyContent.getChildren().add(row);
    }

    private void showPrecipitationData(VBox hourlyContent, String subTextColor) {
        hourlyContent.getChildren().clear();

        if (hourlyData == null || hourlyData.isEmpty()) {
            Label noData = new Label("No hourly precipitation data available.");
            noData.setStyle("-fx-text-fill: " + subTextColor + ";");
            hourlyContent.getChildren().add(noData);
            return;
        }

        HBox row = new HBox(25);
        row.setAlignment(Pos.CENTER_LEFT);

        int limit = Math.min(8, hourlyData.size());
        for (int i = 0; i < limit; i++) {
            HourlyWeather h = hourlyData.get(i);

            VBox item = new VBox(5);
            item.setAlignment(Pos.CENTER);

            Label precip = new Label(h.precipitation + "%");
            precip.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

            Label time = new Label(h.timeLabel);
            time.setStyle("-fx-text-fill: " + subTextColor + ";");

            item.getChildren().addAll(precip, time);
            row.getChildren().add(item);
        }

        hourlyContent.getChildren().add(row);
    }

    private void showWindData(VBox hourlyContent, String mainTextColor, String subTextColor) {
        hourlyContent.getChildren().clear();

        if (hourlyData == null || hourlyData.isEmpty()) {
            Label noData = new Label("No hourly wind data available.");
            noData.setStyle("-fx-text-fill: " + mainTextColor + ";");
            hourlyContent.getChildren().add(noData);
            return;
        }

        HBox row = new HBox(25);
        row.setAlignment(Pos.CENTER_LEFT);

        int limit = Math.min(8, hourlyData.size());
        for (int i = 0; i < limit; i++) {
            HourlyWeather h = hourlyData.get(i);

            VBox item = new VBox(5);
            item.setAlignment(Pos.CENTER);

            Label wind = new Label(h.windSpeed);
            wind.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + mainTextColor + ";");

            Label time = new Label(h.timeLabel);
            time.setStyle("-fx-text-fill: white;");

            item.getChildren().addAll(wind, time);
            row.getChildren().add(item);
        }

        hourlyContent.getChildren().add(row);
    }

    private String getBackgroundColor(String forecastText, boolean isDaytime) {
        forecastText = forecastText.toLowerCase();

        if (!isDaytime) {
            if (forecastText.contains("rain") || forecastText.contains("shower") || forecastText.contains("storm")) {
                return "#2f3e46";
            } else if (forecastText.contains("snow")) {
                return "#3d4f5c";
            } else if (forecastText.contains("cloud")) {
                return "#4a5568";
            } else if (forecastText.contains("clear") || forecastText.contains("sun")) {
                return "#1e3a5f";
            } else {
                return "#374151";
            }
        } else {
            if (forecastText.contains("rain") || forecastText.contains("shower") || forecastText.contains("storm")) {
                return "#d6e6f2";
            } else if (forecastText.contains("snow")) {
                return "#eef6fb";
            } else if (forecastText.contains("cloud")) {
                return "#e5e7eb";
            } else if (forecastText.contains("clear") || forecastText.contains("sun")) {
                return "#dbeafe";
            } else {
                return "#f8fafc";
            }
        }
    }
}