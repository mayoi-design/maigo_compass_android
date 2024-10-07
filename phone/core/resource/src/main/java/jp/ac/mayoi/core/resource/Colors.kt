package jp.ac.mayoi.core.resource

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import jp.ac.mayoi.common.resource.buttonColorsDefaultCommon
import jp.ac.mayoi.common.resource.colorAccentCommon
import jp.ac.mayoi.common.resource.colorAccentSecondaryCommon
import jp.ac.mayoi.common.resource.colorBackgroundPrimaryLightCommon
import jp.ac.mayoi.common.resource.colorBackgroundSecondaryLightCommon
import jp.ac.mayoi.common.resource.colorTextCaptionCommon
import jp.ac.mayoi.common.resource.colorTextMainLightCommon

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val colorBackgroundPrimary = colorBackgroundPrimaryLightCommon
val colorBackgroundSecondary = colorBackgroundSecondaryLightCommon
val colorTextMain = colorTextMainLightCommon
val colorTextCaption = colorTextCaptionCommon
val colorAccent = colorAccentCommon
val colorAccentSecondary = colorAccentSecondaryCommon

// ButtonColors

val buttonColorsDefault
    @Composable get() = buttonColorsDefaultCommon