package com.toluwani.tolumilestone2

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.sqrt

/**
 * This activity is responsible for reading live accelerometer data
 * from the device and displaying the X, Y, Z values using Jetpack Compose.
 *
 * It implements SensorEventListener so that it can react whenever the
 * accelerometer detects a change in motion.
 */
class AccelerometerActivity : ComponentActivity(), SensorEventListener {

    // SensorManager gives access to all hardware sensors on the device.
    private lateinit var sensorManager: SensorManager

    // Reference to the actual accelerometer sensor (may be null if unavailable).
    private var accelSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the system's SensorManager so we can access hardware sensors.
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Try to retrieve the accelerometer sensor from the device.
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Set the UI layout using Jetpack Compose.
        setContent { AccelerometerScreen() }
    }

    /**
     * Called when the activity becomes visible.
     * We register the sensor listener here to start receiving sensor updates.
     */
    override fun onResume() {
        super.onResume()

        // Only register the listener if the accelerometer exists.
        accelSensor?.let {
            sensorManager.registerListener(
                this,            // listener class (this activity)
                it,              // the accelerometer sensor
                SensorManager.SENSOR_DELAY_NORMAL // update frequency
            )
        }
    }

    /**
     * Called when the activity is no longer visible.
     * We unregister the listener to save battery and avoid memory leaks.
     */
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    /**
     * Triggered automatically whenever the accelerometer detects movement.
     * The event contains three values:
     *  - event.values[0] → X-axis acceleration
     *  - event.values[1] → Y-axis acceleration
     *  - event.values[2] → Z-axis acceleration
     */
    override fun onSensorChanged(event: SensorEvent) {

        // Update shared state values so the UI can react.
        MotionState.x = event.values[0]
        MotionState.y = event.values[1]
        MotionState.z = event.values[2]

        // Calculate the overall movement strength using vector magnitude.
        MotionState.magnitude = sqrt(
            (MotionState.x * MotionState.x +
                    MotionState.y * MotionState.y +
                    MotionState.z * MotionState.z).toDouble()
        )
    }

    // Required override but not used in this project.
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

/**
 * Simple object used to hold the most recent accelerometer values.
 * The values are wrapped in mutableStateOf so the UI automatically updates
 * whenever the sensor readings change.
 */
object MotionState {
    var x by mutableStateOf(0f)
    var y by mutableStateOf(0f)
    var z by mutableStateOf(0f)
    var magnitude by mutableStateOf(0.0)
}

/**
 * Composable UI that displays the current accelerometer readings.
 * Jetpack Compose automatically re-draws the UI when MotionState changes.
 */
@Composable
fun AccelerometerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen title
        Text(
            text = "Motion Tracking",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Display the raw X, Y, Z accelerometer values
        Text("X: ${MotionState.x}")
        Text("Y: ${MotionState.y}")
        Text("Z: ${MotionState.z}")

        Spacer(modifier = Modifier.height(20.dp))

        // Show the calculated movement strength
        Text("Movement Strength: ${"%.2f".format(MotionState.magnitude)}")
    }
}