package jp.ac.mayoi.wear.features.waiting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import jp.ac.mayoi.common.resource.colorBackgroundPrimaryLightCommon
import jp.ac.mayoi.wear.core.resource.MaigoButton

@Composable
fun WaitingScreen() {
    var isButtonView by remember { mutableStateOf(true) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = "目的地を設定\nしてみましょう",
            fontSize = 13.sp,
            color = colorBackgroundPrimaryLightCommon,
            textAlign = TextAlign.Center
        )
        if (isButtonView) {
            MaigoButton(
                modifier = Modifier
                    .padding(top = 95.dp)
                    .height(47.dp)
                    .width(138.dp),
                onClick = {
                    isButtonView = false
                }
            ) {
                Text(
                    text = "目的地を設定",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center

                )
            }
        } else {
            Text(
                text = "スマートフォンの操作を\n待機しています…",
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 90.dp),
                textAlign = TextAlign.Center
            )
        }


    }

}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    WaitingScreen()
}

