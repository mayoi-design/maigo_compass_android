package jp.ac.mayoi.wear.features.traveling

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import jp.ac.mayoi.wear.core.resource.fontSizeCaption
import jp.ac.mayoi.wear.core.resource.fontSizeTitle
import jp.ac.mayoi.wear.core.resource.spacingDouble
import jp.ac.mayoi.wear.core.resource.spacingSingle
import jp.ac.mayoi.wear.core.resource.spacingTriple
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

// 目的地に向かっている際のUI実装
@Composable
fun TravelingScreen(
    isDarkRedTriangle: Boolean
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTravelingScreen(
            onRedTriangleClick = {/**/ },
            onBlueTriangleClick = {/**/ },
            isDarkTriangleView = isDarkRedTriangle
        )
        if (!isDarkRedTriangle) {
            TextInCircle(
                distanceText = "",
                text = "目的地"
            )
        } else {
            BestSpotTextInCircle(
                distanceText = "",
                text = "おすすめスポット",
                fontSize = 6,
            )
        }
    }
}

//旅行中画面の共通しているコードをまとめた
@Composable
fun CommonTravelingScreen(
    onBlueTriangleClick: () -> Unit,
    onRedTriangleClick: () -> Unit,
    isDarkTriangleView: Boolean,
) {
    val items = remember { mutableStateListOf<Int>() }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 青い三角形を無数に作成する場合
        for (recommend in items) {
                BlueTriangle(
                    onClick = { onBlueTriangleClick() },
                    isDarkBlueTriangleView = !isDarkTriangleView
                )
            }
        RedTriangle(
            onClick = onRedTriangleClick,
            isDarkRedTriangleView = isDarkTriangleView
        )
    }
}

// TravelingScreenの大きい円の中のテキストの実装
@Composable
fun TextInCircle(
    distanceText: String,
    text: String,
    textColor: Color = colorTextMain,
) {
    val distances: ImmutableList<String> by remember {
        mutableStateOf(persistentListOf("1.0", "2.0", "3.0"))
    }// 一旦これで表示
    val paint = Paint().apply {
        strokeWidth = 2f
        isAntiAlias = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawCircle(
                    color = colorButtonTextPrimary,
                    radius = 140f,
                    center = Offset(190f, 190f),
                    style = Stroke(paint.strokeWidth)
                )
            }
    ) {
        SmallTextCircle(text)
        DistanceText(
            distanceTexts = distances,
        )
        Text(
            text = "km",
            color = textColor,
            modifier = Modifier
                .padding(start = 50.dp, top = spacingDouble)
        )
    }
}

@Composable
fun DistanceText(distanceTexts: ImmutableList<String>) {
    val distanceText =
        remember { mutableStateOf(distanceTexts.firstOrNull() ?: "0.0") }
    Text(
        text = distanceText.value,
        fontSize = 30.sp,
        modifier = Modifier.padding(
            end = spacingTriple,
            top = spacingSingle
        )
    )
}

@Composable
fun SmallTextCircle(
    text: String,
    textColor: Color = colorTextMain,
    circleColor: Color = colorButtonTextPrimary,
) {
    Text(
        text = text,
        style = TextStyle(fontSize = 6.sp),
        color = textColor,
        modifier = Modifier
            .drawBehind {
                drawRoundRect(
                    color = circleColor,
                    cornerRadius = CornerRadius(32f),
                    size = Size(size.width + 10, size.height / 10),
                    style = Stroke(width = 2f),
                    topLeft = Offset(0f, 0f)
                )
            }
            .padding(bottom = 90.dp, start = 4.dp)
    )
}

// BestSpotScreenの大きい円の中のテキストの実装
@Composable
fun BestSpotTextInCircle(
    distanceText: String,
    text: String,
    fontSize: Int,
    textColor: Color = colorTextMain,
) {
    val distances: ImmutableList<String> by remember {
        mutableStateOf(persistentListOf("1.0", "2.0", "3.0"))
    }// 一旦これで表示
    val paint = Paint().apply {
        strokeWidth = 2f
        isAntiAlias = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawCircle(
                    color = colorButtonTextPrimary,
                    radius = 140f,
                    center = Offset(190f, 190f),
                    style = Stroke(paint.strokeWidth)
                )
            }
    ) {
        SmallTextCircle(text)
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
        BestSpotDistanceText(
            distanceTexts = distances
        )
        Text(
            text = "km",
            fontSize = fontSizeCaption,
            color = textColor,
            modifier = Modifier
                .padding(start = 30.dp, top = 90.dp)
        )
    }
}

@Composable
fun BestSpotDistanceText(distanceTexts: ImmutableList<String>) {
    val distanceText =
        remember { mutableStateOf(distanceTexts.firstOrNull() ?: "0.0") }
    Text(
        text = distanceText.value,
        fontSize = fontSizeTitle,
        modifier = Modifier.padding(end = spacingTriple, top = 85.dp)
    )
}

// 目的地の方角を指す赤い三角形の実装
@Composable
fun RedTriangle(
    onClick: () -> Unit,
    isDarkRedTriangleView: Boolean
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

// おすすめスポットの方角を指す青い三角形の実装
@Composable
fun BlueTriangle(
    onClick: () -> Unit,
    isDarkBlueTriangleView: Boolean
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

// 目的地を指す際のPreview
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun TravelingScreenPreview() {
    TravelingScreen(
        isDarkRedTriangle = false
    )
}

// おすすめスポットを指す際のPreview
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun TravelingScreenBestPreview() {
    TravelingScreen(
        isDarkRedTriangle = true
    )
}