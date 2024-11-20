package jp.ac.mayoi.wear.core.resource

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.ButtonDefaults
import jp.ac.mayoi.common.resource.colorAccentCommon
import jp.ac.mayoi.common.resource.colorAccentSecondaryCommon
import jp.ac.mayoi.common.resource.colorBackgroundAppSettingButtonCommon
import jp.ac.mayoi.common.resource.colorBackgroundPrimaryDarkCommon
import jp.ac.mayoi.common.resource.colorBackgroundPrimaryLightCommon
import jp.ac.mayoi.common.resource.colorBackgroundSecondaryDarkCommon
import jp.ac.mayoi.common.resource.colorBlueTriangleCommon
import jp.ac.mayoi.common.resource.colorDarkBlueTriangleCommon
import jp.ac.mayoi.common.resource.colorDarkRedTriangleCommon
import jp.ac.mayoi.common.resource.colorRedTriangleCommon
import jp.ac.mayoi.common.resource.colorTextCaptionCommon
import jp.ac.mayoi.common.resource.colorTextMainDarkCommon

// Template Colors

val colorBackgroundPrimary = colorBackgroundPrimaryDarkCommon
val colorBackgroundSecondary = colorBackgroundSecondaryDarkCommon
val colorTextMain = colorTextMainDarkCommon
val colorTextCaption = colorTextCaptionCommon
val colorAccent = colorAccentCommon
val colorAccentSecondary = colorAccentSecondaryCommon
val colorButtonTextPrimary = colorBackgroundPrimaryLightCommon
val colorBackgroundAppSettingButton = colorBackgroundAppSettingButtonCommon
val colorRedTriangle = colorRedTriangleCommon
val colorBlueTriangle = colorBlueTriangleCommon
val colorDarkRedTriangle = colorDarkRedTriangleCommon
val colorDarkBlueTriangle = colorDarkBlueTriangleCommon

// ButtonColors

val buttonColorsDefault
    @Composable get() =
        ButtonDefaults.primaryButtonColors(
            backgroundColor = colorAccent,
            contentColor = colorButtonTextPrimary
        )
val appSettingButtonColors
    @Composable get() =
        ButtonDefaults.primaryButtonColors(
            backgroundColor = colorBackgroundAppSettingButton,
            contentColor = colorButtonTextPrimary
        )
