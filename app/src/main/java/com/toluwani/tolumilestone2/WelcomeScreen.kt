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
 * A simple menu screen that allows the user to access:
 * - GPS Tracking
 * - Accelerometer Motion Visualiser (Canvas-based)
 *
 * This file demonstrates UI layout fundamentals
 * matching your uploaded Compose layout tutorials.
 */
@Composable
fun WelcomeScreen() {
    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Travel Journal – Sensor Menu",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(32.dp))

        // GPS screen button
        Button(
            onClick = {
                ctx.startActivity(Intent(ctx, LocationActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open GPS Tracking")
        }

        Spacer(Modifier.height(20.dp))

        // Accelerometer screen button (Canvas visualiser)
        Button(
            onClick = {
                ctx.startActivity(Intent(ctx, AccelerometerActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open Motion Visualiser")
        }
    }
}