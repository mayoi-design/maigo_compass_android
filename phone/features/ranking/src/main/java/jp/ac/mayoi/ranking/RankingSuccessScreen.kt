package jp.ac.mayoi.ranking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorAccent
import jp.ac.mayoi.core.resource.colorAccentSecondary
import jp.ac.mayoi.core.resource.colorTextCaption
import jp.ac.mayoi.core.resource.textStyleTitle
import jp.ac.mayoi.core.util.LoadState
import jp.ac.mayoi.core.util.SpotCard
import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.phone.model.RemoteRankingArea
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RankingSuccessScreen(
    areas: ImmutableList<RemoteRankingArea>,
    currentSelection: RemoteRankingArea,
    rankingState: LoadState<ImmutableList<LocalSpot>>,
    onAreaSelected: (RemoteRankingArea) -> Unit,
    onCardClicked: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        stickyHeader {
            AreaSelectButtons(
                areas = areas,
                currentSelection = currentSelection,
                onAreaSelectButtonClick = onAreaSelected,
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        when (rankingState) {
            is LoadState.Loading, is LoadState.Error -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxHeight()
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (rankingState is LoadState.Loading) {
                            RankingLoading()
                        } else {
                            Text(text = "エリアのデータを読み込めませんでした")
                        }
                    }
                }
            }
            is LoadState.Success -> {
                itemsIndexed(rankingState.value) { index_, spot ->
                    val index = index_ + 1
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = "$index.",
                            style = textStyleTitle.copy(
                                fontSize = 24.sp,
                                shadow = Shadow(
                                    color = Color(0x33000000),
                                    offset = Offset(1f, 1f),
                                    blurRadius = 8f,
                                )
                            ),
                            color = when (index) {
                                1 -> colorAccent
                                2, 3 -> colorAccentSecondary
                                else -> colorTextCaption
                            },
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SpotCard(
                            spot = spot,
                            onCardClicked = onCardClicked,
                            isClickEnabled = true,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "RankingSuccessScreen Loading State")
@Composable
private fun RankingSuccessScreenLoadingPreview() {
    MaigoCompassTheme {
        val areas = persistentListOf(
            RemoteRankingArea("エリア名1", "1"),
            RemoteRankingArea("エリア名2", "2"),
            RemoteRankingArea("名前がちょっと長いエリア名", "3")
        )
        RankingSuccessScreen(
            areas = areas,
            currentSelection = areas[0],
            rankingState = LoadState.Loading(null),
            onAreaSelected = {},
            onCardClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "RankingSuccessScreen Success State")
@Composable
private fun RankingSuccessScreenSuccessPreview() {
    MaigoCompassTheme {
        val areas = persistentListOf(
            RemoteRankingArea("エリア名1", "1"),
            RemoteRankingArea("エリア名2", "2"),
            RemoteRankingArea("名前がちょっと長いエリア名", "3")
        )
        val spot = LocalSpot(
            lat = 0.0F,
            lng = 0.0F,
            message = "Hello From Preview!",
            imageUrl = "",
            postUserId = "",
            reachedCount = 100,
            createdAt = "2024-10-09T23:31:15+09:00",
        )
        val rankingTestList = List(10) { spot }.toImmutableList()

        RankingSuccessScreen(
            areas = areas,
            currentSelection = areas[0],
            rankingState = LoadState.Success(rankingTestList),
            onAreaSelected = {},
            onCardClicked = {}
        )
    }
}
