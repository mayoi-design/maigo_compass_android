package jp.ac.mayoi.traveling

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ac.mayoi.core.util.LoadState
import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

class TravelingViewModel(
    private val travelingRepository: TravelingRepository,
) : ViewModel() {
    var spotListState: LoadState<ImmutableList<LocalSpot>> by mutableStateOf(
        LoadState.Loading(null)
    )
        private set

    private var currentLocation: Location = Location(null).also {
        it.latitude = 0.0
        it.longitude = 0.0
    }

    fun getNearSpot() {
        // todo: repositoryから取得できる現在地のlat, lngをつかって、spotListStateを更新する
        val currentLat = currentLocation.latitude
        val currentLng = currentLocation.longitude
        spotListState = LoadState.Loading(spotListState.value)
        viewModelScope.launch {
            try {
                val spots =
                    travelingRepository.getNearSpots(currentLat, currentLng)
                spotListState = LoadState.Success(spots.toImmutableList())
            } catch (exception: Exception) {
                Log.e("getNearSpot", "${exception.message}")
                spotListState = LoadState.Error(spotListState.value, exception)
            }
        }
    }
}