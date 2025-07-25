package com.example.mazesolver.list.data.repository

import com.example.mazesolver.core.domain.util.NetworkError
import com.example.mazesolver.core.domain.util.Result
import com.example.mazesolver.list.data.dto.MazeListDto
import com.example.mazesolver.list.data.network.MazesRemoteDataSource
import com.example.mazesolver.list.domain.MazeListRepository

class MazeListRepositoryImpl(private val dataSource: MazesRemoteDataSource) : MazeListRepository {
    override suspend fun fetchMazes(): Result<MazeListDto, NetworkError> = dataSource.fetchMazes()
}