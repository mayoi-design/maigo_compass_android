package jp.ac.mayoi.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorAccentSecondary
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.resource.spacingSingle
import jp.ac.mayoi.core.util.MaigoButton

@Composable
fun OnboardingScreen(
    onCurrentPositionClicked: () -> Unit,
    onDecideClicked: () -> Unit,
) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Box() {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
            ),
            cameraPositionState = cameraPositionState,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = spacingSingle),
            horizontalAlignment = Alignment.End,
        ) {
            FloatingActionButton(
                onClick = { onCurrentPositionClicked() },
                modifier = Modifier
                    .padding(horizontal = spacingDouble),
                shape = CircleShape,
                containerColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp,
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_current_position),
                    contentDescription = null,
                    tint = colorAccentSecondary,
                    modifier = Modifier.size(32.dp),
                )
            }
            MaigoButton(
                onClick = onDecideClicked,
                content = {
                    Text("目的地を決定")
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    MaigoCompassTheme {
        OnboardingScreen(
            onDecideClicked = {},
            onCurrentPositionClicked = {}
        )
    }
}