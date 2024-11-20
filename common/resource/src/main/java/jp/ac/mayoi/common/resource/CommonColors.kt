package jp.ac.mayoi.common.resource

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Template Colors

val colorBackgroundPrimaryLightCommon = Color(0xFFFFFFFF)
val colorBackgroundSecondaryLightCommon = Color(0xFFE0E0E0)
val colorTextMainLightCommon = Color(0xFF000000)
val colorTextCaptionCommon = Color(0xFF868686)
val colorAccentCommon = Color(0xFFFF7A7A)
val colorAccentSecondaryCommon = Color(0xFF20C0DD)
val colorBackgroundPrimaryDarkCommon = Color(0xFF000000)
val colorBackgroundSecondaryDarkCommon = Color(0xFF4A4A4A)
val colorTextMainDarkCommon = Color(0xFFF0F0F0)
val colorRedTriangleCommon = Color(0xFFDC3535)
val colorBlueTriangleCommon = Color(0xFF419AFF)
val colorDarkRedTriangleCommon = Color(0xFF581515)
val colorDarkBlueTriangleCommon = Color(0xFF1A3E66)
val colorBackgroundAppSettingButtonCommon = colorTextCaptionCommon

// ButtonColors

val buttonColorsDefaultCommon
    @Composable get() =
        ButtonColors(
            containerColor = colorAccentCommon,
            contentColor = colorTextMainDarkCommon,
            disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
            disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
        )