package jp.ac.mayoi.wear.core.resource

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonColors
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices

@Composable
fun MaigoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = buttonColorsDefault,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(32.dp),
    content: @Composable BoxScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .defaultMinSize(
                minWidth = 120.dp,
                minHeight = 60.dp
            ),
        colors = colors,
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
        ) {
            content()
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun MaigoButtonPreview() {
    Box(
        // 画面全体を埋める
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center // 中央に配置
    ) {
        MaigoButton(
            onClick = {/*押した後の処理を書く*/ },
        ) {
            Text(
                text = "ボタン",
            )
        }
    }
}