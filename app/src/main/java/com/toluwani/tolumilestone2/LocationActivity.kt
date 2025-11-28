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

/**
 * Handles GPS permissions and location updates using
 * FusedLocationProviderClient.
 *
 * Values update live through Compose state, fulfilling
 * the "dynamic and practical use of sensor data" requirement.
 */
class LocationActivity : ComponentActivity() {

    private lateinit var fused: FusedLocationProviderClient

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) startGPS()
            else Toast.makeText(this, "Location permission is required", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fused = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            GPSUI { requestPermission() }
        }
    }

    private fun requestPermission() {
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) startGPS()
        else permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun startGPS() {
        val ok = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!ok) return

        val req = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1500L
        ).build()

        fused.requestLocationUpdates(req, object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val loc = result.lastLocation ?: return
                GPSState.lat = loc.latitude
                GPSState.lon = loc.longitude
                GPSState.acc = loc.accuracy
            }
        }, mainLooper)
    }
}

object GPSState {
    var lat by mutableStateOf(0.0)
    var lon by mutableStateOf(0.0)
    var acc by mutableStateOf(0f)
}

@Composable
fun GPSUI(onStart: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("GPS Tracking", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(20.dp))

        Text("Latitude:  ${GPSState.lat}")
        Text("Longitude: ${GPSState.lon}")
        Text("Accuracy:  ${GPSState.acc} m")

        Spacer(Modifier.height(30.dp))

        Button(onClick = onStart) {
            Text("Start GPS Tracking")
        }
    }
}