package jp.ac.mayoi.wear.features.waiting

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices


@Composable
fun WaitingScreen() {
    Text(
        modifier = Modifier.padding(
            start = 65.dp,
            top = 86.dp,
        ),
        text = "ようこそ",

        color = MaterialTheme.colors.primary,
    )
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WaitingScreen()

}
