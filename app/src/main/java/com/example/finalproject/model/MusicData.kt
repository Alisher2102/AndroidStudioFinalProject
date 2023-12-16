package com.example.finalproject.model

data class MusicData(
    val data: List<Data> = emptyList(),
    val next: String = "",
    val total: Int = 0
)