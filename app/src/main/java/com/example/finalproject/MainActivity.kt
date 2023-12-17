package com.example.finalproject

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.data.MyPlaylistDataProvider
import com.example.finalproject.model.Track

class MainActivity : ComponentActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyPlaylistAdapter
    lateinit var searchView: SearchView
    companion object {
        val globalMediaPlayer = MediaPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_of_music)
        searchView = findViewById(R.id.search)
        recyclerView = findViewById(R.id.recyclerView)

        val dataList = MyPlaylistDataProvider.getTracksData()
        adapter = MyPlaylistAdapter(this@MainActivity, dataList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        Log.d("TAG: DataList", "Check : $dataList")
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->
                    val filteredTracks = getFilteredTracks(query)
                    val adapter = MyPlaylistAdapter(this@MainActivity, filteredTracks)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                }
                return true
            }
        })
        searchButton()
        homeButton()
    }
    private fun getFilteredTracks(query: String): List<Track> {
        return MyPlaylistDataProvider.getTracksData().filter {
            it.title.contains(query, ignoreCase = true)
        }
    }
    private fun searchButton(){
        val searchSp = findViewById<ImageView>(R.id.svButton)
        searchSp.setOnClickListener(){
            val intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
    }
    private fun homeButton(){
        val homeSp = findViewById<ImageView>(R.id.HOME)
        homeSp.setOnClickListener(){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
