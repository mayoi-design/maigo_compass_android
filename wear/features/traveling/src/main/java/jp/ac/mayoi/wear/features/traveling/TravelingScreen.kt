package jp.ac.mayoi.wear.features.traveling

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import jp.ac.mayoi.wear.core.resource.colorBlueTriangle
import jp.ac.mayoi.wear.core.resource.colorButtonTextPrimary
import jp.ac.mayoi.wear.core.resource.colorDarkBlueTriangle
import jp.ac.mayoi.wear.core.resource.colorDarkRedTriangle
import jp.ac.mayoi.wear.core.resource.colorRedTriangle
import jp.ac.mayoi.wear.core.resource.spacingTriple
import jp.ac.mayoi.wear.model.RecommendSpot
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.roundToInt

// 目的地に向かっている際のUI実装
@Composable
fun TravelingScreen(
    isHeadingDestination: Boolean,
    travelingViewModel: TravelingViewModel
) {
    LaunchedEffect(travelingViewModel) {
        travelingViewModel.updateLocation()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTravelingScreen(
            isDarkTriangleView = !isHeadingDestination,
            destination = travelingViewModel.destination,
            recommendSpot = travelingViewModel.recommendSpot,
            onRedTriangleClick = {
                travelingViewModel.focusing = travelingViewModel.destination
            },
            onBlueTriangleClick = {
                travelingViewModel.focusing = it
            }
        )

        if (isHeadingDestination) {
            val distanceInMeter = travelingViewModel.destination.distance
            val distanceInKilo = (distanceInMeter / 100.0).roundToInt() / 10.0
            TextInCircle(
                distanceText = "$distanceInKilo"
            )
        } else {
            val distanceInKilo =
                (travelingViewModel.focusing.distance / 100.0).roundToInt() / 10.0
            BestSpotTextInCircle(
                text = travelingViewModel.focusing.comment,
                distanceText = "$distanceInKilo",
            )
        }
    }
}

//旅行中画面の共通しているコードをまとめた
@Composable
fun CommonTravelingScreen(
    isDarkTriangleView: Boolean,
    destination: RecommendSpot,
    recommendSpot: ImmutableList<RecommendSpot>,
    onRedTriangleClick: () -> Unit,
    onBlueTriangleClick: (RecommendSpot) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        // 青い三角形を無数に作成する場合
        for (recommend in recommendSpot) {
            BlueTriangle(
                onClick = { onBlueTriangleClick(recommend) },
                isDarkBlueTriangleView = !isDarkTriangleView,
                recommendSpot = recommend
            )
        }
        RedTriangle(
            onClick = onRedTriangleClick,
            isDarkRedTriangleView = isDarkTriangleView,
            destination = destination
        )
    }
}

// TravelingScreenの大きい円の中のテキストの実装
@Composable
fun TextInCircle(distanceText: String) {
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
        SmallTextCircle(
            text = "目的地"
        )
        DistanceText(
            distanceTexts = distanceText,
        )
    }
}

@Composable
fun DistanceText(distanceTexts: String) {
    Column {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = distanceTexts,
                fontSize = 30.sp,
            )
            Text(
                text = "km"
            )
        }
    }
}

@Composable
fun SmallTextCircle(
    text: String,
    circleColor: Color = colorButtonTextPrimary,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 6.sp),
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(
                        color = circleColor,
                        cornerRadius = CornerRadius(32f),
                        size = Size(size.width + 10, size.height / 10),
                        style = Stroke(width = 2f),
                        topLeft = Offset(-4f, 0f)
                    )
                }
                .padding(bottom = 90.dp)
        )
    }
}

// BestSpotScreenの大きい円の中のテキストの実装
@Composable
fun BestSpotTextInCircle(
    distanceText: String,
    text: String,
) {
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
        SmallTextCircle(
            text = "おすすめスポット"
        )
        Image(
            painter = painterResource(id = R.drawable.smail),
            contentDescription = "Smail Image",
            modifier = Modifier
                .padding(bottom = spacingTriple)
                .size(35.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 35.dp)
        )
        BestSpotDistanceText(
            distanceTexts = distanceText
        )
    }
}

@Composable
fun BestSpotDistanceText(distanceTexts: String) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(top = 80.dp)
    ) {
        Text(
            text = distanceTexts,
            fontSize = 25.sp,
        )
        Text(
            text = "km",
            fontSize = 10.sp,
            modifier = Modifier.padding(bottom = 2.dp, start = 6.dp)
        )
    }
}


// 目的地の方角を指す赤い三角形の実装
@Composable
fun RedTriangle(
    onClick: () -> Unit,
    isDarkRedTriangleView: Boolean,
    destination: RecommendSpot
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .rotate(destination.bearing.toFloat())
    ) {
        val path = Path().apply {
            // 三角形の頂点を設定
            moveTo(size.width / 2, 1f)
            lineTo(163f, 40f)
            lineTo(220f, 40f)
            close()
        }
        drawPath(
            path,
            color = if (isDarkRedTriangleView) colorDarkRedTriangle else colorRedTriangle
        )
    }
}

// おすすめスポットの方角を指す青い三角形の実装
@Composable
fun BlueTriangle(
    onClick: () -> Unit,
    isDarkBlueTriangleView: Boolean,
    recommendSpot: RecommendSpot
) {
    Box {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .rotate(recommendSpot.bearing.toFloat())

        ) {
            val path = Path().apply {
                // 三角形の頂点を設定
                moveTo(size.width - 2, size.height / 2)
                lineTo(340f, 163f)
                lineTo(340f, 220f)
                close()
            }
            drawPath(
                path,
                color = if (isDarkBlueTriangleView) colorDarkBlueTriangle else colorBlueTriangle
            )
        }
    }
}