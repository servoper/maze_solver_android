package com.example.mazesolver.core.presentation

import kotlinx.serialization.Serializable


@Serializable
object MazesListDestination


@Serializable
data class SolvedMazeDestination(
    val name: String?,
    val url: String
)