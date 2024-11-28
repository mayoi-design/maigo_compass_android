package jp.ac.mayoi.wear.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import jp.ac.mayoi.common.resource.locationIntentAction
import jp.ac.mayoi.common.resource.locationIntentLatitude
import jp.ac.mayoi.common.resource.locationIntentLongitude
import jp.ac.mayoi.common.resource.locationPolingInterval

class LocationService : Service() {

    private lateinit var locationClient: FusedLocationProviderClient

    private val locationListener = LocationListener { p0 ->
        val currentLatitude = p0.latitude
        val currentLongitude = p0.longitude
        Log.d("Location", "$currentLongitude, $currentLatitude")

        broadcastLocation(currentLatitude, currentLongitude)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val polingInterval =
                intent?.extras?.getLong(locationPolingInterval) ?: 1000L
            val locationRequest = LocationRequest.Builder(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                polingInterval
            ).build()

            locationClient =
                LocationServices.getFusedLocationProviderClient(this)
            locationClient.requestLocationUpdates(
                locationRequest,
                locationListener,
                Looper.getMainLooper()
            )
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun broadcastLocation(latitude: Double, longitude: Double) {
        val intent = Intent()
        intent.putExtra(locationIntentLatitude, latitude)
        intent.putExtra(locationIntentLongitude, longitude)
        intent.setAction(locationIntentAction)
        this.sendBroadcast(intent)
    }
}
