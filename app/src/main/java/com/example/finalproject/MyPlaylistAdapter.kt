package com.example.finalproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.MainActivity.Companion.globalMediaPlayer
import com.example.finalproject.model.Track

class MyPlaylistAdapter(val context: Activity, val dataList: List<Track>):
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private var currentPlayingPosition: Int? = null
    val playingTrack = context.intent.getIntExtra("playingTrack",-1)

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

        if(currentPlayingPosition != playingTrack && playingTrack != -1)
            currentPlayingPosition = playingTrack
        holder.musiccard.setOnClickListener {
            handleMusicClick(position)
        }

        holder.musiccard.setOnLongClickListener {
            val intent = Intent(context, MainActivity3::class.java)
            intent.putExtra("music_id", current.id)
            intent.putExtra("audio_path", current.audio)
            intent.putExtra("is_playing", globalMediaPlayer.isPlaying)
            intent.putExtra("current_position", globalMediaPlayer.currentPosition)
            if (playingTrack != -1)
                intent.putExtra("playing_track", playingTrack)
            else intent.putExtra("playing_track", currentPlayingPosition)
            context.startActivity(intent)
            true
        }

    }

    private fun handleMusicClick(position: Int) {
//        if(currentPlayingPosition != playingTrack && playingTrack != -1)
//            currentPlayingPosition = playingTrack
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