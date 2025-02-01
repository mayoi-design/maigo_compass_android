package jp.ac.mayoi.core.resource

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import jp.ac.mayoi.common.resource.textStyleBodyCommon
import jp.ac.mayoi.common.resource.textStyleCaptionCommon
import jp.ac.mayoi.common.resource.textStyleTitleCommon

val textStyleTitle = textStyleTitleCommon
val textStyleBody = textStyleBodyCommon
val textStyleCaption = textStyleCaptionCommon

/*
 * Memory Pie Chart
 */

val textStyleMemoryPieChartCenter = textStyleTitle.copy(
    color = colorTextMain,
    fontSize = 64.sp,
)

val textStyleMemoryPieChartCenterSubText = textStyleTitle.copy(
    color = colorTextMain,
    fontSize = 28.sp,
)

val textStyleMemoryPieChartLabel = TextStyle(
    color = colorTextMain,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.Normal,
)

val textStyleMemoryPieChartCaption = TextStyle(
    color = colorTextMain,
    fontSize = 28.sp,
    lineHeight = 32.sp,
    fontWeight = FontWeight.Normal,
)

val textStyleMemoryPieChartCaptionSubText = TextStyle(
    color = colorTextMain,
    fontSize = 16.sp,
    lineHeight = 20.sp,
    fontWeight = FontWeight.Normal,
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)