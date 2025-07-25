package com.example.mazesolver.list.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazesolver.core.domain.util.onError
import com.example.mazesolver.core.domain.util.onSuccess
import com.example.mazesolver.list.domain.MazeListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MazeListViewModel(private val repository: MazeListRepository) : ViewModel() {

    private val _state = MutableStateFlow(MazeListState())
    val state = _state.onStart { loadMazeList() }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), _state.value
    )

    private fun loadMazeList() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            runCatching {
                repository.fetchMazes().onSuccess { dto ->
                    _state.update {
                        it.copy(
                            isLoading = false, mazes = dto?.list ?: emptyList()
                        )
                    }
                }.onError { error ->
                    _state.update { it.copy(isLoading = false, error = error) }
                }
            }.onFailure {
                _state.update { it.copy(isLoading = false, error = it.error) }
            }
        }
    }
}