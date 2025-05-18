# 🌡️ ESP32 + DHT22 + Firebase Realtime Database with Android App
This project uses an **ESP32 microcontroller** to:
- 📡 Read **temperature** and **humidity** from a DHT22 sensor.
- ☁️ Send sensor data to **Firebase Realtime Database**.
- 🚨 Automatically activate a warning **LED when temperature exceeds 40°C**.
- 💡 Receive control signals from Firebase to **toggle an LED remotely**.
- 📱 Display data in **an Android app running on a virtual emulator (Android Studio)**.

---

## 🔧 Hardware Requirements
- **ESP32 development board**
- **DHT22 sensor**
- **1x LEDs + 220Ω resistors**
- **Jumper wires, Breadboard**
- **Wi-Fi network with internet access**
- **Android Studio (for virtual emulator testing)**

---

## 📷 Wiring Diagram
| **Component** | **ESP32 GPIO** |
|--------------|---------------|
| **DHT22 (Data)** | GPIO 4 |
| **Warning LED** | GPIO 5 |
| **Remote LED** | GPIO 19 |

---

## ☁️ Firebase Setup  
1️⃣ **Create a new Firebase project** in [Firebase Console](https://console.firebase.google.com/).  
2️⃣ **Enable Realtime Database** → Set to **Test Mode** (`.read` & `.write` rules set to `true`).  
3️⃣ **Copy Firebase API Key & Database URL** from **Project Settings**.  
4️⃣ **Install required libraries in Arduino IDE:**  
   - **Firebase ESP32 Client** by Mobitz  
   - **DHT sensor library** by Adafruit  
   - **Adafruit Unified Sensor**  

---

## 📱 Android Studio Setup  
📌 **To display ESP32 sensor data in an Android emulator:**  
1️⃣ **Create a new project in Android Studio**  
2️⃣ **Choose "Empty Activity" and name it `ESP32MonitorApp`**  
3️⃣ **Connect Firebase to Android:**  
   - Open **Tools > Firebase**  
   - Under **Realtime Database**, click **"Set up Firebase Realtime Database"**  
   - Connect to your Firebase project & accept changes to `build.gradle`  

---

## 🛠 Dependencies for Firebase (Android App)  
📌 **Modify `app/build.gradle`**:
```gradle
dependencies {
    implementation 'com.google.firebase:firebase-database:20.3.0'
    implementation 'com.google.firebase:firebase-auth:22.3.1'
}
apply plugin: 'com.google.gms.google-services'
```

---

## 📌 Modify build.gradle (Project):
classpath 'com.google.gms:google-services:4.4.1'
📌 Sync Gradle and rebuild the project.

---

## 🖼️ Firebase Database Structure Example
```
{
  "sensorData": {
    "alert": false,
    "temperature": 33.8,
    "humidity": 68,
    "lightStatus": true
  }
}
```

---

## ✅ Features
✔️ Real-time monitoring of temperature & humidity in Android app. 
✔️ Automatic warning LED when temperature > 40°C. 
✔️ Remote LED control via Firebase. 
✔️ Works on Android Studio emulator, no physical device required.

---

## 📌 Notes
⚠️ For testing purposes, set Firebase rules to public:
```
{
  "rules": {
    ".read": "true",
    ".write": "true"
  }
}
```
📌 After testing, update security rules for better protection.

