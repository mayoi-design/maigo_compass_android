package jp.ac.mayoi.wear.features.waiting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import jp.ac.mayoi.wear.core.resource.MaigoButton
import jp.ac.mayoi.wear.core.resource.appSettingButtonColors

@Composable
fun AppSettingButton() {
    MaigoButton(
        onClick = {/*設定画面に遷移される処理*/ },
        colors = appSettingButtonColors,
        modifier = Modifier
            .width(138.dp)
            .height(47.dp)
    ) {
        Text(
            text = "アプリ設定",
            fontSize = 12.sp,
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun AppSettingButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AppSettingButton()
    }

}