package com.toluwani.tolumilestone2

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * This screen acts as the entry point for my Milestone 2 work.
 * Instead of opening the GPS or accelerometer features immediately,
 * I decided to create a simple menu-style screen. This keeps navigation clear
 * and lets me test each sensor separately.
 */
@Composable
fun WelcomeScreen() {

    // I grab the current context so I can launch activities from inside the UI.
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title for the screen so the user knows what the menu is for.
        Text(
            text = "Travel Journal – Sensor Menu",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        /**
         * Button that opens my GPS location activity.
         * I am using an explicit intent because I want to launch
         * a separate Activity where the location logic lives.
         */
        Button(
            onClick = {
                val openGPS = Intent(context, LocationActivity::class.java)
                context.startActivity(openGPS)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open GPS Tracking")
        }

        Spacer(modifier = Modifier.height(20.dp))

        /**
         * Button that opens the accelerometer activity.
         * This lets me view live motion values separate from the GPS UI,
         * which makes debugging each part easier.
         */
        Button(
            onClick = {
                val openMotion = Intent(context, AccelerometerActivity::class.java)
                context.startActivity(openMotion)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open Motion Sensor")
        }
    }
}