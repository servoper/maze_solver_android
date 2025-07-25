package com.example.mazesolver.list.data.dto

import com.example.mazesolver.core.presentation.model.Maze
import com.google.gson.annotations.SerializedName

data class MazeListDto(
    @SerializedName("list")
    val list: List<Maze>,

    @SerializedName("count")
    val count: Int?
)
