package com.example.mazesolver.core.presentation.model

import com.google.gson.annotations.SerializedName

data class Maze (
    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val imageUrl: String?,
)