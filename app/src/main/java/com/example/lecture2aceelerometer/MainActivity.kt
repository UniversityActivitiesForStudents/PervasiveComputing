package com.example.lecture2aceelerometer

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lecture2aceelerometer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var accelerometerSensor: Sensor? = null
    private val screenWidth
        get() = resources.displayMetrics.widthPixels
    private val screenHeight
        get() = resources.displayMetrics.heightPixels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    }

    override fun onSensorChanged(p0: SensorEvent?) {
        when(p0!!.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                val x = p0.values[0]
                val y = p0.values[1]
                val z = p0.values[2]
                binding.tvSensorData.text = "X: $x\nY: $y\nZ: $z"

                binding.ivBall.x -= x
                binding.ivBall.y += y
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onStart() {
        super.onStart()
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

}