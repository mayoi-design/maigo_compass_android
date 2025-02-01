package jp.ac.mayoi.memory.graph

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import jp.ac.mayoi.core.resource.spacingHalf
import jp.ac.mayoi.core.resource.textStyleMemoryPieChartCaption
import jp.ac.mayoi.core.resource.textStyleMemoryPieChartCaptionSubText
import jp.ac.mayoi.core.resource.textStyleMemoryPieChartCenter
import jp.ac.mayoi.core.resource.textStyleMemoryPieChartCenterSubText
import jp.ac.mayoi.core.resource.textStyleMemoryPieChartLabel
import jp.ac.mayoi.phone.model.PieChartModel
import kotlin.math.roundToInt

@Composable
internal fun MemoryPieChartInfo(
    model: PieChartModel,
    selectedEntry: Int?,
    modifier: Modifier = Modifier,
) {
    Crossfade(
        targetState = selectedEntry,
        modifier = modifier,
    ) { fixedSelectedEntry ->
        if (fixedSelectedEntry != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = model.entries[fixedSelectedEntry].label,
                    style = textStyleMemoryPieChartLabel,
                )

                Row(
                    verticalAlignment = Alignment.Bottom,
                ) {
                    Text(
                        text = "${model.entries[fixedSelectedEntry].count}",
                        style = textStyleMemoryPieChartCenter,
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.size(spacingHalf))
                    Text(
                        text = "回",
                        style = textStyleMemoryPieChartCenterSubText,
                        modifier = Modifier.alignByBaseline()
                    )
                }

                val percentage = remember(model) { model.getRatioForEach() }
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "${(percentage[fixedSelectedEntry] * 100).roundToInt()}",
                        style = textStyleMemoryPieChartCaption,
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.size(spacingHalf))
                    Text(
                        text = "%",
                        style = textStyleMemoryPieChartCaptionSubText,
                        modifier = Modifier.alignByBaseline()
                    )
                }
            }
        }
    }
}