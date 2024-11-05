import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.resource.spacingSingle
import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.ranking.RankingButtonChip
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun AreaSelectButtons(
    areas: ImmutableList<RemoteRankingArea>,
    currentSelection: RemoteRankingArea?,
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
private fun RankingScreen() {
    val areas = persistentListOf(
        RemoteRankingArea("エリア名1", "1"),
        RemoteRankingArea("エリア名2", "2"),
        RemoteRankingArea("名前がちょっと長いエリア名", "3")
    )
    var selectedArea by remember { mutableStateOf<RemoteRankingArea?>(null) }

    Column(
        modifier = Modifier.padding(spacingDouble)
    ) {
        if (areas.isEmpty()) {
            Text("エリアがありません")
        } else {
            AreaSelectButtons(
                areas = areas,
                currentSelection = selectedArea,
                onAreaSelectButtonClick = { selectedArea = it }
            )

            Text(text = "選択されたエリア: ${selectedArea?.areaName ?: "未選択"}")
        }
    }
}
