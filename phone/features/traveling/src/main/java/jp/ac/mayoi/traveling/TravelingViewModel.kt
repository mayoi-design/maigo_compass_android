package jp.ac.mayoi.traveling

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import jp.ac.mayoi.core.util.LoadState
import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import kotlinx.collections.immutable.ImmutableList

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
    }
}