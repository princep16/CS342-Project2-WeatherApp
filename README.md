# Weather Forecast Application

A JavaFX desktop application that retrieves live weather data from the National Weather Service and presents current, hourly, and three-day forecasts through an interactive interface.

## 📸 Application Preview

<p align="center">
  <strong>Current and Hourly Weather Dashboard</strong><br>
  <img src="Screenshots%20Weather%20App/weather-dashboard.png"
       alt="Current and hourly weather dashboard"
       width="70%">
</p>

<p align="center">
  <strong>Three-Day Forecast</strong><br>
  <img src="Screenshots%20Weather%20App/three-day-forecast.png"
       alt="Three-day weather forecast"
       width="90%">
</p>

## ✨ Features

- Live weather data from the National Weather Service API
- Current temperature, forecast, precipitation, and wind conditions
- Hourly temperature, precipitation, and wind views
- Fahrenheit and Celsius temperature switching
- Three-day day-and-night forecast
- Dynamic weather icons and backgrounds based on current conditions
- Clothing suggestions based on temperature, wind, and precipitation
- Navigation between current-weather and extended-forecast scenes
- Graceful handling of unavailable or incomplete API data

## 🛠️ Technologies

- Java 11
- JavaFX
- National Weather Service REST API
- Jackson Databind
- Maven
- Git and GitHub

## Architecture

The application separates API data, presentation logic, and reusable interface components:

- **Adapter Pattern:** `WeatherAdapter` converts raw API objects into UI-ready weather information.
- **Template Method Pattern:** `WeatherSceneTemplate` provides shared scene construction while allowing each weather view to define its own content.
- **Data Models:** Dedicated model classes map forecast and hourly JSON responses into Java objects.
- **Scene-Based UI:** Separate JavaFX scenes display current conditions and extended forecasts.

## 🚀 Getting Started

### Prerequisites

- Java Development Kit 11 or later
- Maven
- IntelliJ IDEA or another Java IDE with Maven support
- Internet connection for live weather data

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/princep16/CS342-Project2-WeatherApp.git
   ```

2. Open the project in IntelliJ IDEA.

3. Allow Maven to load the dependencies from `pom.xml`.

4. Confirm that the project uses JDK 11 or later.

5. Run `JavaFX.java` from `src/main/java`.

No API key is required because the application uses the public National Weather Service API.

## Project Structure

```text
src/main/java/
├── JavaFX.java                 # Application entry point and scene navigation
├── TodayWeatherScene.java      # Current and hourly weather interface
├── ForecastScene.java          # Three-day forecast interface
├── MyWeatherAPI.java           # Hourly API requests and JSON processing
├── WeatherAdapter.java         # Converts API data into UI-ready values
├── WeatherSceneTemplate.java   # Shared JavaFX scene structure
├── HourlyWeather.java          # Processed hourly weather model
└── weather/                    # Forecast API models and provided API client
```

## 👥 Team

- Prince Patel
- Shlok Zala

### Prince Patel’s Contributions

- Integrated the National Weather Service hourly API
- Parsed and transformed JSON forecast data with Jackson
- Designed the JavaFX frontend and overall user experience
- Implemented hourly weather views, dynamic visuals, and forecast navigation
- Coordinated the project workflow through Git and GitHub
- Assisted with integration testing and debugging

## Current Scope

The application currently displays weather for Chicago using the National Weather Service grid location configured in the source code. Support for user-selected locations could be added in a future version.

## Academic Context

Developed as a team project for CS 342: Software Design at the University of Illinois Chicago.
