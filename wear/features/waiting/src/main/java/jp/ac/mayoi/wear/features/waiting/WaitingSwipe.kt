package jp.ac.mayoi.wear.features.waiting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.wear.core.resource.colorButtonTextPrimary
import jp.ac.mayoi.wear.core.resource.colorTextCaption

@Composable
fun WaitingSwipe() {
    val pagerState = rememberPagerState(initialPage = 0) { 2 }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
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
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (iteration == pagerState.currentPage) colorButtonTextPrimary else colorTextCaption
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(7.dp)
                )
            }
        }
    }
}


