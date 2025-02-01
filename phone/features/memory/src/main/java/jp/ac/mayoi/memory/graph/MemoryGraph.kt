package jp.ac.mayoi.memory.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorGraphMihara
import jp.ac.mayoi.core.resource.colorGraphYunokawa
import jp.ac.mayoi.phone.model.PieChartEntry
import jp.ac.mayoi.phone.model.PieChartModel

@Composable
fun MemoryGraph(
    model: PieChartModel,
    selectedEntry: Int?,
    onChartEntrySelected: (Int?) -> Unit,
) {
    val chartBoxSize = 550.dp
    Box {
        MemoryPieChart(
            model = model,
            selectedEntry = selectedEntry,
            onEntrySelected = onChartEntrySelected,
            modifier = Modifier
                .fillMaxWidth()
                .height(chartBoxSize)
        )

        val infoBoxSize = min(chartBoxSize / 2, 225.dp)
        MemoryPieChartInfo(
            model = model,
            selectedEntry = selectedEntry,
            modifier = Modifier
                .size(infoBoxSize)
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun MemoryGraphPreview() {
    MaigoCompassTheme {
        val model = PieChartModel(
            listOf(
                PieChartEntry(
                    label = "函館駅前・大門",
                    arcColor = colorGraphYunokawa,
                    count = 60,
                ),
                PieChartEntry(
                    label = "LabelB",
                    arcColor = colorGraphMihara,
                    count = 1,
                ),
            )
        )

        MemoryGraph(
            model = model,
            selectedEntry = 0,
            onChartEntrySelected = {},
        )
    }
}