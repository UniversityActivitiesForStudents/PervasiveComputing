package com.example.lecture3gyroscopesensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lecture3gyroscopesensor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var gyroscopeSensor: Sensor? = null
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        when(p0!!.sensor.type) {
            Sensor.TYPE_GYROSCOPE -> {
                val x = p0.values[0]
                val y = p0.values[1]
                val z = p0.values[2]
                binding.tvSensorData.text = "X=$x\nY=$y\nz=$z"
                binding.hsvSensorImage.smoothScrollTo(binding.hsvSensorImage.scrollX + y.toInt() * 100, 0)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onStart() {
        super.onStart()
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

}