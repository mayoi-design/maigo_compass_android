package jp.ac.mayoi.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import jp.ac.mayoi.core.resource.DrawableR
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.StringR
import jp.ac.mayoi.core.resource.colorAccent
import jp.ac.mayoi.core.resource.colorAccentSecondary
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.resource.spacingSingle
import jp.ac.mayoi.core.resource.textStyleTitle
import jp.ac.mayoi.core.util.MaigoButton

@Composable
fun OnboardingScreen(
    onCameraPositionChanged: (LatLng) -> Unit,
    onCurrentPositionClicked: () -> Unit,
    onDecideClicked: () -> Unit
) {
    val hakodate = LatLng(41.842140, 140.767)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(hakodate, 15f)
    }

    LaunchedEffect(cameraPositionState.position.target) {
        onCameraPositionChanged(cameraPositionState.position.target)
    }

    Box {
        GoogleMap(
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
            ),
            cameraPositionState = cameraPositionState,
            contentPadding = PaddingValues(
                vertical = 92.dp,
                horizontal = spacingDouble,
            ),
            modifier = Modifier.fillMaxSize()
        )

        //画像で真ん中さしてみるマーカー
        Icon(
            painter = painterResource(jp.ac.mayoi.core.resource.R.drawable.position_set),
            contentDescription = null,
            tint = colorAccent,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center)
                .offset(y = (-24).dp)
        )

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = spacingSingle)
        ) {
            FloatingActionButton(
                onClick = { onCurrentPositionClicked() },
                shape = CircleShape,
                containerColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 4.dp,
                ),
                modifier = Modifier
                    .padding(horizontal = spacingDouble)
            ) {
                Icon(
                    painter = painterResource(DrawableR.icon_current_position),
                    contentDescription = null,
                    tint = colorAccentSecondary,
                    modifier = Modifier.size(32.dp),
                )
            }
            MaigoButton(
                onClick = onDecideClicked,
                content = {
                    Text(
                        text = stringResource(StringR.onboarding_decide),
                        style = textStyleTitle
                    )
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
            onCameraPositionChanged = {},
            onDecideClicked = {},
            onCurrentPositionClicked = {},
        )
    }
}