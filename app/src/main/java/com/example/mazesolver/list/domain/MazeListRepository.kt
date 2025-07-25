package com.example.mazesolver.list.domain

import com.example.mazesolver.core.domain.util.NetworkError
import com.example.mazesolver.core.domain.util.Result
import com.example.mazesolver.list.data.dto.MazeListDto

interface MazeListRepository {
    suspend fun fetchMazes(): Result<MazeListDto, NetworkError>
}