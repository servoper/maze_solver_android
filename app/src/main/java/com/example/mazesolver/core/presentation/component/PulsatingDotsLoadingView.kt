package com.example.mazesolver.core.presentation.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mazesolver.R
import com.example.mazesolver.core.presentation.theme.MazeSolverTheme
import kotlinx.coroutines.delay

@Composable
fun PulsatingDotsLoadingView(
    modifier: Modifier = Modifier,
    loadingText: String = stringResource(R.string.loading),
    dotSize: Dp = 12.dp,
    dotColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetweenDots: Dp = 8.dp,
    animationDelayMillis: Int = 200
) {
    val dots = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) }
    )

    dots.forEachIndexed { index, canAnimate ->
        LaunchedEffect(canAnimate) {
            delay(index * animationDelayMillis.toLong())
            canAnimate.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 600,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenDots)
        ) {
            dots.forEach { canAnimate ->
                Box(
                    modifier = Modifier
                        .size(dotSize)
                        .graphicsLayer {
                            scaleX = canAnimate.value
                            scaleY = canAnimate.value
                            alpha = canAnimate.value // Fade in/out with scale
                        }
                        .clip(CircleShape)
                        .background(dotColor)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            textAlign = TextAlign.Center,
            text = loadingText,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true, name = "Pulsating Dots Light")
@Composable
fun PulsatingDotsLoadingViewPreviewLight() {
    MazeSolverTheme(darkTheme = false) {
        Surface {
            PulsatingDotsLoadingView()
        }
    }
}

@Preview(showBackground = true, name = "Pulsating Dots Dark")
@Composable
fun PulsatingDotsLoadingViewPreviewDark() {
    MazeSolverTheme(darkTheme = true) {
        Surface {
            PulsatingDotsLoadingView()
        }
    }
}