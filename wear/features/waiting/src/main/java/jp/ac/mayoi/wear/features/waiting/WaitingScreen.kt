package jp.ac.mayoi.wear.features.waiting

import androidx.compose.foundation.layout.Column
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
import jp.ac.mayoi.wear.core.resource.MaigoButton
import jp.ac.mayoi.wear.core.resource.colorTextMain
import jp.ac.mayoi.wear.core.resource.textStyleBody

@Composable
fun WaitingScreen(
    isButtonView: Boolean,
    onSetDestinationButtonClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 45.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp),
            text = "目的地を設定\nしてみましょう",
            style = textStyleBody,
            color = colorTextMain,
            textAlign = TextAlign.Center
        )
        if (isButtonView) {
            MaigoButton(
                modifier = Modifier
                    .height(47.dp)
                    .width(138.dp),
                onClick = {
                    onSetDestinationButtonClick()
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
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PreviewButton() {
    var isButtonView by remember { mutableStateOf(true) }
    val onSetDestinationButtonClick = {
        isButtonView = false
    }
    WaitingScreen(
        isButtonView = isButtonView,
        onSetDestinationButtonClick = onSetDestinationButtonClick
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    PreviewButton()
}

