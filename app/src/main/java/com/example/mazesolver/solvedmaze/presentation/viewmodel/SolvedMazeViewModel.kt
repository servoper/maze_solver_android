package com.example.mazesolver.solvedmaze.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazesolver.solvedmaze.domain.MazeSolverRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SolvedMazeViewModel(
    private val mazeName: String?,
    private val mazeImageUrl: String,
    private val repository: MazeSolverRepository
) :
    ViewModel() {

    private val _state = MutableStateFlow(MazeSolverState(mazeName = mazeName))
    val state = _state.onStart { solveMaze() }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    private fun solveMaze() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                )
            }

            _state.update {
                it.copy(
                    isLoading = false,
                    solvedImageBitmap = repository.solveMaze(mazeImageUrl)
                )
            }
        }
    }
}