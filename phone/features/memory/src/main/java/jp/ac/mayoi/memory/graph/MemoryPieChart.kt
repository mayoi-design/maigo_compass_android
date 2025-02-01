package jp.ac.mayoi.memory.graph

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
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
import kotlin.math.atan2
import kotlin.math.min

private val arcOuterPaddingAsPx
    @Composable get() = with(LocalDensity.current) { 64.dp.toPx() }
private val arcOuterExpandWidthAsPx
    @Composable get() = with(LocalDensity.current) { 24.dp.toPx() }

@Composable
internal fun MemoryPieChart(
    model: PieChartModel,
    selectedEntry: Int?,
    onEntrySelected: (Int?) -> Unit,
    modifier: Modifier = Modifier,
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

    BoxWithConstraints(
        modifier = modifier
    ) {
        val density = LocalDensity.current
        val boxSize = with(density) {
            Size(constraints.maxWidth.toDp().toPx(), constraints.maxHeight.toDp().toPx())
        }
        val diameter = min(boxSize.width, boxSize.height)
        val arcSize = Size(
            width = diameter - arcOuterPaddingAsPx,
            height = diameter - arcOuterPaddingAsPx,
        )
        val expandedArcSize = Size(
            width = diameter - arcOuterPaddingAsPx + arcOuterExpandWidthAsPx,
            height = diameter - arcOuterPaddingAsPx + arcOuterExpandWidthAsPx,
        )

        // このUIは高さ300dpのPreviewで作成している
        // 高さが300dpから変わるとグラフの色のついた部分の幅が太くなったり狭くなったりする
        // これを防ぐために、今のmin(width, height)が300dpからどれほど離れているのかを計算し、
        // 色部分の幅48dpにかけることでレスポンシブに描画できるようになる
        val scale = with(density) { ((diameter - 300.dp.toPx()) / 300.dp.toPx() + 1f) }
        val centerOuterPadding = with(density) { 48.dp.toPx() * scale + arcOuterPaddingAsPx }
        val centerRadius = (diameter - centerOuterPadding) / 2
        val centerArcOffset = Offset(x = boxSize.width / 2, y = boxSize.height / 2)

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(selectedEntry) {
                    detectTapGestures { p ->
                        val center = Offset(size.width.toFloat() / 2, size.height.toFloat() / 2)
                        val tapPoint = Offset(x = p.x - center.x, y = center.y - p.y)
                        val r = tapPoint.getDistance()

                        // 回転角の回る方向を反時計回りから時計回りにして、-90°スタートにする
                        val rawThetaInDeg =
                            360f - Math.toDegrees(atan2(tapPoint.y, tapPoint.x).toDouble())
                                .mod(360f)
                        val theta = if (rawThetaInDeg > 270) {
                            rawThetaInDeg - 360f
                        } else {
                            rawThetaInDeg
                        }

                        for (i in angleRangeForEach.indices) {
                            val begin = angleRangeForEach[i].first
                            val end = begin + angleRangeForEach[i].second

                            // 円グラフの特性上270°を跨いで区間が存在することがないので簡単な判定で良い
                            if (begin <= theta && theta < end) {
                                // fixme: rかouterR, innerRが微妙にズレてる気がする？
                                val outerR =
                                    if (i == selectedEntry) expandedArcSize.width / 2 else arcSize.width / 2
                                Log.d(
                                    "MemoryPieChart",
                                    "Theta Condition Satisfied. i:$i theta: $theta, r: $r"
                                )

                                if (centerRadius <= r && r < outerR) {
                                    onEntrySelected(i)
                                    Log.d("MemoryPieChart", "entry $i selected.")
                                    return@detectTapGestures
                                }
                            }
                        }
                        onEntrySelected(null)
                        Log.d(
                            "MemoryPieChart",
                            "Nothing Selected. RawTapPos: $p, Tap pos: $tapPoint, theta: $theta r: $r"
                        )
                    }
                }
        ) {
            for (it in 0..<model.entries.size) {
                val itSize = if (it == selectedEntry) expandedArcSize else arcSize
                val arcOffset = Offset(
                    x = (boxSize.width - itSize.width) / 2,
                    y = (boxSize.height - itSize.height) / 2,
                )

                drawArc(
                    color = model.entries[it].arcColor,
                    startAngle = angleRangeForEach[it].first,
                    sweepAngle = angleRangeForEach[it].second,
                    useCenter = true,
                    topLeft = arcOffset,
                    size = itSize,
                )
            }

            drawCircle(
                color = colorBackgroundPrimary,
                radius = centerRadius,
                center = centerArcOffset
            )
        }
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
                selectedEntry = null,
                onEntrySelected = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxHeight)
            )
        }
    }
}