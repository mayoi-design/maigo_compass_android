package jp.ac.mayoi.wear.core.application.location

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.health.services.client.data.DataTypeAvailability
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.ac.mayoi.wear.core.application.data.LocationServiceRepository
import jp.ac.mayoi.wear.core.application.data.MeasureMessage
import kotlinx.coroutines.launch

class LocationViewModel(
    private val locationServiceRepository: LocationServiceRepository
) : ViewModel() {

    var latitude: MutableState<Double> = mutableDoubleStateOf(-1.0)
        private set

    var altitude: MutableState<Double> = mutableDoubleStateOf(-1.0)
        private set

    var azimuth: MutableState<Double> = mutableDoubleStateOf(-1.0)
        private set

    var availability: MutableState<DataTypeAvailability> =
        mutableStateOf(DataTypeAvailability.UNKNOWN)
        private set

     var uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)
         private set

    init {
        viewModelScope.launch {
            val supported = locationServiceRepository.hasLocationCapability()
            uiState.value = if (supported) {
                UiState.Supported
            } else {
                UiState.NotSupported
            }
            val test = uiState.value
            Log.d("LOCATION", "$test :uistateChanged")
        }

        viewModelScope.launch {
            locationServiceRepository.locationMeasureFlow()
                .collect { measureMessage ->
                    when (measureMessage) {
                        is MeasureMessage.MeasureAvailability -> {
                            availability.value = measureMessage.availability
                            val test = availability.value
                            Log.d("LOCATION", "$test: availability!")
                        }
                        is MeasureMessage.MeasureData -> {
                            val data = measureMessage.data
                            val location = data.last()
                            latitude.value = location.value.latitude
                            altitude.value = location.value.altitude
                            azimuth.value = location.value.bearing
                            val test1 = location.value.latitude
                            val test2 = location.value.altitude
                            val test3 = location.value.bearing
                            Log.d("LOCATION", "lat:$test1,alt:$test2,bear:$test3,updated!")
                        }
                    }
                }
        }
    }

}

class LocationDataViewModelFactory(
    private val locationServiceRepository: LocationServiceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(
                locationServiceRepository = locationServiceRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

sealed class UiState {
    data object Startup : UiState()
    data object NotSupported : UiState()
    data object Supported : UiState()
}