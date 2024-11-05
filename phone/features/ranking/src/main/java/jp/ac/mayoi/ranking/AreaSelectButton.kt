import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.resource.spacingSingle
import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.ranking.RankingButtonChip
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun AreaSelectButtons(
    areas: ImmutableList<RemoteRankingArea>,
    currentSelection: RemoteRankingArea,
    onAreaSelectButtonClick: (RemoteRankingArea) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacingDouble),
        horizontalArrangement = Arrangement.spacedBy(spacingSingle)
    ) {
        items(areas) { area ->
            RankingButtonChip(
                label = area.areaName,
                isSelected = area.areaId == currentSelection?.areaId,
                onClick = { onAreaSelectButtonClick(area) }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun AreaSelectButtonsFirstAreaSelectedPreview() {
    MaigoCompassTheme {
        val areas = persistentListOf(
            RemoteRankingArea("エリア名1", "1"),
            RemoteRankingArea("エリア名2", "2"),
            RemoteRankingArea("名前がちょっと長いエリア名", "3")
        )

        AreaSelectButtons(
            areas = areas,
            currentSelection = areas[0],
            onAreaSelectButtonClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AreaSelectButtonsSecondAreaSelectedPreview() {
    MaigoCompassTheme {
        val areas = persistentListOf(
            RemoteRankingArea("エリア名1", "1"),
            RemoteRankingArea("エリア名2", "2"),
            RemoteRankingArea("名前がちょっと長いエリア名", "3")
        )

        AreaSelectButtons(
            areas = areas,
            currentSelection = areas[1],
            onAreaSelectButtonClick = {}
        )
    }
}
