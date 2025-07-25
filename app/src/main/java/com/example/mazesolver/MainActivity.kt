package com.example.mazesolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mazesolver.core.presentation.MazesListDestination
import com.example.mazesolver.core.presentation.SolvedMazeDestination
import com.example.mazesolver.core.presentation.theme.Dimension
import com.example.mazesolver.core.presentation.theme.MazeSolverTheme
import com.example.mazesolver.solvedmaze.presentation.viewmodel.SolvedMazeViewModel
import com.example.mazesolver.list.presentation.MazeListPage
import com.example.mazesolver.list.presentation.viewmodel.MazeListActions
import com.example.mazesolver.list.presentation.viewmodel.MazeListViewModel
import com.example.mazesolver.solvedmaze.presentation.MazeSolverPage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MazeSolverTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = Dimension.space)
                    ) {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = MazesListDestination
                        ) {

                            composable<MazesListDestination> {
                                val viewModel: MazeListViewModel = koinViewModel()

                                val state by viewModel.state.collectAsStateWithLifecycle()

                                MazeListPage(
                                    state
                                ) { action ->
                                    when {
                                        action is MazeListActions.ItemSelected -> {
                                            action.maze.imageUrl?.let { url ->
                                                navController.navigate(
                                                    SolvedMazeDestination(
                                                        action.maze.name,
                                                        action.maze.imageUrl
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            composable<SolvedMazeDestination> {
                                val viewModel: SolvedMazeViewModel = koinViewModel(parameters = {
                                    parametersOf(
                                        it.toRoute<SolvedMazeDestination>().name,
                                        it.toRoute<SolvedMazeDestination>().url,

                                        )
                                })

                                val state by viewModel.state.collectAsStateWithLifecycle()

                                MazeSolverPage(state)
                            }

                        }
                    }
                }
            }
        }
    }
}