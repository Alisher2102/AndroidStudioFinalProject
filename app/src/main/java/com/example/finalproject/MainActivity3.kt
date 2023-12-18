package com.example.finalproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.MainActivity.Companion.globalMediaPlayer
import com.example.finalproject.data.MyPlaylistDataProvider
import com.example.finalproject.model.MusicPlayerVIewModel
import com.example.finalproject.model.Track
import com.example.finalproject.ui.theme.FinalProjectTheme

class MainActivity3 : ComponentActivity() {
    var musicId: Int = -1
    var playingTrack: Int = -1
    lateinit var tracksList: List<Track>
    lateinit var selectedTrack: Track
    lateinit var seekbar: SeekBar
    lateinit var musicImage: ImageView
    lateinit var trackName: TextView
    lateinit var artist: TextView
    lateinit var playBtn: ImageButton
    val musicPlayerViewModel: MusicPlayerVIewModel by viewModels()
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.one_track)
        musicId = intent.getIntExtra("music_id", -1)
        playingTrack = intent.getIntExtra("playing_track", -1)
        seekbar = findViewById(R.id.musicSeekBar)
        if(musicId == playingTrack)
            startSeekBar()
        musicImage = findViewById(R.id.music_image)
        trackName = findViewById(R.id.title)
        artist = findViewById(R.id.artist)
        playBtn = findViewById(R.id.play)
        tracksList = MyPlaylistDataProvider.getTracksData()
        selectedTrack = tracksList.find { it.id == musicId }!!

        if (musicId == playingTrack && globalMediaPlayer.isPlaying)
            playBtn.setImageResource(R.drawable.pause)
        else playBtn.setImageResource(R.drawable.play_button)
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
        if (musicId != playingTrack) {
            if (playingTrack != -1) {
                globalMediaPlayer.stop()
                globalMediaPlayer.reset()
            }
            globalMediaPlayer.setDataSource(
                this,
                Uri.parse("android.resource://${this.packageName}/${selectedTrack.audio}")
            )
            globalMediaPlayer.prepare()
            globalMediaPlayer.start()
            playBtn.setImageResource(R.drawable.pause)
            playingTrack = musicId
        }else{
            if (globalMediaPlayer.isPlaying) {
                globalMediaPlayer.pause()
                playBtn.setImageResource(R.drawable.play_button)
            } else {
                globalMediaPlayer.start()
                playBtn.setImageResource(R.drawable.pause)
            }
        }
        startSeekBar()
    }
    fun startSeekBar(){
        seekbar.max = globalMediaPlayer.duration
        val currentPosition = intent.getIntExtra("current_position",0)
        seekbar.progress = currentPosition
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    globalMediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        val updateSeekBar = object : Runnable {
            override fun run() {
                val currentPosition = globalMediaPlayer.currentPosition
                seekbar.progress = currentPosition
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(updateSeekBar, 0)
    }
}
