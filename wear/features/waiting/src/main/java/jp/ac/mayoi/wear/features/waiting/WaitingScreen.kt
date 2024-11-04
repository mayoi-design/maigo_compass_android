package jp.ac.mayoi.wear.features.waiting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import jp.ac.mayoi.wear.core.resource.MaigoButton
import jp.ac.mayoi.wear.core.resource.colorTextMain
import jp.ac.mayoi.wear.core.resource.spacingDouble

// スクロール作成のための画面の準備
class WaitingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WaitingScreen(
                    isButtonView = true,
                    onSetDestinationButtonClick = {
                        // ボタンがクリックされた時の処理を定義
                    }
                )
            }
        }
    }
}

@Composable
fun WaitingScreen(
    isButtonView: Boolean,
    onSetDestinationButtonClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "目的地を設定\nしてみましょう",
            fontSize = 13.sp,
            color = colorTextMain,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = spacingDouble)
        )
        if (isButtonView) {
            MaigoButton(
                onClick = onSetDestinationButtonClick,
                modifier = Modifier
                    .height(47.dp)
                    .width(138.dp)
            ) {
                Text(
                    text = "目的地を設定",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.height(47.dp)
            ) {
                Text(
                    text = "スマートフォンの操作を\n待機しています…",
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ボタンがないバージョン
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun InitialPreview() {
    WaitingScreen(
        isButtonView = true,
        onSetDestinationButtonClick = {/*なにか処理*/ }
    )
}

// ボタンがあるバージョン
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
private fun SettingPreview() {
    WaitingScreen(
        isButtonView = false,
        onSetDestinationButtonClick = {/*なにか処理*/ }
    )
}