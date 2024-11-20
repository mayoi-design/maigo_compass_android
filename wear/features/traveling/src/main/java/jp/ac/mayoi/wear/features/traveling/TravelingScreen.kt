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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import jp.ac.mayoi.wear.core.resource.fontSizeTitle
import jp.ac.mayoi.wear.core.resource.spacingTriple

// 目的地に向かっている際のUI実装
@Composable
fun TravelingScreen(
    isHeadingDestination: Boolean,
    travelingViewModel: TravelingViewModel
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTravelingScreen(
            onRedTriangleClick = {/**/ },
            onBlueTriangleClick = {/**/ },
            isDarkTriangleView = !isHeadingDestination,
            travelingViewModel = travelingViewModel
        )
        if (isHeadingDestination) {
            TextInCircle(
                distanceText = "",
                headingTo = travelingViewModel.headingTo,
                bearing = travelingViewModel.destination.bearing
            )
        } else {
            BestSpotTextInCircle(
                text = "きれいな海が見える！",
                distanceText = "",
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
    travelingViewModel: TravelingViewModel
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
            isDarkRedTriangleView = isDarkTriangleView,
            viewModel = travelingViewModel,

        )
    }
}

// TravelingScreenの大きい円の中のテキストの実装
@Composable
fun TextInCircle(distanceText: String, headingTo: Double, bearing: Double) {
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
            headingTo = headingTo,
            bearing = bearing,
        )
    }
}

@Composable
fun DistanceText(distanceTexts: String, headingTo: Double, bearing: Double) {
    Column {
        Text(
            "headingTo = $headingTo\nbearing = $bearing"
        )
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
            fontSize = 10.sp,
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
            fontSize = fontSizeTitle,
        )
        Text(
            text = "km",
            fontSize = 10.sp,
        )
    }
}

// 目的地の方角を指す赤い三角形の実装
@Composable
fun RedTriangle(
    onClick: () -> Unit,
    isDarkRedTriangleView: Boolean,
    viewModel: TravelingViewModel
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .rotate(viewModel.destination.bearing.toFloat())
    ) {
        val destination = viewModel.destination
        val radius = 5f

        val path = Path().apply {
            // 三角形の頂点を設定
            moveTo(size.width / 2, 1f)
            lineTo(163f, 40f)
            lineTo(220f, 40f)
            close()
//            moveTo(size.width / 2  + radius*(destination.bearing).toFloat()-100f, 1f  + radius*(destination.bearing).toFloat()-100f)
//            lineTo(163f + radius*(destination.bearing).toFloat()-100f, 40f  + radius*(destination.bearing).toFloat()-100f)
//            lineTo(220f  + radius*(destination.bearing).toFloat()-100f, 40f + radius*(destination.bearing).toFloat()-100f)
//            close()
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
    isDarkBlueTriangleView: Boolean
) {
    Canvas(
        modifier = Modifier.fillMaxSize()
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

// 目的地を指す際のPreview
//@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
//@Composable
//private fun TravelingScreenPreview() {
//    TravelingScreen(
//        isHeadingDestination = true,
//        travelingViewModel =
//    )
//}
//
//// おすすめスポットを指す際のPreview
//@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
//@Composable
//private fun TravelingScreenBestPreview() {
//    TravelingScreen(
//        isHeadingDestination = false,
//        travelingViewModel =
//    )
//}