package jp.ac.mayoi.wear.features.traveling

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import jp.ac.mayoi.wear.core.resource.colorBlueTriangle
import jp.ac.mayoi.wear.core.resource.colorButtonTextPrimary
import jp.ac.mayoi.wear.core.resource.colorDarkBlueTriangle
import jp.ac.mayoi.wear.core.resource.colorDarkRedTriangle
import jp.ac.mayoi.wear.core.resource.colorRedTriangle
import jp.ac.mayoi.wear.core.resource.spacingHalf
import jp.ac.mayoi.wear.core.resource.spacingTriple
import jp.ac.mayoi.wear.model.RecommendSpot
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

// 目的地に向かっている際のUI実装
@Composable
fun TravelingScreen(
    travelingViewModel: TravelingViewModel
) {
    LaunchedEffect(travelingViewModel) {
        travelingViewModel.updateLocation()
    }
    val isHeadingDestination by remember {
        derivedStateOf {
            travelingViewModel.destination.lat == travelingViewModel.focusing.lat &&
                    travelingViewModel.destination.lng == travelingViewModel.focusing.lng
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTravelingScreen(
            isHeadingDestination = isHeadingDestination,
            destination = travelingViewModel.destination,
            recommendSpot = travelingViewModel.recommendSpot,
            focusing = travelingViewModel.focusing,
            onRedTriangleClick = {
                travelingViewModel.focusing = travelingViewModel.destination
            },
            onBlueTriangleClick = { recommendSpot ->
                travelingViewModel.focusing = recommendSpot
            }
        )
        val distanceInMeter = travelingViewModel.focusing.distance
        val distanceInKilo = (distanceInMeter / 100.0).roundToInt() / 10.0
        if (isHeadingDestination) {
            TextInCircle(
                distanceText = "$distanceInKilo"
            )
        } else {
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
    isHeadingDestination: Boolean,
    destination: RecommendSpot,
    focusing: RecommendSpot,
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
            val isSameAsFocus =
                recommend.lat == focusing.lat && recommend.lng == focusing.lng
            val isLightBlueTriangle = !isHeadingDestination && isSameAsFocus
            BlueTriangle(
                onClick = { onBlueTriangleClick(recommend) },
                isDarkBlueTriangleView = !isLightBlueTriangle,
                recommendSpot = recommend
            )
        }
        RedTriangle(
            onClick = onRedTriangleClick,
            isDarkRedTriangleView = !isHeadingDestination,
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

@Composable
fun TriangleCore(
    rotation: Float,
    color: Color,
    onClick: () -> Unit,
) {
    val density = LocalDensity.current
    val rotationInRad = Math.toRadians(rotation.toDouble())
    with(density) {
        val canvasSize = 24.dp
        val screenHeightInPx = LocalConfiguration.current.screenHeightDp.dp.toPx()
        val r = screenHeightInPx / 2 - (canvasSize / 2 + spacingHalf).toPx()

        // 画面の真上が現在の目線 → rotationが0 なので、位置合わせのために90°だけ座標系を回して計算する
        val offsetYInPx = r * -sin(rotationInRad + Math.PI / 2f).toFloat()
        val offsetXInPx = r * cos(rotationInRad + Math.PI / 2f).toFloat()
        Canvas(
            modifier = Modifier
                .size(canvasSize)
                .offset(x = offsetXInPx.toDp(), y = offsetYInPx.toDp())
                .clickable { onClick() }
        ) {
            val shrink = 16f
            val path = Path().apply {
                // 三角形の頂点を設定
                moveTo(size.width / 2, 0f)
                lineTo(0f, size.height - shrink)
                lineTo(size.width, size.height - shrink)
                moveTo(size.width / 2f, size.height / 2f)
                close()
            }

            // DrawScopeはrotateの方向が時計回りなので、
            // 反時計回りの回転角から時計回りの回転角に直す
            val rotationInDrawScope = 360 - rotation
            rotate(
                degrees = rotationInDrawScope,
                block = {
                    drawPath(
                        path = path,
                        color = color,
                    )
                }
            )
        }
    }
}

// 目的地の方角を指す赤い三角形の実装
@Composable
fun RedTriangle(
    onClick: () -> Unit,
    isDarkRedTriangleView: Boolean,
    destination: RecommendSpot,
) {
    TriangleCore(
        rotation = destination.bearing.toFloat(),
        color = when (isDarkRedTriangleView) {
            true -> colorDarkRedTriangle
            else -> colorRedTriangle
        },
        onClick = onClick,
    )
}

// おすすめスポットの方角を指す青い三角形の実装
@Composable
fun BlueTriangle(
    onClick: () -> Unit,
    isDarkBlueTriangleView: Boolean,
    recommendSpot: RecommendSpot
) {
    TriangleCore(
        rotation = recommendSpot.bearing.toFloat(),
        color = when (isDarkBlueTriangleView) {
            true -> colorDarkBlueTriangle
            else -> colorBlueTriangle
        },
        onClick = onClick,
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun RedTrianglePreview() {
    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalDivider()
            VerticalDivider()
            RedTriangle(
                onClick = {},
                isDarkRedTriangleView = false,
                destination = RecommendSpot(
                    lat = 0.0,
                    lng = 0.0,
                    comment = "",
                    distance = 0.0,
                    bearing = 330.0
                )
            )
        }
    }
}

// 左上に赤い矢印があるべき
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun RedTriangle45Preview() {
    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalDivider()
            VerticalDivider()
            RedTriangle(
                onClick = {},
                isDarkRedTriangleView = false,
                destination = RecommendSpot(
                    lat = 0.0,
                    lng = 0.0,
                    comment = "",
                    distance = 0.0,
                    bearing = 45.0,
                )
            )
        }
    }
}

// 西南西に矢印がある
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun BlueTrianglePreview() {
    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalDivider()
            VerticalDivider()
            BlueTriangle(
                onClick = {},
                isDarkBlueTriangleView = false,
                recommendSpot = RecommendSpot(
                    lat = 0.0,
                    lng = 0.0,
                    comment = "",
                    distance = 0.0,
                    bearing = 250.0
                )
            )
        }
    }
}