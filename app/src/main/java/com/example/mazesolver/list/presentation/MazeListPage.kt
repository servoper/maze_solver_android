package com.example.mazesolver.list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mazesolver.R
import com.example.mazesolver.core.presentation.component.MissingDataInfo
import com.example.mazesolver.core.presentation.component.PulsatingDotsLoadingView
import com.example.mazesolver.core.presentation.model.Maze
import com.example.mazesolver.core.presentation.theme.Dimension
import com.example.mazesolver.list.presentation.viewmodel.MazeListActions
import com.example.mazesolver.list.presentation.viewmodel.MazeListState

@Composable
fun MazeListPage(
    state: MazeListState, actions: (MazeListActions) -> Unit
) {
    if (state.isLoading) {
        PulsatingDotsLoadingView(loadingText = stringResource(R.string.loading_mazes))
    } else if (state.error != null) {
        MissingDataInfo(
            drawableId = R.drawable.ic_error,
            labelText = stringResource(R.string.something_went_wrong)
        )
    } else if (state.mazes.isEmpty()) {
        MissingDataInfo(
            drawableId = R.drawable.ic_palm,
            labelText = stringResource(R.string.no_mazes_label)
        )
    } else {
        LazyColumn(
            modifier = Modifier.padding(vertical = Dimension.spaceExtraSmall),
            verticalArrangement = Arrangement.spacedBy(Dimension.spaceSmall),
        ) {
            items(state.mazes) {
                MazeListItem(it) { selectedMaze ->
                    actions(MazeListActions.ItemSelected(selectedMaze))
                }
            }
        }
    }
}

@Composable
fun MazeListItem(maze: Maze, onSelected: (Maze) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelected(maze)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(Dimension.cornerRadiusDefault),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(Dimension.spaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                maze.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.size(Dimension.spaceExtraSmall))

                maze.description?.let {
                    Text(
                        modifier = Modifier.clickable {
                            onSelected(maze)
                        },
                        text = it,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            AsyncImage(
                model = maze.imageUrl,
                contentDescription = maze.description,
                modifier = Modifier.width(Dimension.listItemImageSize)
            )
        }
    }
}