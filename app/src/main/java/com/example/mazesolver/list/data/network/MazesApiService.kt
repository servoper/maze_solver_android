package com.example.mazesolver.list.data.network

import com.example.mazesolver.list.data.dto.MazeListDto
import retrofit2.Response
import retrofit2.http.GET

interface MazesApiService {
    @GET("mazes")
    suspend fun fetchMazes(): Response<MazeListDto>
}