package jp.ac.mayoi.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorAccent
import jp.ac.mayoi.core.resource.colorAccentSecondary
import jp.ac.mayoi.core.resource.colorTextCaption
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.resource.spacingSingle
import jp.ac.mayoi.core.resource.spacingTriple
import jp.ac.mayoi.core.resource.textStyleTitle
import jp.ac.mayoi.core.util.SpotCard
import jp.ac.mayoi.phone.model.LocalSpot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun RankingList(
    spotList: ImmutableList<LocalSpot>,
    isClickEnabled: Boolean,
    onCardClicked: () -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(spacingTriple),
        contentPadding = PaddingValues(vertical = spacingTriple),
    ) {
        itemsIndexed(spotList) { index_, spot ->
            val index = index_ + 1
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = spacingDouble),
            ) {
                Text(
                    text = "$index.",
                    style = textStyleTitle.copy(
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
                Spacer(modifier = Modifier.size(spacingSingle))
                SpotCard(
                    spot = spot,
                    onCardClicked = {},
                    isClickEnabled = false,
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingListPreview() {
    MaigoCompassTheme {
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
        RankingList(
            spotList = rankingTestList,
            onCardClicked = {},
            isClickEnabled = false,
        )
    }
}