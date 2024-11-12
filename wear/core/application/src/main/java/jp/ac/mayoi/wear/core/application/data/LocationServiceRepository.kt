package jp.ac.mayoi.wear.core.application.data

import android.content.Context
import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServices
import androidx.health.services.client.MeasureCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.DataTypeAvailability
import androidx.health.services.client.data.DeltaDataType
import androidx.health.services.client.data.LocationData
import androidx.health.services.client.data.SampleDataPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking

class LocationServiceRepository(context: Context) {
    private val healthServicesClient = HealthServices.getClient(context)
    private val measureClient = healthServicesClient.measureClient


    suspend fun hasLocationCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        Log.d("LOCATION","${capabilities.supportedDataTypesMeasure}")
        return (DataType.LOCATION in capabilities.supportedDataTypesMeasure)
    }

    fun locationMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(
                dataType: DeltaDataType<*, *>,
                availability: Availability
            ) {
                // Only send back DataTypeAvailability (not LocationAvailability)
                if (availability is DataTypeAvailability) {
                    Log.d("LOCATION", "availability send")
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                val locationData = data.getData(DataType.LOCATION)
                Log.d("LOCATION", "location send")
                trySendBlocking(MeasureMessage.MeasureData(locationData))
            }
        }

        Log.d("LOCATION", "Registering for data")
        measureClient.registerMeasureCallback(DataType.LOCATION, callback)

        awaitClose {
            Log.d("LOCATION", "Unregistering for data")
            runBlocking {
                measureClient.unregisterMeasureCallbackAsync(DataType.LOCATION, callback)
                    .await()
            }
        }
    }
}

sealed class MeasureMessage {
    class MeasureAvailability(val availability: DataTypeAvailability) : MeasureMessage()
    class MeasureData(val data: List<SampleDataPoint<LocationData>>) : MeasureMessage()
}