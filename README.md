# CS 342 Project 2 – Weather App

## 👥 Team Members
- Prince Patel
- Shlok Zala

---

## 📌 Project Overview
This project is a JavaFX-based Weather App that uses the National Weather Service API (provided in the starter code) to display weather data for Chicago.

The app will contain:
- Scene 1: Today’s Weather
- Scene 2: 3-Day Forecast
- Scene switching between the two

---

## ✅ Completed Features (Scene 1)

### 🔹 1. Weather Data Integration
- Used `WeatherAPI.getForecast("LOT", 77, 70)` to retrieve forecast data
- Stored results in an `ArrayList<Period>`
- Extracted today's weather using:
  ```java
  Period today = forecast.get(0);

2. Scene 1 UI Layout

Used a VBox layout to arrange elements vertically
Elements included:
- Title label ("Today's Weather")
- Weather icon (dynamic)
- Temperature label
- Forecast description label
- Button ("View 3-Day Forecast")

3. Dynamic Weather Icon (Extra Feature)

- Implemented logic to display different icons based on forecast text
- Converted forecast string to lowercase for easier comparison:
String forecastText = today.shortForecast.toLowerCase();

Used if-else conditions to check keywords like:

sun / clear → ☼

cloud → ☁

rain / shower → ☂

snow → ❄

fog / mist → ≈

storm / thunder → ⚡

Displays a default icon if no match is found

🚧**Work Remaining**

🔹 Scene 2 (3-Day Forecast)

Display 3 forecast periods

Include:

Day name

Temperature

Forecast description

Wind speed and direction

Use VBox blocks for each day

🔹 Scene Switching

Implement button actions:

Scene 1 → Scene 2

Scene 2 → Scene 1

🔹 Design Patterns (HW Requirement)

Plan to implement:

Adapter Pattern (for cleaner data access)

Template Method Pattern (for reusable UI structure)
