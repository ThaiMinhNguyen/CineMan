package com.nemo.cineman.entity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    onRatingChanged: (Double) -> Unit,
    starsColor: Color = Color.Yellow
) {
    val starSize = 48.dp
    val starSizePx = with(LocalDensity.current) { starSize.toPx() }

    Row(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->

                    val newRating = (offset.x / starSizePx).coerceIn(0f, stars.toFloat())

                    val roundedRating = (newRating * 2).roundToInt() / 2.0
                    onRatingChanged(roundedRating)
                }
            }
    ) {
        for (index in 1..stars) {
            Icon(
                imageVector =
                when {
                    index <= rating -> Icons.Rounded.Star
                    index - 0.5 == rating -> Icons.AutoMirrored.Rounded.StarHalf
                    else -> Icons.Rounded.StarOutline
                },
                contentDescription = "Rating Star",
                tint = starsColor,
                modifier = Modifier.size(starSize)
            )
        }
    }
}
