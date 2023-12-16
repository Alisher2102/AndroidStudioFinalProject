package com.example.finalproject

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.MainActivity.Companion.globalMediaPlayer
import com.example.finalproject.model.Data
import com.squareup.picasso.Picasso

class DataAdapter(val context: Activity, val dataList: List<Data>):
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private var currentPlayingPosition: Int? = null
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView
        val title: TextView
        val artist: TextView
        val musiccard: CardView

        init {
            image = itemView.findViewById(R.id.image)
            title = itemView.findViewById(R.id.title)
            artist = itemView.findViewById(R.id.artist)
            musiccard = itemView.findViewById(R.id.music_card)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = dataList[position]
        holder.title.text = current.title
        holder.artist.text = current.artist.name
        Picasso.get().load(current.album.cover).into(holder.image)

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
            globalMediaPlayer.setDataSource(context, current.preview.toUri())
            globalMediaPlayer.prepare()
            globalMediaPlayer.start()
            currentPlayingPosition = position
        }
    }
}