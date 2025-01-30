package jp.ac.mayoi.memory.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import jp.ac.mayoi.phone.model.PieChartModel

@Composable
internal fun MemoryGraph(
    model: PieChartModel,
) {
    Box {
        MemoryPieChart(
            model = model,
        )
    }
}
