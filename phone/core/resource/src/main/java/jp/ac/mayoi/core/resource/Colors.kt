package jp.ac.mayoi.core.resource

import androidx.compose.material3.NavigationBarItemColors
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

// Memory Pie Graph
val colorGraphBackground = Color(0xFFA1ECFD)
val colorGraphHakodateSt = Color(0xFFFF3A28)
val colorGraphHakodateBay = Color(0xFFB55233)
val colorGraphMotomachi = Color(0xFFF8F36A)
val colorGraphGoryokaku = Color(0xFFFFBCE4)
val colorGraphYunokawa = Color(0xFF8EB4FC)
val colorGraphMihara = Color(0xFFFFBD80)

// ButtonColors

val buttonColorsDefault
    @Composable get() = buttonColorsDefaultCommon

val maigoNavigationBarItemColors = NavigationBarItemColors(
    selectedIconColor = colorAccent,
    selectedTextColor = colorAccent,
    selectedIndicatorColor = Color.Transparent,
    unselectedIconColor = colorTextCaption,
    unselectedTextColor = colorTextCaption,
    disabledIconColor = colorTextCaption,
    disabledTextColor = colorTextCaption
)