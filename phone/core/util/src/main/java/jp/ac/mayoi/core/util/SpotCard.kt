package jp.ac.mayoi.core.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jp.ac.mayoi.core.resource.DrawableR
import jp.ac.mayoi.core.resource.MaigoCompassTheme
import jp.ac.mayoi.core.resource.colorBackgroundPrimary
import jp.ac.mayoi.core.resource.colorBackgroundSecondary
import jp.ac.mayoi.core.resource.colorTextCaption
import jp.ac.mayoi.core.resource.colorTextMain
import jp.ac.mayoi.core.resource.spacingDouble
import jp.ac.mayoi.core.resource.spacingHalf
import jp.ac.mayoi.core.resource.spacingSingle
import jp.ac.mayoi.core.resource.textStyleBody
import jp.ac.mayoi.core.resource.textStyleCaption
import jp.ac.mayoi.phone.model.LocalSpot
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SpotCard(
    spot: LocalSpot,
    onCardClicked: () -> Unit,
    isClickEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colorBackgroundPrimary)
            .clickable(
                enabled = isClickEnabled,
                onClick = onCardClicked,
            )
            .padding(spacingDouble)
    ) {
        AsyncImage(
            model = spot.imageUrl,
            contentDescription = null,
            error = ColorPainter(colorBackgroundSecondary),
            placeholder = ColorPainter(colorBackgroundSecondary),
            contentScale = ContentScale.Crop, // Cropじゃないほうがいいかも
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(
            modifier = Modifier.size(spacingDouble)
        )

        /*
        // emojiをいれるときはここのコメントを解除して書く
        Image()

        Spacer(
            modifier = Modifier
                .size(spacingDouble)
        )
         */

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = spot.message,
                style = textStyleBody,
                color = colorTextMain,
            )

            Spacer(
                modifier = Modifier.size(spacingHalf)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(24.dp)
            ) {
                ReachCounter(
                    count = spot.reachedCount ?: 0,
                )

                Spacer(modifier = Modifier.size(spacingDouble))

                val date = remember(spot.createdAt) {
                    parseIso8601(spot.createdAt)
                }
                Text(
                    text = date,
                    style = textStyleCaption,
                    color = colorTextCaption,
                )
            }
        }
    }
}

@Composable
private fun ReachCounter(
    count: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(DrawableR.footprint_24dp),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
        Text(
            text = "$count",
            fontSize = 12.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorTextMain,
        )
    }
}

internal fun parseIso8601(
    dateTime: String?,
): String {
    if (dateTime == null) {
        return "Unknown Date"
    } else {
        try {
            val res = LocalDateTime
                .parse(
                    dateTime,
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME
                )
                .format(
                    DateTimeFormatter.ofPattern("yyyy/MM/dd")
                )

            return res
        } catch (_: Exception) {
            return "Unknown Date"
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
private fun SpotCardPreview() {
    MaigoCompassTheme {
        val spot = LocalSpot(
            lat = 0.0F,
            lng = 0.0F,
            message = "Hello From Preview!",
            imageUrl = "",
            postUserId = "",
            reachedCount = 100,
            createdAt = "2024-10-09T23:31:15+09:00",
        )

        val longMessageSpot = spot.copy(
            message = "ながい共有時文章".repeat(4)
        )

        val veryLongMessageSpot = spot.copy(
            message = "絶対に実運用で出てくることはないだろうけど、" +
                    "こういうめちゃくちゃ長いテキストもいれることができる。" +
                    "この場合はカードが縦方向に伸びることでどうにかなる"
        )

        val wrongTimeFormatSpot = spot.copy(
            message = "createdAtのparseに失敗するとこうなる",
            createdAt = "2024-10-09T23:31:15+0900",
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            SpotCard(
                spot = spot,
                onCardClicked = {},
                isClickEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(spacingSingle))

            SpotCard(
                spot = longMessageSpot,
                onCardClicked = {},
                isClickEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(spacingSingle))

            SpotCard(
                spot = veryLongMessageSpot,
                onCardClicked = {},
                isClickEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.size(spacingSingle))

            SpotCard(
                spot = wrongTimeFormatSpot,
                onCardClicked = {},
                isClickEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}