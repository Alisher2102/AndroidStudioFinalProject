package com.example.finalproject

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.MyPlaylistDataProvider
import com.example.finalproject.model.MusicData
import com.example.finalproject.network.MusicApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyPlaylistAdapter
    companion object {
        val globalMediaPlayer = MediaPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_music)

        recyclerView = findViewById(R.id.recyclerView)
        val retrofitData = MusicApi.retrofitService.getData("eminem")
        retrofitData.enqueue(object : Callback<MusicData?> {
            override fun onResponse(
                call: Call<MusicData?>,
                response: Response<MusicData?>
            ) {
//                val dataList = response.body()?.data!!
//                adapter = DataAdapter(this@MainActivity, dataList)
                val dataList = MyPlaylistDataProvider.getTracksData()
                adapter = MyPlaylistAdapter(this@MainActivity, dataList)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                Log.d("TAG: onResponse", "onResponse: " + response.body())
            }

            override fun onFailure(call: Call<MusicData?>, t: Throwable) {
                Log.d("TAG: onFailure", "onFailure: " + t.message)
            }
        })
    }
}