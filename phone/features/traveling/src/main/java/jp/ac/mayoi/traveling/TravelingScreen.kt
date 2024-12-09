package jp.ac.mayoi.traveling

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.spacingSingle
import jp.ac.mayoi.core.resource.textStyleBody
import jp.ac.mayoi.core.util.LoadState
import jp.ac.mayoi.core.util.MaigoButton
import jp.ac.mayoi.phone.model.LocalSpot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ParentScreen(
    viewModel: TravelingViewModel,
    onRetryButtonClick: () -> Unit,
    onTripCancelButtonClick: () -> Unit,
) {
    val context = LocalContext.current
    var isGranted by remember { mutableStateOf(checkTravelingPermission(context)) }
    if (isGranted) {
        LifecycleResumeEffect(Unit) {
            viewModel.startLocationUpdate(context)

            onPauseOrDispose {
                viewModel.stopLocationUpdate(context)
            }
        }

        TravelingScreen(
            previousState = viewModel.previousState,
            spotListState = viewModel.spotListState,
            onRetryButtonClick = onRetryButtonClick,
            onTripCancelButtonClick = onTripCancelButtonClick,
            onSendMessage = { str, spot ->
                viewModel.sendLocalSpot(str, spot)
            }
        )
    } else {
        PermissionLackingScreen(
            onPermissionGranted = {
                isGranted = true
            }
        )
    }
}

@Composable
internal fun TravelingScreen(
    previousState: LoadState<ImmutableList<LocalSpot>>,
    spotListState: LoadState<ImmutableList<LocalSpot>>,
    onRetryButtonClick: () -> Unit,
    onTripCancelButtonClick: () -> Unit,
    onSendMessage: (String, ImmutableList<LocalSpot>) -> Unit,
) {
    when (spotListState) {
        is LoadState.Error -> {
            TravelingErrorScreen(
                onRetryButtonClick = onRetryButtonClick,
                onTripCancelButtonClick = onTripCancelButtonClick,
            )
        }
        is LoadState.Loading -> {
            if (previousState is LoadState.Success) {
                val spotList = spotListState.value
                if (spotList != null) {
                    TravelingSpotScreen(
                        spotList = spotList,
                        onTripCancelButtonClick = onTripCancelButtonClick,
                        onSendLocalSpot = onSendMessage,
                    )
                } else {
                    TravelingLoadScreen(
                        onTripCancelButtonClick = onTripCancelButtonClick,
                    )
                }
            } else {
                TravelingLoadScreen(
                    onTripCancelButtonClick = onTripCancelButtonClick,
                )
            }
        }
        is LoadState.Success -> {
            if (spotListState.value.isEmpty()) {
                SpotEmptyScreen(
                    onTripCancelButtonClick = onTripCancelButtonClick,
                )
            } else {
                TravelingSpotScreen(
                    spotList = spotListState.value,
                    onTripCancelButtonClick = onTripCancelButtonClick,
                    onSendLocalSpot = onSendMessage,
                )
            }
        }
    }
}

@Composable
private fun PermissionLackingScreen(
    onPermissionGranted: () -> Unit,
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(ACCESS_FINE_LOCATION, false)
                    && permissions.getOrDefault(
                ACCESS_COARSE_LOCATION,
                false
            ) -> {
                onPermissionGranted()
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "利用には位置情報への\nアクセスが必要です",
            textAlign = TextAlign.Center,
            style = textStyleBody,
        )

        Spacer(modifier = Modifier.size(spacingSingle))

        MaigoButton(
            onClick = {
                permissionLauncher.launch(
                    listOf(
                        ACCESS_FINE_LOCATION,
                        ACCESS_COARSE_LOCATION
                    ).toTypedArray()
                )
            },
            modifier = Modifier
                .width(260.dp)
        ) {
            Text(
                text = "アクセスを許可",
                style = textStyleBody,
            )
        }
    }
}

private fun checkTravelingPermission(context: Context): Boolean {
    val isGrantedFineLocation =
        context.checkSelfPermission(ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
    val isGrantedCoarseLocation =
        context.checkSelfPermission(ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED

    return isGrantedCoarseLocation && isGrantedFineLocation
}

@Preview(showBackground = true)
@Composable
private fun TravelingScreenPreview() {
    // エラー時の表示
    val errorState: LoadState<ImmutableList<LocalSpot>> =
        LoadState.Error(null, Throwable("Error occurred"))
    MaigoCompassTheme {
        TravelingScreen(
            previousState = errorState,
            spotListState = errorState,
            onRetryButtonClick = { },
            onTripCancelButtonClick = { },
            onSendMessage = { _, _ -> },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelingScreenLoadingPreview() {
    // ロード中の表示
    val loadingState: LoadState<ImmutableList<LocalSpot>> =
        LoadState.Loading(null)
    MaigoCompassTheme {
        TravelingScreen(
            previousState = loadingState,
            spotListState = loadingState,
            onRetryButtonClick = { },
            onTripCancelButtonClick = { },
            onSendMessage = { _, _ -> },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelingScreenEmptySpotsPreview() {
    val emptySpotsState: LoadState<ImmutableList<LocalSpot>> =
        LoadState.Success(
            persistentListOf()
        )
    MaigoCompassTheme {
        TravelingScreen(
            previousState = emptySpotsState,
            spotListState = emptySpotsState,
            onRetryButtonClick = {},
            onTripCancelButtonClick = {},
            onSendMessage = { _, _ -> },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelingScreenSpotsPreview() {
    val spot = LocalSpot(
        lat = 0.0F,
        lng = 0.0F,
        message = "Hello From Preview!",
        imageUrl = "",
        spotId = "",
        reachedCount = 100,
        createdAt = "2024-10-09T23:31:15+09:00",
    )
    val rankingTestList: ImmutableList<LocalSpot> =
        List(10) {
            spot
        }.toImmutableList()
    MaigoCompassTheme {
        TravelingScreen(
            previousState = LoadState.Success(rankingTestList),
            spotListState = LoadState.Success(rankingTestList),
            onTripCancelButtonClick = { },
            onRetryButtonClick = {},
            onSendMessage = { _, _ -> },
        )
    }
}
