package com.example.nhom4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nhom4.ui.theme.Nhom4Theme
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivity : ComponentActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 🔥 Khởi tạo Firebase
        database = FirebaseDatabase.getInstance()
        ref = database.getReference("sensorData")

        setContent {
            Nhom4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        SensorDisplay() // Hiển thị nhiệt độ, độ ẩm
                        Spacer(modifier = Modifier.height(20.dp))
                        ToggleLightButton() // Nút bật/tắt đèn
                    }
                }
            }
        }
    }
}

// ✅ Cập nhật dữ liệu từ Firebase bằng StateFlow để giảm tải truy vấn
class SensorViewModel {
    private val database = FirebaseDatabase.getInstance().getReference("sensorData")
    private val _temperature = MutableStateFlow("--")
    private val _humidity = MutableStateFlow("--")
    val temperature = _temperature.asStateFlow()
    val humidity = _humidity.asStateFlow()

    init {
        database.child("temperature").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _temperature.value = snapshot.getValue(Float::class.java)?.toString() ?: "--"
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        database.child("humidity").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _humidity.value = snapshot.getValue(Float::class.java)?.toString() ?: "--"
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}

// 🎨 Hiển thị nhiệt độ, độ ẩm
@Composable
fun SensorDisplay(viewModel: SensorViewModel = SensorViewModel()) {
    val temperature by viewModel.temperature.collectAsState()
    val humidity by viewModel.humidity.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)), // Màu xanh nhẹ
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Nhiệt độ: $temperature °C", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Độ ẩm: $humidity %", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

// 💡 Nút bật/tắt đèn LED với màu sắc phản hồi
@Composable
fun ToggleLightButton() {
    var isLightOn by remember { mutableStateOf(false) }
    val ref = FirebaseDatabase.getInstance().getReference("sensorData")

    Button(
        onClick = {
            isLightOn = !isLightOn
            ref.child("lightStatus").setValue(isLightOn) // Gửi trạng thái lên Firebase
        },
        colors = ButtonDefaults.buttonColors(containerColor = if (isLightOn) Color.Red else Color.Green),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            if (isLightOn) "🔴 Tắt Đèn" else "🟢 Bật Đèn",
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Nhom4Theme {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SensorDisplay()
            ToggleLightButton()
        }
    }
}
