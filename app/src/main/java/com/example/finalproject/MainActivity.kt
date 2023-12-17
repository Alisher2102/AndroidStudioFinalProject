package com.example.finalproject

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.model.MusicData
import com.example.finalproject.network.MusicApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DataAdapter
    lateinit var searchView: SearchView
    companion object {
        val globalMediaPlayer = MediaPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView = findViewById<SearchView>(R.id.sv)
        recyclerView = findViewById(R.id.recyclerView)
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val retrofitData = newText?.let { MusicApi.retrofitService.getData(it) }
                retrofitData?.enqueue(object : Callback<MusicData?> {
                    override fun onResponse(
                        call: Call<MusicData?>,
                        response: Response<MusicData?>
                    ) {
                        val dataList = response.body()?.data!!

                        //                val textView = findViewById<TextView>(R.id.textView)
                        //                textView.text = dataList.toString()

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
                return true
            }
        })
    }
}