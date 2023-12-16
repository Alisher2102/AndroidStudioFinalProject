package com.example.finalproject

import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.DataAdapter
import com.example.finalproject.model.MusicData
import com.example.finalproject.network.MusicApi
import com.example.finalproject.ui.theme.materials.FinalProjectTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DataAdapter
    companion object {
        val globalMediaPlayer = MediaPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val retrofitData = MusicApi.retrofitService.getData("eminem")
        retrofitData.enqueue(object : Callback<MusicData?> {
            override fun onResponse(
                call: Call<MusicData?>,
                response: Response<MusicData?>
            ) {
                val dataList = response.body()?.data!!
//                val textView = findViewById<TextView>(R.id.textView)
//                textView.text = dataList.toString()
                adapter = DataAdapter(this@MainActivity, dataList)
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
