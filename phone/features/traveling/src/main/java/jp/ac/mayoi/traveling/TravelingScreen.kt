package jp.ac.mayoi.traveling
import androidx.compose.foundation.layout.Arrangement
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
internal fun SpotEmptyScreen(onSubmitSpot: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            text = "この近くにはまだスポットがないようです",
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
        Spacer(modifier = Modifier.size(24.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "あなたが最初のスポットを\n登録してみませんか？",
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.size(24.dp))
        MaigoButton(
            onClick = { },
            modifier = Modifier
                .width(240.dp),
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "スポットを登録",
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripPreview() {
    MaigoCompassTheme {
        SpotEmptyScreen(
            onSubmitSpot = { }
        )
    }
}

