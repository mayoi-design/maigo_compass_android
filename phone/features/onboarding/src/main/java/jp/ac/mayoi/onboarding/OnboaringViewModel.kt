package jp.ac.mayoi.onboarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class OnboardingViewModel : ViewModel() {
    var destination: LatLng by mutableStateOf(LatLng(0.0, 0.0))
        private set

    fun onCameraChanged(newPosition: LatLng) {
        destination = newPosition
    }
}