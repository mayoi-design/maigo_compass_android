package jp.ac.mayoi.wear.features.traveling

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import jp.ac.mayoi.wear.core.resource.colorBlueTriangle
import jp.ac.mayoi.wear.core.resource.colorButtonTextPrimary
import jp.ac.mayoi.wear.core.resource.colorDarkBlueTriangle
import jp.ac.mayoi.wear.core.resource.colorDarkRedTriangle
import jp.ac.mayoi.wear.core.resource.colorRedTriangle
import jp.ac.mayoi.wear.core.resource.spacingDouble
import jp.ac.mayoi.wear.core.resource.spacingHalf
import jp.ac.mayoi.wear.core.resource.spacingSingle
import jp.ac.mayoi.wear.core.resource.spacingTriple
import jp.ac.mayoi.wear.model.RecommendSpot
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

// 目的地に向かっている際のUI実装
@Composable
fun TravelingScreen(
    travelingViewModel: TravelingViewModel
) {
    val isHeadingDestination by remember {
        derivedStateOf {
            travelingViewModel.destination.lat == travelingViewModel.focusing.lat &&
                    travelingViewModel.destination.lng == travelingViewModel.focusing.lng
        }
    }

    val distanceInMeter = if (isHeadingDestination) {
        travelingViewModel.destination.distance
    } else {
        val focusing = travelingViewModel.focusing
        travelingViewModel.recommendSpot.find { spot ->
            spot.lat == focusing.lat && spot.lng == focusing.lng
        }?.distance ?: Double.POSITIVE_INFINITY
    }
    val distanceInKilo = (distanceInMeter / 100.0).roundToInt() / 10.0

    TravelingScreen(
        destination = travelingViewModel.destination,
        recommendSpot = travelingViewModel.recommendSpot,
        focusing = travelingViewModel.focusing,
        isHeadingDestination = isHeadingDestination,
        distanceToFocus = distanceInKilo,
        onRedTriangleClick = {
            travelingViewModel.focusing = travelingViewModel.destination
        },
        onBlueTriangleClick = { spot ->
            travelingViewModel.focusing = spot
        }
    )
}

@Composable
private fun TravelingScreen(
    destination: RecommendSpot,
    recommendSpot: ImmutableList<RecommendSpot>,
    focusing: RecommendSpot,
    isHeadingDestination: Boolean,
    distanceToFocus: Double,
    onRedTriangleClick: () -> Unit,
    onBlueTriangleClick: (RecommendSpot) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTravelingScreen(
            isHeadingDestination = isHeadingDestination,
            destination = destination,
            recommendSpot = recommendSpot,
            focusing = focusing,
            onRedTriangleClick = onRedTriangleClick,
            onBlueTriangleClick = onBlueTriangleClick,
        )

        if (isHeadingDestination) {
            TextInCircle(
                distanceText = "$distanceToFocus"
            )
        } else {
            BestSpotTextInCircle(
                text = focusing.comment,
                distanceText = "$distanceToFocus",
            )
        }
    }
}

//旅行中画面の共通しているコードをまとめた
@Composable
private fun CommonTravelingScreen(
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

@Composable
private fun CenterCircle(
    circleRadius: Dp,
    screenWidth: Dp,
    content: @Composable (BoxScope.() -> Unit),
) {
    val widthInPx = with(LocalDensity.current) { screenWidth.toPx() }
    val radiusInPx = with(LocalDensity.current) { circleRadius.toPx() }
    Box(
        contentAlignment = Alignment.Center,
        content = content,
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawCircle(
                    color = colorButtonTextPrimary,
                    radius = radiusInPx,
                    center = Offset(widthInPx / 2, widthInPx / 2),
                    style = Stroke(width = 2f)
                )
            }
            .padding(26.dp)
    )
}

// TravelingScreenの大きい円の中のテキストの実装
@Composable
private fun TextInCircle(distanceText: String) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp // As same as height
    val circleRadius = (screenWidth - 2 * (24.dp + 2.dp)) / 2
    CenterCircle(
        screenWidth = screenWidth,
        circleRadius = circleRadius,
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = spacingTriple)
        ) {
            SmallTextCircle(
                text = "目的地"
            )
        }
        DistanceText(
            distanceTexts = distanceText,
        )
    }
}

