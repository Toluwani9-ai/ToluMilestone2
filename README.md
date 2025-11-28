Travel Journal Sensors – Milestone2 

My Milestone 2 Mobile Development, my app demonstrates practical interaction with multiple device sensors using Kotlin, Jetpack Compose, and the Android sensor framework.
The main goal of my milestone is to show dynamic, practical, and well-designed sensor features that run in real time.


Features
 GPS Tracking
-	Displays live latitude, longitude, and accuracy
-	Uses FusedLocationProviderClient for reliable, high-accuracy updates
-	Automatically refreshes when new location data arrives
-	Includes full runtime permission handling for ACCESS_FINE_LOCATION

Motion Tracking (Accelerometer + Visualiser)
-	Reads live X, Y, and Z accelerometer values
-	Calculates movement strength using vector magnitude
-	Visualises phone motion on a Canvas using a smooth animated dot
-	Demonstrates practical use of custom drawing and gesture-based interact

User Interface & Navigation
-	Built entirely with Jetpack Compose
-	Uses Material 3 for a clean and modern design
-	Simple navigation menu linking to:
  GPS Tracking Screen, and
 	Motion Visualiser Screen
-	Layout centered and spaced for readability across devices

Technologies Used
-	Kotlin
-	Jetpack Compose
-	Material 3 Design System
-	FusedLocationProviderClient (GPS)
-	SensorManager (Accelerometer)
-	Canvas API (Custom drawing)


















