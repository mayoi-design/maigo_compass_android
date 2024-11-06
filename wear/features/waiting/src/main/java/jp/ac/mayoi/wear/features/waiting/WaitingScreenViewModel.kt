package jp.ac.mayoi.wear.features.waiting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WaitingScreenViewModel : ViewModel() {
    var isButtonView: Boolean by mutableStateOf(true)
        private set

    fun onSetDestinationButtonClick() {
        isButtonView = false
    }

}
