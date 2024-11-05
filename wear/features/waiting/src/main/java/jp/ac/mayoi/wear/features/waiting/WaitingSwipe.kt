package jp.ac.mayoi.wear.features.waiting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun WaitingSwipe() {
    val pagerState = rememberPagerState(initialPage = 0) { 2 }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> WaitingScreen(
                    isButtonView = true,
                    onSetDestinationButtonClick = { /* ボタン遷移後の処理 */ }
                )

                1 -> AppSettingButton(
                    onSettingButtonClick = { /* ボタン遷移後の処理 */ }
                )

            }
        }
    }
}
