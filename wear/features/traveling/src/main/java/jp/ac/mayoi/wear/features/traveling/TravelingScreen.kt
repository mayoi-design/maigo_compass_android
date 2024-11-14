package jp.ac.mayoi.wear.features.traveling

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import jp.ac.mayoi.wear.core.resource.colorBlueTriangle
import jp.ac.mayoi.wear.core.resource.colorButtonTextPrimary
import jp.ac.mayoi.wear.core.resource.colorDarkTriangle
import jp.ac.mayoi.wear.core.resource.colorRedTriangle
import jp.ac.mayoi.wear.core.resource.colorTextMain
import jp.ac.mayoi.wear.core.resource.spacingHalf
import jp.ac.mayoi.wear.core.resource.spacingSingle
import jp.ac.mayoi.wear.core.resource.spacingTriple

// 目的地に向かっている祭のUI実装
@Composable
fun TravelingScreen(
    onBlueTriangleClick: () -> Unit,
    onDarkBlueTriangleClick: () -> Unit,
    onRedTriangleClick: () -> Unit,
    isDarkBlueTriangleView: Boolean
) {
    val paint = Paint().apply {
        strokeWidth = 2f
        isAntiAlias = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        BlueTriangle(
            onClick = onBlueTriangleClick
        )
        DarkBlueTriangle(
            onClick = onDarkBlueTriangleClick,
            isDarkBlueTriangleView = isDarkBlueTriangleView
        )
        RedTriangle(
            onClick = onRedTriangleClick
        )
        TextInCircle(
            text = "目的地",
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(
                color = colorButtonTextPrimary,
                radius = 140f,
                center = Offset(190f, 190f),
                style = Stroke(paint.strokeWidth)
            )
        }
    }
}

// おすすめスポットに向かっているときのUI実装
@Composable
fun BestSpotScreen(
    onBlueTriangleClick: () -> Unit,
    onDarkRedTriangleClick: () -> Unit,
    onRedTriangleClick: () -> Unit,
    isDarkRedTriangleView: Boolean,
) {
    val paint = Paint().apply {
        strokeWidth = 2f
        isAntiAlias = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        BlueTriangle(
            onClick = onBlueTriangleClick
        )
        RedTriangle(
            onClick = onRedTriangleClick
        )
        DarkRedTriangle(
            onClick = onDarkRedTriangleClick,
            isDarkRedTriangleView = isDarkRedTriangleView
        )
        BestSpotTextInCircle(
            text = "おすすめスポット",
            fontSize = 6,
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(
                color = colorButtonTextPrimary,
                radius = 140f,
                center = Offset(190f, 190f),
                style = Stroke(paint.strokeWidth)
            )
        }
    }
}

// TravelingScreenの大きい円の中のテキストの実装
@Composable
fun TextInCircle(
    text: String,
    fontSize: Int = 6,
    textColor: Color = colorTextMain,
    circleColor: Color = colorButtonTextPrimary,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.padding(bottom = 105.dp, end = 50.dp)
        ) {
            drawRoundRect(
                color = circleColor,
                size = Size(105f, 30f),
                cornerRadius = CornerRadius(32f),
                style = Stroke(width = 2f)
            )
        }
        Text(
            text = text,
            fontSize = fontSize.sp,
            color = textColor,
            modifier = Modifier.padding(bottom = 90.dp, start = spacingHalf / 2)
        )
        Text(
            text = "3.5",
            fontSize = 30.sp,
            modifier = Modifier.padding(
                end = spacingTriple,
                top = spacingSingle
            )
        )
        Text(
            text = "km",
            color = textColor,
            modifier = Modifier
                .padding(start = 50.dp, top = 18.dp)

        )
    }
}

// BestSpotScreenの大きい円の中のテキストの実装
@Composable
fun BestSpotTextInCircle(
    text: String,
    fontSize: Int,
    textColor: Color = colorTextMain,
    circleColor: Color = colorButtonTextPrimary,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(
            modifier = Modifier.padding(bottom = 105.dp, end = 50.dp)
        ) {
            drawRoundRect(
                color = circleColor,
                size = Size(105f, 30f),
                cornerRadius = CornerRadius(32f),
                style = Stroke(width = 2f)
            )
        }
        Text(
            text = text,
            fontSize = fontSize.sp,
            color = textColor,
            modifier = Modifier.padding(bottom = 90.dp, start = spacingHalf / 2)
        )
        Image(
            painter = painterResource(id = R.drawable.smail),
            contentDescription = "Smail Image",
            modifier = Modifier
                .padding(bottom = spacingTriple)
                .size(35.dp)
        )
        Text(
            text = "きれいな海が見える！",
            fontSize = 10.sp,
            color = textColor,
            modifier = Modifier
                .padding(top = 35.dp)
        )
        Text(
            text = "1.8",
            fontSize = 20.sp,
            modifier = Modifier.padding(end = spacingTriple, top = 85.dp)
        )
        Text(
            text = "km",
            fontSize = 10.sp,
            color = textColor,
            modifier = Modifier
                .padding(start = 30.dp, top = 90.dp)

        )
    }
}

// 目的地の方角を指す赤い三角形の実装
@Composable
fun RedTriangle(
    onClick: () -> Unit,
) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val path = Path().apply {
            // 三角形の頂点を設定
            moveTo(size.width / 2, 1f)
            lineTo(159f, 43f)
            lineTo(222f, 43f)
            close()
        }
        drawPath(
            path,
            color = colorRedTriangle,
        )
    }
}

// おすすめスポットの方角を指す青い三角形の実装
@Composable
fun BlueTriangle(
    onClick: () -> Unit
) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val path = Path().apply {
            // 三角形の頂点を設定
            moveTo(size.width - 2, size.height / 2)
            lineTo(337f, 159f)
            lineTo(337f, 222f)
            close()
        }
        drawPath(
            path,
            color = colorBlueTriangle
        )
    }
}

// 青い三角形を暗くしたいときに使う
@Composable
fun DarkBlueTriangle(
    onClick: () -> Unit,
    isDarkBlueTriangleView: Boolean
) {
    if (isDarkBlueTriangleView) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val path = Path().apply {
                // 三角形の頂点を設定
                moveTo(size.width - 2, size.height / 2)
                lineTo(337f, 159f)
                lineTo(337f, 222f)
                close()
            }
            drawPath(
                path,
                color = colorDarkTriangle,
            )
        }
    }
}

// 赤い三角形を暗くしたいときに使う
@Composable
fun DarkRedTriangle(
    onClick: () -> Unit,
    isDarkRedTriangleView: Boolean
) {
    if (isDarkRedTriangleView) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val path = Path().apply {
                // 三角形の頂点を設定
                moveTo(size.width / 2, 1f)
                lineTo(159f, 43f)
                lineTo(222f, 43f)
                close()
            }
            drawPath(
                path,
                color = colorDarkTriangle,
            )
        }
    }
}

// 目的地を指す際のPreview
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun TravelingScreenPreview() {
    TravelingScreen(
        onBlueTriangleClick = {/**/ },
        onDarkBlueTriangleClick = {/**/ },
        onRedTriangleClick = {/**/ },
        isDarkBlueTriangleView = true,
    )
}

// おすすめスポットを指す際のPreview
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun TravelingScreenBestPreview() {
    BestSpotScreen(
        onBlueTriangleClick = {/**/ },
        onDarkRedTriangleClick = {/**/ },
        onRedTriangleClick = {/**/ },
        isDarkRedTriangleView = true
    )
}


