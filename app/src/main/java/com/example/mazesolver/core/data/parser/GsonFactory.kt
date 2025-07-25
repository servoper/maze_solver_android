package com.example.mazesolver.core.data.parser

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonFactory {
    val gson: Gson = GsonBuilder().create()
}