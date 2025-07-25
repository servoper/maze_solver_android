package com.example.mazesolver.list.data.network

import com.example.mazesolver.core.domain.util.NetworkError
import com.example.mazesolver.core.domain.util.Result
import com.example.mazesolver.list.data.dto.MazeListDto

interface MazesRemoteDataSource {
    suspend fun fetchMazes(): Result<MazeListDto, NetworkError>
}