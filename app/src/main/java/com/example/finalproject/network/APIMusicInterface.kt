package com.example.finalproject.network

import com.example.finalproject.model.MusicData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private const val BASE_URL =
    "https://deezerdevs-deezer.p.rapidapi.com/"


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

interface APIMusicInterface {
    @Headers(
        "X-RapidAPI-Key: 591a880d33msh95ee6337b73cb07p1896f1jsnb0b7b5f1e27c",
        "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com"
    )
    @GET("search")
     fun getData(@Query("q") query: String): Call<MusicData>
}

object MusicApi{
    val retrofitService: APIMusicInterface by lazy {
        retrofit.create(APIMusicInterface::class.java)
    }
}