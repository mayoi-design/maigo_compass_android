package jp.ac.mayoi.ranking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.StringR
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.resource.textStyleBody
import jp.ac.mayoi.core.util.LoadState
import jp.ac.mayoi.core.util.MaigoButton
import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.phone.model.RemoteRankingArea
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun RankingScreen(
    viewModel: RankingViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.loadRankingArea()
    }

    RankingScreen(
        rankingAreaState = viewModel.rankingAreaState,
        rankingState = viewModel.rankingState,
        selectingArea = viewModel.selectingArea,
        onAreaLoadRetryButtonClick = {
            viewModel.loadRankingArea()
        },
        onUpdateSelectingArea = { area ->
            viewModel.loadRanking(area.areaId)
        },
        onSelectRankingArea = {
            viewModel.selectingArea = it
        },
    )
}

@Composable
private fun RankingScreen(
    rankingAreaState: LoadState<ImmutableList<RemoteRankingArea>>,
    rankingState: LoadState<ImmutableList<LocalSpot>>,
    selectingArea: RemoteRankingArea?,
    onAreaLoadRetryButtonClick: () -> Unit,
    onUpdateSelectingArea: (RemoteRankingArea) -> Unit,
    onSelectRankingArea: (RemoteRankingArea) -> Unit,
) {
    when (rankingAreaState) {
        is LoadState.Loading -> {
            RankingLoading()
        }

        is LoadState.Error   -> {
            RankingRootErrorScreen(
                onAreaLoadRetryButtonClick = onAreaLoadRetryButtonClick,
            )
        }

        is LoadState.Success -> {
            LaunchedEffect(selectingArea) {
                if (selectingArea != null) {
                    onUpdateSelectingArea(selectingArea)
                }
            }

            if (selectingArea != null) {
                RankingSuccessScreen(
                    areas = rankingAreaState.value,
                    currentSelection = selectingArea,
                    rankingState = rankingState,
                    onAreaSelected = { rankingArea ->
                        onSelectRankingArea(rankingArea)
                    },
                )
            } else {
                RankingRootErrorScreen(
                    onAreaLoadRetryButtonClick = onAreaLoadRetryButtonClick,
                )
            }
        }
    }
}

@Composable
private fun RankingRootErrorScreen(
    onAreaLoadRetryButtonClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ランキング情報の読み込みに失敗しました",
                style = textStyleBody
            )

            Spacer(modifier = Modifier.size(spacingDouble))

            MaigoButton(
                onClick = onAreaLoadRetryButtonClick,
                modifier = Modifier.width(240.dp)
            ) {
                Text(
                    text = stringResource(StringR.general_retry)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingScreenSuccessAndLoadingPreview() {
    MaigoCompassTheme {
        val areas = persistentListOf(
            RemoteRankingArea("エリア名1", "1"),
            RemoteRankingArea("エリア名2", "2"),
            RemoteRankingArea("名前がちょっと長いエリア名", "3")
        )
        val rankingAreaState: LoadState<ImmutableList<RemoteRankingArea>> =
            LoadState.Success(areas)
        val rankingState: LoadState<ImmutableList<LocalSpot>> =
            LoadState.Loading(null)

        RankingScreen(
            rankingAreaState = rankingAreaState,
            rankingState = rankingState,
            selectingArea = areas[0],
            onAreaLoadRetryButtonClick = {},
            onSelectRankingArea = {},
            onUpdateSelectingArea = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingScreenSuccessBothPreview() {
    MaigoCompassTheme {
        val areas = persistentListOf(
            RemoteRankingArea("エリア名1", "1"),
            RemoteRankingArea("エリア名2", "2"),
            RemoteRankingArea("名前がちょっと長いエリア名", "3")
        )
        val rankingAreaState: LoadState<ImmutableList<RemoteRankingArea>> =
            LoadState.Success(areas)
        val spots = List(10) {
            LocalSpot(
                lat = 0.0f,
                lng = 0.0f,
                message = "おすすめスポット ${it + 1}",
                imageUrl = "",
                spotId = "",
                reachedCount = 0,
                createdAt = "2024-11-27T22:02:15+09:00"
            )
        }.toImmutableList()
        val rankingState: LoadState<ImmutableList<LocalSpot>> =
            LoadState.Success(spots)

        RankingScreen(
            rankingAreaState = rankingAreaState,
            rankingState = rankingState,
            selectingArea = areas[0],
            onAreaLoadRetryButtonClick = {},
            onSelectRankingArea = {},
            onUpdateSelectingArea = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingScreenSuccessAndErrorPreview() {
    MaigoCompassTheme {
        val areas = persistentListOf(
            RemoteRankingArea("エリア名1", "1"),
            RemoteRankingArea("エリア名2", "2"),
            RemoteRankingArea("名前がちょっと長いエリア名", "3")
        )
        val rankingAreaState: LoadState<ImmutableList<RemoteRankingArea>> =
            LoadState.Success(areas)
        val rankingState: LoadState<ImmutableList<LocalSpot>> =
            LoadState.Error(null, Throwable("Preview Error!"))

        RankingScreen(
            rankingAreaState = rankingAreaState,
            rankingState = rankingState,
            selectingArea = areas[0],
            onAreaLoadRetryButtonClick = {},
            onSelectRankingArea = {},
            onUpdateSelectingArea = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingScreenLoadingPreview() {
    MaigoCompassTheme {
        val rankingAreaState: LoadState<ImmutableList<RemoteRankingArea>> =
            LoadState.Loading(null)
        val rankingState: LoadState<ImmutableList<LocalSpot>> =
            LoadState.Loading(null)

        RankingScreen(
            rankingAreaState = rankingAreaState,
            rankingState = rankingState,
            selectingArea = RemoteRankingArea(areaName = "", areaId = ""),
            onAreaLoadRetryButtonClick = {},
            onSelectRankingArea = {},
            onUpdateSelectingArea = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingScreenErrorPreview() {
    MaigoCompassTheme {
        val rankingAreaState: LoadState<ImmutableList<RemoteRankingArea>> =
            LoadState.Error(null, Throwable("Preview Error!"))
        val rankingState: LoadState<ImmutableList<LocalSpot>> =
            LoadState.Loading(null)

        RankingScreen(
            rankingAreaState = rankingAreaState,
            rankingState = rankingState,
            selectingArea = RemoteRankingArea(areaName = "", areaId = ""),
            onAreaLoadRetryButtonClick = {},
            onSelectRankingArea = {},
            onUpdateSelectingArea = {},
        )
    }
}