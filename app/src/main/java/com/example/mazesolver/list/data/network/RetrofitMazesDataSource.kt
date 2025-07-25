package com.example.mazesolver.list.data.network

import com.example.mazesolver.core.data.network.toResult
import com.example.mazesolver.core.domain.util.NetworkError
import com.example.mazesolver.core.domain.util.Result
import com.example.mazesolver.list.data.dto.MazeListDto

class RetrofitMazesDataSource(private val factory: MazesApiFactory) : MazesRemoteDataSource {
    override suspend fun fetchMazes(): Result<MazeListDto, NetworkError> =
        factory.api.fetchMazes().toResult()
}