package jp.ac.mayoi.traveling

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.mayoi.core.resource.MaigoCompassTheme

@Composable
internal fun TravelingLoadScreen(
    onTripCancelButtonClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            TripCancelButton(
                onTripCancelButtonClick = onTripCancelButtonClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelingLoadScreenPreview() {
    MaigoCompassTheme {
        TravelingLoadScreen(
            onTripCancelButtonClick = { }
        )
    }
}