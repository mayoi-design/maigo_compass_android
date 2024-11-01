package jp.ac.mayoi.ranking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorAccentSecondary

@Composable
fun RankingLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(50.dp),
            color = colorAccentSecondary,
            trackColor = Color.Transparent,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RankingLoadingPreview(){
    MaigoCompassTheme {
        RankingLoading()
    }
}