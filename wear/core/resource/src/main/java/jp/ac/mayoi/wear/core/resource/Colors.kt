package jp.ac.mayoi.wear.core.resource

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.ButtonDefaults
import jp.ac.mayoi.common.resource.colorAccentCommon
import jp.ac.mayoi.common.resource.colorAccentSecondaryCommon
import jp.ac.mayoi.common.resource.colorBackgroundPrimaryDarkCommon
import jp.ac.mayoi.common.resource.colorBackgroundPrimaryLightCommon
import jp.ac.mayoi.common.resource.colorBackgroundSecondaryDarkCommon
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

// ButtonColors

val buttonColorsDefault
    @Composable get() =
        ButtonDefaults.primaryButtonColors(
            backgroundColor = colorAccent,
            contentColor = colorButtonTextPrimary
        )