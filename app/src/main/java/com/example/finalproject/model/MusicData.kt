package com.example.finalproject.model

import com.example.finalproject.model.Data

data class MusicData(
    val data: List<Data> = emptyList(),
    val next: String = "",
    val total: Int = 0
)