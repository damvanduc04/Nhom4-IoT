#include <WiFi.h>
#include <FirebaseESP32.h>
#include <DHT.h>

// 🔥 Thông tin WiFi của bạn
#define WIFI_SSID "Hanh Tu888"
#define WIFI_PASSWORD "123456789"

// 🔥 Thông tin Firebase của bạn
#define FIREBASE_HOST "nhom4iot-44b0d-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "zLbzILWcbDAGEM9vfATvZXVJzGmQFrKWnijOe2T5"

FirebaseConfig config;
FirebaseAuth auth;
FirebaseData firebaseData;
DHT dht(4, DHT22); // Cảm biến nhiệt độ/độ ẩm DHT22 (PIN 4)
#define LED_PIN 5   // Đèn LED nối với chân D5

void setup() {
    Serial.begin(115200);
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("\n✅ WiFi connected!");

    // 🚀 Cấu hình Firebase
    config.host = FIREBASE_HOST;
    config.signer.tokens.legacy_token = FIREBASE_AUTH;

    Firebase.begin(&config, &auth);
    Firebase.reconnectWiFi(true);
    
    pinMode(LED_PIN, OUTPUT);
    dht.begin();
}

void loop() {
    float temperature = dht.readTemperature();
    float humidity = dht.readHumidity();

    // 🚀 Gửi dữ liệu lên Firebase
    Firebase.setFloat(firebaseData, "/sensorData/temperature", temperature);
    Firebase.setFloat(firebaseData, "/sensorData/humidity", humidity);

    // 🚨 Cảnh báo nếu nhiệt độ > 40°C
    Firebase.setBool(firebaseData, "/sensorData/alert", temperature > 40);

    // 🔥 Kiểm tra trạng thái LED từ Firebase
    if (Firebase.getBool(firebaseData, "/sensorData/lightStatus")) {
        digitalWrite(LED_PIN, firebaseData.boolData() ? HIGH : LOW);
    }

    // 📝 Hiển thị dữ liệu trên Serial Monitor
    Serial.print("Temp: "); Serial.println(temperature);
    Serial.print("Humidity: "); Serial.println(humidity);

    delay(2000); // Cập nhật mỗi 2 giây
}
