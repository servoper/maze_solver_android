package com.example.mazesolver.solvedmaze.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.mazesolver.R
import com.example.mazesolver.core.presentation.component.MissingDataInfo
import com.example.mazesolver.core.presentation.component.PulsatingDotsLoadingView
import com.example.mazesolver.solvedmaze.presentation.component.ZoomableBox
import com.example.mazesolver.solvedmaze.presentation.viewmodel.MazeSolverState
import org.koin.compose.koinInject


@Composable
fun MazeSolverPage(state: MazeSolverState) {
    if (state.isLoading) {
        PulsatingDotsLoadingView(loadingText = stringResource(R.string.solving))
    } else if (state.solvedImageBitmap == null) {
        MissingDataInfo(
            drawableId = R.drawable.ic_error,
            labelText = stringResource(R.string.cant_solve_label)
        )
    } else {
        state.solvedImageBitmap.let {
            ZoomableBox(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            ) {
                Image(
                    modifier = Modifier.graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offsetX,
                        translationY = offsetY
                    ), bitmap = it.asImageBitmap(), contentDescription = stringResource(
                        R.string.solved_maze_description, state.mazeName ?: ""
                    )
                )
            }
        }
    }
}