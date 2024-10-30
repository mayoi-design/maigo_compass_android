import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.ranking.RankingButtonChip
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Composable
internal fun AreaSelectButtons(
    areas: ImmutableList<RemoteRankingArea>,
    currentSelection: RemoteRankingArea?,
    onAreaSelectButtonClick: (RemoteRankingArea) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(areas) { area ->
            RankingButtonChip(
                label = area.areaName,
                isSelected = area == currentSelection,
                onClick = { onAreaSelectButtonClick(area) }
            )
        }
    }
}



