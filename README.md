🌡️ ESP32 + DHT22 + Firebase Realtime Database with Android App
This project demonstrates how to use an ESP32 microcontroller to:

📡 Read temperature and humidity from a DHT22 sensor

☁️ Send sensor data to Firebase Realtime Database

🚨 Automatically activate a warning LED when temperature exceeds 40°C

💡 Remotely toggle a LED via Firebase

📱 Display data and control the LED using an Android app running on a virtual emulator in Android Studio

🔧 Hardware Requirements
ESP32 development board (e.g., ESP32 DevKitC)
DHT22 sensor (temperature and humidity)
2x LEDs (with 220Ω resistors)
Jumper wires and breadboard
Wi-Fi network with internet access
PC with Android Studio (for virtual emulator testing)
📷 Wiring Diagram

Component	ESP32 GPIO
DHT22 (Data)	GPIO 4
Warning LED	GPIO 5
Remote LED	GPIO 19
Notes:

Connect DHT22 VCC to 3.3V or 5V (check your module’s specs).
Add a 4.7k–10kΩ pull-up resistor between DHT22 Data and VCC.
LEDs should have 220Ω resistors in series to limit current.
☁️ Firebase Setup
Create a Firebase Project:
Go to Firebase Console and create a new project.
Enable Realtime Database:
Navigate to Realtime Database → Create Database.
Start in Test Mode (sets .read and .write to true).
Get Credentials:
Copy the API Key and Database URL from Project Settings.
Install Arduino Libraries:
Open Arduino IDE → Library Manager.
Install:
Firebase ESP Client by Mobitz
DHT sensor library by Adafruit
Adafruit Unified Sensor by Adafruit
📱 Android Studio Setup
To display ESP32 sensor data and control the LED in an Android emulator:

Create a New Project:

Open Android Studio → New Project.
Select Empty Activity.
Name: ESP32MonitorApp.
Language: Java or Kotlin.
Connect Firebase:

Go to Tools > Firebase.
Under Realtime Database, select Set up Firebase Realtime Database.
Connect to your Firebase project and accept changes to build.gradle.
Verify Dependencies:

Ensure the following are added to app/build.gradle:

gradle

Sao chép
dependencies {
    implementation 'com.google.firebase:firebase-database:20.3.0'
    implementation 'com.google.firebase:firebase-auth:22.3.1'
}
apply plugin: 'com.google.gms.google-services'
In build.gradle (Project):

gradle

Sao chép
classpath 'com.google.gms:google-services:4.4.1'
Sync and Build:

Click Sync Project with Gradle Files.
Run the app on an Android Virtual Device (AVD) in the emulator.
🖼️ Firebase Database Structure
Example structure for sensor data and LED control:

json

Sao chép
{
  "sensorData": {
    "temperature": 33.8,
    "humidity": 68,
    "lightStatus": true
  }
}
✅ Features
✔️ Real-time monitoring of temperature and humidity in the Android app
✔️ Automatic warning LED activation when temperature exceeds 40°C
✔️ Remote control of a LED via Firebase
✔️ App runs on Android Studio emulator, no physical Android device required
✔️ Scalable codebase for adding more sensors or controls
📌 Notes
Firebase Rules: For testing, use public rules:
json

Sao chép
{
  "rules": {
    ".read": "true",
    ".write": "true"
  }
}
⚠️ Warning: Update to secure rules for production (e.g., restrict access to authenticated users).
Ensure a stable Wi-Fi connection for ESP32 to communicate with Firebase.
Verify the DHT22 sensor is properly connected and powered.
Test the Android app in the emulator with an active internet connection.
