package jp.ac.mayoi.memory.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorAccentSecondary
import jp.ac.mayoi.core.resource.colorBackgroundPrimary
import jp.ac.mayoi.core.resource.colorGraphHakodateBay
import jp.ac.mayoi.core.resource.colorGraphHakodateSt
import kotlin.math.min

val arcOuterPadding = 64.dp

@Composable
internal fun MemoryGraph(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
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

        drawArc(
            color = colorGraphHakodateSt,
            startAngle = -90f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = arcOffset,
            size = arcSize
        )

        drawArc(
            color = colorGraphHakodateBay,
            startAngle = 0f,
            sweepAngle = 85f,
            useCenter = true,
            topLeft = arcOffset,
            size = arcSize
        )

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

            MemoryGraph(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxHeight)
            )

            VerticalDivider(
                color = colorAccentSecondary,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .height(56.dp)
            )
        }
    }
}