package com.example.dolphone

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.Socket
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var active: Boolean = false
    private var data: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val address = "26.153.187.58"
        val port = 8080
        button.setOnClickListener{
            if(button.text == "connect"){
                button.text = "disconnect"
                active = true
            }
            else {
                active = false
                button.text = "connect"
                CoroutineScope(IO).launch {
                    client(address, port)
                }
            }
        }
    }
    private suspend fun client(address : String, port: Int) {
        val connection = Socket(address, port)
        val writer = connection.getOutputStream()
        writer.write("Hello from the other side".toByteArray())
        val reader = Scanner(connection.getInputStream())
        while(active)
        {
            var input = ""
            input = reader.nextLine()
            if(data.length < 300)
                data += "/n$input"
            else
                data = input
            textView.text = data
        }
        reader.close()
        writer.close()
        connection.close()
    }
}

val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

fun getSystemService(sensorService: String): Sensor? {
    val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
    return sensor;
}


