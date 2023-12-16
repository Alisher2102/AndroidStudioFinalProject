package com.example.finalproject

import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.MainActivity.Companion.globalMediaPlayer
import com.example.finalproject.model.Track

class MyPlaylistAdapter(val context: Activity, val dataList: List<Track>):
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private var currentPlayingPosition: Int? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent,false)
        return DataAdapter.ViewHolder(itemView)
    }



    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        val current = dataList[position]
        holder.title.text = current.title
        holder.artist.text = current.artist
        holder.image.setImageResource(current.imageId)

        holder.musiccard.setOnClickListener {
            handleMusicClick(position)
        }
    }

    private fun handleMusicClick(position: Int) {
        if (position == currentPlayingPosition) {
            if (globalMediaPlayer.isPlaying) {
                globalMediaPlayer.pause()
            } else {
                globalMediaPlayer.start()
            }
        } else {
            if (currentPlayingPosition != null) {
                globalMediaPlayer.stop()
                globalMediaPlayer.reset()
            }

            val current = dataList[position]
            globalMediaPlayer.setDataSource(context, Uri.parse("android.resource://${context.packageName}/${current.audio}"))
            globalMediaPlayer.prepare()
            globalMediaPlayer.start()
            currentPlayingPosition = position
        }
    }
}