package jp.ac.mayoi.wear.core.resource

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonColors
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices


@Composable
fun MaigoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.primaryButtonColors(),
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    content: @Composable BoxScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = colors,
        shape = shape
    ) {
        content()
    }
}

@Composable
fun MaigoButtonStyle() {
    Box(
        modifier = Modifier
            .fillMaxSize(),  // 画面全体を埋める
        contentAlignment = Alignment.Center // 中央に配置
    ) {
        MaigoButton(
            onClick = {/*押した後の処理を書く*/ },
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.width(100.dp)
        ) {
            Text(
                text = "ボタン",
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun MaigoButtonPreview() {
    MaigoButtonStyle()
}

