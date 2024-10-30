package jp.ac.mayoi.traveling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.util.MaigoButton

@Composable
internal fun TravelingErrorScreen(
    onRetryButtonClick: () -> Unit,
    onTripCancelButtonClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "インターネットの接続に失敗しました",
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                text = "ネットワーク、WiFi設定を確認の上\n再度お試しください",
                fontSize = 16.sp,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.size(24.dp))
            MaigoButton(
                onClick = onRetryButtonClick,
                modifier = Modifier
                    .width(240.dp),
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = "再試行",
                    fontSize = 16.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            TripCancelButton(
                onTripCancelButtonClick = onTripCancelButtonClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TripPreview() {
    MaigoCompassTheme {
        TravelingErrorScreen(
            onRetryButtonClick = { },
            onTripCancelButtonClick = { }
        )
    }
}