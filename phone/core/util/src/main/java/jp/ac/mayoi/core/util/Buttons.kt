package jp.ac.mayoi.core.util

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MaigoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(), //後からアプリ側で設定したやつに置き換える
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        colors = colors,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .defaultMinSize(minWidth = 120.dp, minHeight = 60.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(40.dp),
            ),
        enabled = enabled,
        shape = RoundedCornerShape(40.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 16.dp), // elevationを強調
        content = content,
    )
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    MaigoButton(
        onClick = { },
        content = {
            Text("ボタン")
        }
    )
}