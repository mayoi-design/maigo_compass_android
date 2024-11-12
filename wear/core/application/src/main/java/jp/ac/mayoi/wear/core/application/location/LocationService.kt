package jp.ac.mayoi.wear.core.application.location


import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import jp.ac.mayoi.wear.core.resource.locationIntentAction
import jp.ac.mayoi.wear.core.resource.locationIntentLatitude
import jp.ac.mayoi.wear.core.resource.locationIntentLongitude

import androidx.health.services.client.HealthServices
import androidx.health.services.client.HealthServicesClient
import androidx.health.services.client.MeasureCallback
import androidx.health.services.client.MeasureClient
import androidx.health.services.client.PassiveListenerCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.DeltaDataType
import androidx.health.services.client.data.LocationAvailability
import androidx.health.services.client.data.LocationData
import androidx.health.services.client.data.PassiveListenerConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import androidx.concurrent.futures.await
import androidx.health.services.client.data.DataTypeAvailability
import androidx.health.services.client.data.SampleDataPoint
import androidx.health.services.client.proto.DataProto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

//class LocationService : Service() {
//    //private lateinit var locationClient: FusedLocationProviderClient
//    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
//    private val healthServicesClient by lazy { HealthServices.getClient(this) }
//    private val measureClient by lazy { healthServicesClient.measureClient }
//
////    private var locationRequest: LocationRequest =
////        LocationRequest.Builder(
////            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
////            1000L
////        ).build()
//
////    private val locationCallBack = object : LocationCallback() {
////        override fun onLocationResult(locationResult: LocationResult) {
////            for (location in locationResult.locations){
////                val latitude = location.latitude
////                val longitude = location.longitude
////            }
////
////        }
////    }
//
////    private val locationListener = LocationListener { p0 ->
////        val currentLatitude = p0.latitude
////        val currentLongitude = p0.longitude
////        Log.d("Location", "$currentLongitude, $currentLatitude")
////
////
////        broadcastLocation(currentLongitude, currentLatitude)
////    }
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//
//        if(ActivityCompat.checkSelfPermission(
//            this,Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//            this,Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//            ){
//
//            serviceScope.launch {
//                if (hasHeartRateCapability()){
//
//                }else{
//                    Log.d("Location", "Location capability not available on this device")
//                }
//            }
//
//            Log.d("Location", "Location Service has been started and the callback is attached")
//        }
//
//        return super.onStartCommand(intent, flags, startId)
//    }
//
//    override fun onBind(intent: Intent?): IBinder? = null
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("Location", "Location Service destroyed")
//        serviceScope.cancel()
//    }
//
//    @ExperimentalCoroutinesApi
//    private fun locationMeasureFlow() = callbackFlow{
//
//        val healthLocationCallback = object : MeasureCallback {
//            override fun onAvailabilityChanged(
//                dataType: DeltaDataType<*, *>,
//                availability: Availability
//            ) {
//                if (availability is DataTypeAvailability) {
//                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
//                }
//            }
//
//            override fun onDataReceived(data: DataPointContainer) {
//                val locationData = data.getData(DataType.LOCATION)
//                trySendBlocking(MeasureMessage.MeasureData(locationData))
//            }
//        }
//
//        Log.d("Location", "Registering for Data")
//        measureClient.registerMeasureCallback(DataType.Companion.LOCATION, healthLocationCallback)
//
//        awaitClose {
//            Log.d("Location", "Unregistering for Data")
//            runBlocking {
//                measureClient.unregisterMeasureCallbackAsync(DataType.Companion.LOCATION, healthLocationCallback)
//                    .await()
//            }
//        }
//
//    }
//
//
//    private suspend fun hasHeartRateCapability(): Boolean {
//        val capabilities = measureClient.getCapabilitiesAsync().await()
//        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesMeasure)
//    }
//
//    private fun broadcastLocation(latitude: Double, longitude: Double) {
//        val intent = Intent()
//        intent.putExtra(locationIntentLatitude, latitude)
//        intent.putExtra(locationIntentLongitude, longitude)
//        intent.setAction(locationIntentAction)
//        Log.d("Location", "sendlocation")
//        this.sendBroadcast(intent)
//    }
//
//
//
//
//}
//
//
