package jp.ac.mayoi.onboarding

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class OnboardingViewModel : ViewModel() {
    var currentPosition: LatLng by mutableStateOf(LatLng(0.0, 0.0))
        private set

    fun onCameraChanged(newPosition: LatLng) {
        currentPosition = newPosition
        Log.d("onboarding", currentPosition.toString())
    }
}