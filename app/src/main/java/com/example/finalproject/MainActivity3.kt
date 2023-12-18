package com.example.finalproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.finalproject.data.MyPlaylistDataProvider
import com.example.finalproject.model.MusicPlayerVIewModel
import com.example.finalproject.model.Track

class MainActivity3 : ComponentActivity() {
    var musicId: Int = -1
    var playingTrack: Int = -1
    lateinit var tracksList: List<Track>
    lateinit var selectedTrack: Track
    lateinit var seekbar: SeekBar
    lateinit var musicImage: ImageView
    lateinit var trackName: TextView
    lateinit var artist: TextView
    val musicPlayerViewModel: MusicPlayerVIewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_track)
        seekbar = findViewById(R.id.musicSeekBar)
        musicImage = findViewById(R.id.music_image)
        trackName = findViewById(R.id.title)
        artist = findViewById(R.id.artist)
        musicId = intent.getIntExtra("music_id", -1)
        tracksList = MyPlaylistDataProvider.getTracksData()
        selectedTrack = tracksList.find { it.id == musicId }!!
//        playingTrack = intent.getIntExtra("playing_track", -1)

        musicImage.setImageResource(selectedTrack.imageId)
        trackName.text = selectedTrack.title
        artist.text = selectedTrack.artist
    }
    fun goBack(view: View){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("playingTrack", playingTrack)
        this.startActivity(intent)
    }

    fun play(view: View){
        val audioPath = intent.getStringExtra("audio_path")
        val isPlaying = intent.getBooleanExtra("is_playing", false)
        val currentPosition = intent.getIntExtra("current_position", 0)
        playingTrack = intent.getIntExtra("playing_track", -1)
        if (musicId != playingTrack) {
            if (playingTrack != -1) {
                MainActivity.globalMediaPlayer.stop()
                MainActivity.globalMediaPlayer.reset()
            }
            MainActivity.globalMediaPlayer.setDataSource(
                this,
                Uri.parse("android.resource://${this.packageName}/${selectedTrack?.audio}")
            )
            MainActivity.globalMediaPlayer.prepare()
            MainActivity.globalMediaPlayer.start()
            playingTrack = musicId
        }else{
            if (MainActivity.globalMediaPlayer.isPlaying) {
                MainActivity.globalMediaPlayer.pause()
            } else {
                MainActivity.globalMediaPlayer.start()
            }
        }
    }
}