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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.StringR
import jp.ac.mayoi.core.resource.spacingTriple
import jp.ac.mayoi.core.resource.textStyleBody
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
                text = stringResource(StringR.traveling_connection_failed),
                style = textStyleBody
            )
            Spacer(modifier = Modifier.size(spacingTriple))
            Text(
                text = stringResource(StringR.traveling_retry_section),
                style = textStyleBody,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(spacingTriple))
            MaigoButton(
                onClick = onRetryButtonClick,
                modifier = Modifier
                    .width(240.dp),
            ) {
                Text(
                    text = stringResource(StringR.general_retry),
                    style = textStyleBody
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
private fun TravelingErrorScreenPreview() {
    MaigoCompassTheme {
        TravelingErrorScreen(
            onRetryButtonClick = { },
            onTripCancelButtonClick = { }
        )
    }
}