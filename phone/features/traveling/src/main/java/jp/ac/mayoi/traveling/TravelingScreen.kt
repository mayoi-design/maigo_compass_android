package jp.ac.mayoi.traveling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.util.LoadState
import jp.ac.mayoi.phone.model.LocalSpot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ParentScreen() {
    var spotListState by remember {
        mutableStateOf<LoadState<ImmutableList<LocalSpot>>>(
            LoadState.Loading(null)
        )
    }
    TravelingScreen(
        spotListState = spotListState,
        onRetryButtonClick = {
            spotListState = LoadState.Loading(null)
        },
        onTripCancelButtonClick = { },
    )
}

@Composable
internal fun TravelingScreen(
    spotListState: LoadState<ImmutableList<LocalSpot>>,
    onRetryButtonClick: () -> Unit,
    onTripCancelButtonClick: () -> Unit
) {
    when (spotListState) {
        is LoadState.Error -> {
            TravelingErrorScreen(
                onRetryButtonClick = onRetryButtonClick,
                onTripCancelButtonClick = onTripCancelButtonClick,
            )
        }
        is LoadState.Loading -> {
            TravelingLoadScreen(
                onTripCancelButtonClick = onTripCancelButtonClick,
            )
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
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelingScreenPreview() {
    // エラー時の表示
    val errorState: LoadState<ImmutableList<LocalSpot>> =
        LoadState.Error(null, Throwable("Error occurred"))
    MaigoCompassTheme {
        TravelingScreen(
            spotListState = errorState,
            onRetryButtonClick = { },
            onTripCancelButtonClick = { },
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
            spotListState = loadingState,
            onRetryButtonClick = { },
            onTripCancelButtonClick = { },
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
            spotListState = emptySpotsState,
            onRetryButtonClick = {},
            onTripCancelButtonClick = {}
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
        TravelingSpotScreen(
            spotList = rankingTestList,
            onTripCancelButtonClick = { },
        )
    }
}
