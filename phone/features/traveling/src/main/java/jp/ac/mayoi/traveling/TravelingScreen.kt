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
        }
    )
}

@Composable
internal fun TravelingScreen(
    spotListState: LoadState<ImmutableList<LocalSpot>>,
    onRetryButtonClick: () -> Unit
) {
    when (spotListState) {
        is LoadState.Error -> {
            TravelingErrorScreen(
                onRetryButtonClick = onRetryButtonClick,
                onTripCancelButtonClick = { }
            )
        }
        // エラー時の画面、処理を書く。
        // 一応valueの値をnullableだけど参照することもできる。
        is LoadState.Loading -> {
            TravelingLoadScreen(
                onTripCancelButtonClick = { }
            )
        }
        // ロード中の画面、処理を書く
        // 一応valueの値をnullableだけど参照することもできる。
        is LoadState.Success -> {
            if (spotListState.value.isEmpty()) {
                SpotEmptyScreen(
                    onTripCancelButtonClick = { }
                )
            } else {
                val spot = LocalSpot(
                    lat = 0.0F,
                    lng = 0.0F,
                    message = "Hello From Preview!",
                    imageUrl = "",
                    postUserId = "",
                    reachedCount = 100,
                    createdAt = "2024-10-09T23:31:15+09:00",
                )
                val rankingTestList: ImmutableList<LocalSpot> =
                    List(10) {
                        spot
                    }.toImmutableList()
                TravelingSpotScreen(
                    spotList = rankingTestList,
                    onTripCancelButtonClick = { },
                )
            }
            // ロード完了後の画面、処理を書く。
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelingScreenPreview() {
    val spotlistState: LoadState<ImmutableList<LocalSpot>> =
        LoadState.Error(null, Throwable())
    MaigoCompassTheme {
        TravelingScreen(
            spotListState = spotlistState,
            onRetryButtonClick = {}
        )
    }
}
