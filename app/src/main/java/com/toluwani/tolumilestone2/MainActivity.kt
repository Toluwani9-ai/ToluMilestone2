package com.toluwani.tolumilestone2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    // This activity acts as the main entry point of the app.
    // When the app launches, this screen is the first thing the user sees.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instead of using XML layouts, the UI is created using Jetpack Compose.
        // setContent allows us to place our composable functions directly on screen.
        setContent { HomeScreen() }
    }
}

@Composable
fun HomeScreen() {

    // Getting the current context so that we can start new activities
    // when the user taps any of the buttons.
    val context = LocalContext.current

    // The layout of the home screen is built using a Column,
    // which stacks items vertically.
    Column(
        modifier = Modifier
            .fillMaxSize()              // Make the screen fill the entire display
            .padding(24.dp),            // Add some spacing around the edges
        verticalArrangement = Arrangement.Center,   // Centre the content vertically
        horizontalAlignment = Alignment.CenterHorizontally  // Centre items horizontally
    ) {

        // Title displayed at the top of the home screen
        Text(
            "Travel Journal Sensors",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(28.dp))   // Add space before the next button

        // First button – this opens the GPS tracking feature.
        // It starts the LocationActivity using an Intent.
        Button(onClick = {
            context.startActivity(Intent(context, LocationActivity::class.java))
        }) {
            Text("Open GPS Tracking")
        }

        Spacer(modifier = Modifier.height(20.dp))   // Spacing between the two buttons

        // Second button – this opens the motion sensor (accelerometer) screen.
        Button(onClick = {
            context.startActivity(Intent(context, AccelerometerActivity::class.java))
        }) {
            Text("Open Motion Tracking")
        }
    }
}