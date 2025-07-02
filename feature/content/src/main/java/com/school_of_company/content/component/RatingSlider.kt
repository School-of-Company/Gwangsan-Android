package com.school_of_company.content.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.GwangSanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RatingSlider(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    require(value in 1..100)

    GwangSanTheme { colors, _ ->

        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt().coerceIn(1, 100)) },
            valueRange = 1f..100f,
            thumb = {
                Canvas(modifier = Modifier.size(16.5.dp)) {
                    drawCircle(color = colors.subYellow500)

                    drawCircle(
                        color = Color.White,
                        radius = size.minDimension / 1.85f * 0.7f
                    )
                }
            },
            track = { sliderState ->
                val fraction by remember {
                    derivedStateOf {
                        (sliderState.value - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                    }
                }

                Box(Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction)
                            .align(Alignment.CenterStart)
                            .height(6.dp)
                            .padding(end = 16.dp)
                            .background(
                                color = colors.subYellow500,
                                shape = CircleShape
                            )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(1f - fraction)
                            .align(Alignment.CenterEnd)
                            .height(6.dp)
                            .padding(start = 16.dp)
                            .background(
                                color = colors.gray100,
                                shape = CircleShape
                            )
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRatingSlider() {
    var rating by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Rating: $rating",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        RatingSlider(
            value = rating,
            onValueChange = { rating = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
    }
}
