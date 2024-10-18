package jp.ac.mayoi.ranking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorAccent
import jp.ac.mayoi.core.resource.colorAccentSecondary
import jp.ac.mayoi.core.resource.colorBackgroundPrimary
import jp.ac.mayoi.core.resource.colorTextCaption

@Composable
private fun CustomToggleButtons() {
    val buttonLabels = listOf("エリア名", "とてもながいエリア名", "さらに長いエリア名が続きます")
    var selectedButtonIndex by remember { mutableStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        buttonLabels.forEachIndexed { index, label ->
            RankingButtonChip(
                label = label,
                isSelected = selectedButtonIndex == index,
                onClick = { selectedButtonIndex = index }
            )
        }
    }
}

@Composable
private fun RankingButtonChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) colorAccent else colorBackgroundPrimary,
        ),
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) null else BorderStroke(1.dp, colorAccentSecondary),
        modifier = Modifier
            .height(48.dp) // ボタンの幅を文字の長さに応じて調整
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = if (isSelected) colorBackgroundPrimary else colorTextCaption
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomToggleButtonsPreview() {
    MaigoCompassTheme {
        CustomToggleButtons()
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectedRankingButtonChipPreview() {
    MaigoCompassTheme {
        RankingButtonChip(label = "エリア名", isSelected = true, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun UnselectedRankingButtonChipPreview() {
    MaigoCompassTheme {
        RankingButtonChip(label = "エリア名", isSelected = false, onClick = {})
    }
}