@Composable
private fun DistanceText(
    distanceTexts: String,
    fontSize: TextUnit = 30.sp,
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = distanceTexts,
            fontSize = fontSize,
        )
        Text(
            text = "km",
            modifier = Modifier.padding(bottom = 3.dp, start = 6.dp)
        )
    }
}

@Composable
private fun SmallTextCircle(
    text: String,
    circleColor: Color = colorButtonTextPrimary,
) {
    var textWidth by remember { mutableFloatStateOf(0f) }
    var textHeight by remember { mutableFloatStateOf(0f) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 8.sp),
            modifier = Modifier
                .onGloballyPositioned { layoutCoordinates ->
                    textHeight = layoutCoordinates.size.height.toFloat()
                    textWidth = layoutCoordinates.size.width.toFloat()
                }
                .drawBehind {
                    drawRoundRect(
                        color = circleColor,
                        cornerRadius = CornerRadius(32f),
                        size = Size(textWidth + 24f, textHeight + 8f),
                        style = Stroke(width = 2f),
                        topLeft = Offset(-12f, -4f)
                    )
                }
        )
    }
}

// BestSpotScreenの大きい円の中のテキストの実装
@Composable
private fun BestSpotTextInCircle(
    distanceText: String,
    text: String,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val circleRadius = (screenWidth - 2 * (24.dp + 2.dp)) / 2
    CenterCircle(
        screenWidth = screenWidth,
        circleRadius = circleRadius,
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = spacingDouble)
        ) {
            SmallTextCircle(
                text = "おすすめスポット"
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.smail),
                contentDescription = "Smail Image",
                modifier = Modifier
                    .size(35.dp)
            )

            Spacer(modifier = Modifier.size(spacingHalf))

            Text(
                text = text,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacingSingle + spacingHalf)
        ) {
            DistanceText(
                distanceTexts = distanceText,
                fontSize = 25.sp
            )
        }
    }
}

@Composable
private fun TriangleCore(
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
private fun RedTriangle(
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
private fun BlueTriangle(
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

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
private fun TextInCirclePreview() {
    MaterialTheme {
        TextInCircle(
            distanceText = "12.3"
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
private fun BestSpotInCirclePreview() {
    MaterialTheme {
        BestSpotTextInCircle(
            distanceText = "12.3",
            text = "あ".repeat(10)
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
private fun TravelingScreenHeadingDestinationPreview() {
    MaterialTheme {
        val destination = RecommendSpot(
            lat = 0.0,
            lng = 0.0,
            comment = "",
            distance = 12.3,
            bearing = 15.0,
        )
        val spots = List(6) {
            RecommendSpot(
                lat = it + 1.0,
                lng = it + 1.0,
                comment = "Spot ${it + 1}",
                distance = 12.3 * (it + 2),
                bearing = 30.0 * it
            )
        }.toImmutableList()
        TravelingScreen(
            destination = destination,
            recommendSpot = spots,
            focusing = destination,
            isHeadingDestination = true,
            distanceToFocus = destination.distance,
            onBlueTriangleClick = {},
            onRedTriangleClick = {},
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
private fun TravelingScreenHeadingSpotPreview() {
    MaterialTheme {
        val destination = RecommendSpot(
            lat = 0.0,
            lng = 0.0,
            comment = "",
            distance = 12.3,
            bearing = 15.0,
        )
        val spots = List(6) {
            RecommendSpot(
                lat = it + 1.0,
                lng = it + 1.0,
                comment = "Spot ${it + 1}",
                distance = 6.2 * (it + 2),
                bearing = 30.0 * it
            )
        }.toImmutableList()
        TravelingScreen(
            destination = destination,
            recommendSpot = spots,
            focusing = spots[1],
            isHeadingDestination = false,
            distanceToFocus = spots[1].distance,
            onBlueTriangleClick = {},
            onRedTriangleClick = {},
        )
    }
}