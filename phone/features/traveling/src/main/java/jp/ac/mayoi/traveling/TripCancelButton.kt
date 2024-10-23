package jp.ac.mayoi.traveling

import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.util.MaigoButton

@Composable
fun TripCancelButton(
    onTripCancelButtonClick: () -> Unit
) {
    MaigoButton(
        onClick = onTripCancelButtonClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3261E)),
        modifier = Modifier
            .width(180.dp),
    ) {
        Text(
            fontWeight = FontWeight.Bold,
            text = "旅行を中断",
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TripCancelPreview() {
    MaigoCompassTheme {
        TripCancelButton(
            onTripCancelButtonClick = { }
        )
    }
}