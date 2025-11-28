package com.toluwani.tolumilestone2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme

/**
 * MainActivity is the launcher activity for the app.
 * It loads the WelcomeScreen(), which provides navigation
 * to the GPS and Accelerometer features for Milestone 2.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                WelcomeScreen()
            }
        }
    }
}