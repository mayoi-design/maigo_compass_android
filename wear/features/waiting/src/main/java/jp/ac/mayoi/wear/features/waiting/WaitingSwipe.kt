package jp.ac.mayoi.wear.features.waiting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ac.mayoi.wear.core.resource.colorButtonTextPrimary
import jp.ac.mayoi.wear.core.resource.colorTextCaption
import jp.ac.mayoi.wear.core.resource.spacingHalf

@Composable
fun WaitingSwipe(
    onSettingButtonClick: () -> Unit,
    onReceiveDestinationData: (lat: Double, lng: Double) -> Unit,
    viewModel: WaitingScreenViewModel = viewModel()
) {
    WaitingSwipe(
        isButtonView = viewModel.isButtonView,
        onSettingButtonClick = onSettingButtonClick,
        onSetDestinationButtonClick = {
            viewModel.onSetDestinationButtonClick()
            onReceiveDestinationData(
                41.797653393691476,
                140.7550099479477,
            )
        },
    )
}

@Composable
private fun WaitingSwipe(
    isButtonView: Boolean,
    onSetDestinationButtonClick: () -> Unit,
    onSettingButtonClick: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            when (page) {
                0 -> WaitingScreen(
                    isButtonView = isButtonView,
                    onSetDestinationButtonClick = onSetDestinationButtonClick
                )
                1 -> AppSettingButton(
                    onSettingButtonClick = onSettingButtonClick
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
                        .padding(spacingHalf)
                        .clip(CircleShape)
                        .background(color)
                        .size(7.dp)
                )
            }
        }
    }
}


