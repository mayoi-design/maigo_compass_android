package jp.ac.mayoi.ranking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun CustomToggleButtons() {
    val buttonLabels = listOf("エリア名")
    var selectedButtonIndex by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        buttonLabels.forEachIndexed { index, label ->
            Button(
                onClick = { selectedButtonIndex = index },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedButtonIndex == index) Color(0xFFFF8080) else Color.White,
                    contentColor = if (selectedButtonIndex == index) Color.White else Color.Gray
                ),
                shape = RoundedCornerShape(50),
                border = BorderStroke(2.dp, Color.Cyan),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Text(text = label, style = TextStyle(fontSize = 14.sp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomToggleButtonsPreview() {
    CustomToggleButtons()
}
