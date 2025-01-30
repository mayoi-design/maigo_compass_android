package jp.ac.mayoi.memory.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorBackgroundPrimary
import jp.ac.mayoi.core.resource.colorGraphGoryokaku
import jp.ac.mayoi.core.resource.colorGraphHakodateBay
import jp.ac.mayoi.core.resource.colorGraphHakodateSt
import jp.ac.mayoi.core.resource.colorGraphMihara
import jp.ac.mayoi.core.resource.colorGraphMotomachi
import jp.ac.mayoi.core.resource.colorGraphYunokawa
import jp.ac.mayoi.phone.model.PieChartEntry
import jp.ac.mayoi.phone.model.PieChartModel
import kotlin.math.min

private val arcOuterPadding = 64.dp

@Composable
internal fun MemoryPieChart(
    model: PieChartModel,
) {
    val angleRangeForEach: List<Pair<Float, Float>> = remember(model) {
        var lastAngle = -90f
        model.getRatioForEach()
            .map {
                val begin = lastAngle
                val sweepAngle = it * 360f
                lastAngle = begin + sweepAngle
                Pair(begin, sweepAngle)
            }
    }

    Canvas(
        modifier = Modifier
            .defaultMinSize(minHeight = 300.dp)
            .fillMaxWidth()
    ) {
        val d = min(size.width, size.height)
        val arcSize = Size(
            width = d - arcOuterPadding.toPx(),
            height = d - arcOuterPadding.toPx()
        )
        val arcOffset = Offset(
            x = (size.width - arcSize.width) / 2,
            y = (size.height - arcSize.height) / 2
        )

        for (it in 0..<model.entries.size) {
            drawArc(
                color = model.entries[it].arcColor,
                startAngle = angleRangeForEach[it].first,
                sweepAngle = angleRangeForEach[it].second,
                useCenter = true,
                topLeft = arcOffset,
                size = arcSize
            )
        }

        // このUIは高さ300dpのPreviewで作成している
        // 高さが300dpから変わるとグラフの色のついた部分の幅が太くなったり狭くなったりする
        // これを防ぐために、今のmin(width, height)が300dpからどれほど離れているのかを計算し、
        // 色部分の幅48dpにかけることでレスポンシブに描画できるようになる
        val differenceRate = ((d - 300.dp.toPx()) / 300.dp.toPx() + 1f)
        val centerOuterPadding =
            48.dp.toPx() * differenceRate + arcOuterPadding.toPx()
        val centerRadius = (d - centerOuterPadding) / 2
        val centerArcOffset = Offset(x = size.width / 2, y = size.height / 2)
        drawCircle(
            color = colorBackgroundPrimary,
            radius = centerRadius,
            center = centerArcOffset
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MemoryGraphPreview() {
    MaigoCompassTheme {
        Box {
            val boxHeight = 300.dp
            HorizontalDivider(modifier = Modifier.align(Alignment.Center))
            VerticalDivider(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(boxHeight)
            )

            val model = PieChartModel(
                listOf(
                    PieChartEntry(
                        label = "LabelA",
                        arcColor = colorGraphHakodateSt,
                        count = 27,
                    ),
                    PieChartEntry(
                        label = "LabelB",
                        arcColor = colorGraphHakodateBay,
                        count = 23,
                    ),
                    PieChartEntry(
                        label = "LabelC",
                        arcColor = colorGraphMotomachi,
                        count = 18,
                    ),
                    PieChartEntry(
                        label = "LabelD",
                        arcColor = colorGraphGoryokaku,
                        count = 13,
                    ),
                    PieChartEntry(
                        label = "LabelE",
                        arcColor = colorGraphYunokawa,
                        count = 13,
                    ),
                    PieChartEntry(
                        label = "LabelF",
                        arcColor = colorGraphMihara,
                        count = 9
                    )
                )
            )

            MemoryPieChart(
                model = model,
            )
        }
    }
}