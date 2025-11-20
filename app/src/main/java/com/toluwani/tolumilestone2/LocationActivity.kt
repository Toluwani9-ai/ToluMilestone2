package com.toluwani.tolumilestone2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf

/**
 * This activity is responsible for handling all GPS-related tasks.
 * It manages permission requests, listens for new location data,
 * and exposes the latest coordinates to the Composable UI.
 */
class LocationActivity : ComponentActivity() {

    // Google’s location provider, used to request GPS updates efficiently.
    private lateinit var fusedClient: FusedLocationProviderClient

    /**
     * Launcher that asks the user for GPS permission.
     * Android requires this to be requested at runtime.
     * The result (granted or denied) is handled inside the callback.
     */
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                // If user accepts, begin receiving GPS updates.
                startGPSUpdates()
            } else {
                // Feedback when permission is denied.
                Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Prepare the GPS provider when the activity starts.
        fusedClient = LocationServices.getFusedLocationProviderClient(this)

        // Display the UI that shows the GPS data.
        setContent {
            LocationScreen(onStart = { requestGPSPermission() })
        }
    }

    /**
     * This method checks whether GPS permission is already available.
     * If not, it requests it from the user.
     */
    private fun requestGPSPermission() {
        val alreadyGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (alreadyGranted) {
            // Permission is already granted, start updating location immediately.
            startGPSUpdates()
        } else {
            // Ask the user for permission.
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    /**
     * Starts listening for GPS updates.
     * Only runs if permission has been granted.
     */
    private fun startGPSUpdates() {

        // Safety check: ensures this function cannot run without permission.
        val hasPermission =
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) return

        // Define how frequently the GPS should update.
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, // High accuracy uses GPS instead of WiFi.
            1500                              // Update interval in milliseconds.
        ).build()

        /**
         * Listener that receives new coordinates whenever the phone detects movement.
         */
        fusedClient.requestLocationUpdates(
            request,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    // Safely extract the last known location.
                    val loc = result.lastLocation ?: return

                    // Update shared state so the UI refreshes automatically.
                    GPSState.latitude = loc.latitude
                    GPSState.longitude = loc.longitude
                    GPSState.accuracy = loc.accuracy
                }
            },
            mainLooper // Ensures updates happen on the main thread.
        )
    }
}

/**
 * Shared object that stores the latest GPS readings.
 * Because these values are Compose state variables,
 * the UI automatically refreshes whenever new data arrives.
 */
object GPSState {
    var latitude by mutableDoubleStateOf(0.0)
    var longitude by mutableDoubleStateOf(0.0)
    var accuracy by mutableFloatStateOf(0f)
}

/**
 * Displays a simple interface that shows the current GPS data on screen.
 * A button is provided for the user to begin the GPS tracking process.
 */
@Composable
fun LocationScreen(onStart: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("GPS Tracking", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        // Live GPS coordinates shown to the user.
        Text("Latitude: ${GPSState.latitude}")
        Text("Longitude: ${GPSState.longitude}")
        Text("Accuracy: ${GPSState.accuracy} m")

        Spacer(modifier = Modifier.height(30.dp))

        // Clicking this button begins the permission + GPS update process.
        Button(onClick = { onStart() }) {
            Text("Start GPS Tracking")
        }
    }
}