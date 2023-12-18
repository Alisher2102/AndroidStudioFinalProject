package com.example.finalproject

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.finalproject.MainActivity.Companion.globalMediaPlayer
import com.example.finalproject.data.MyPlaylistDataProvider
import com.example.finalproject.model.MusicPlayerVIewModel
import com.example.finalproject.model.Track
import kotlin.time.toDuration

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
    lateinit var curTime: TextView
    lateinit var totTime: TextView
    val musicPlayerViewModel: MusicPlayerVIewModel by viewModels()
    val handler = Handler()
//    var searchPage = this.intent.getIntExtra("search_page",0)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.one_track)
        musicId = intent.getIntExtra("music_id", -1)
        tracksList = MyPlaylistDataProvider.getTracksData()
        playingTrack = intent.getIntExtra("playing_track", -1)
        seekbar = findViewById(R.id.musicSeekBar)
        selectedTrack = tracksList.find { it.id == musicId }!!
        totTime = findViewById(R.id.totTime)
        totTime.text = createTimeLabel(getAudioDuration(selectedTrack.audio))
        if(musicId == playingTrack)
            startSeekBar()
        musicImage = findViewById(R.id.music_image)
        trackName = findViewById(R.id.title)
        artist = findViewById(R.id.artist)
        playBtn = findViewById(R.id.play)
        curTime = findViewById(R.id.curTime)
        if (musicId == playingTrack && globalMediaPlayer.isPlaying)
            playBtn.setImageResource(R.drawable.pause)
        else playBtn.setImageResource(R.drawable.play_button)
        musicImage.setImageResource(selectedTrack.imageId)
        trackName.text = selectedTrack.title
        artist.text = selectedTrack.artist
        nextMusic()
        previousMusic()
//        if(globalMediaPlayer.currentPosition == globalMediaPlayer.duration)
//            nextOnTime()
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
        totTime.text = createTimeLabel(globalMediaPlayer.duration)
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
                curTime.text = createTimeLabel(currentPosition)
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(updateSeekBar, 0)
    }
    private fun nextMusic(){
        val homeSp = findViewById<ImageView>(R.id.next)
        homeSp.setOnClickListener{
            playBtn.setImageResource(R.drawable.pause)
            if(musicId<MyPlaylistDataProvider.getTracksData().size - 1){
                musicId++
                selectedTrack = tracksList.find { it.id == musicId }!!
                musicImage.setImageResource(selectedTrack.imageId)
                trackName.text = selectedTrack.title
                artist.text = selectedTrack.artist

                playingTrack = musicId
                globalMediaPlayer.stop()
                globalMediaPlayer.reset()
                globalMediaPlayer.setDataSource(
                    this,
                    Uri.parse("android.resource://${this.packageName}/${selectedTrack?.audio}")
                )
                globalMediaPlayer.prepare()
                globalMediaPlayer.start()
            }
            else{
                musicId = 0
                selectedTrack = tracksList.find { it.id == musicId }!!
                musicImage.setImageResource(selectedTrack.imageId)
                trackName.text = selectedTrack.title
                artist.text = selectedTrack.artist
                playingTrack = musicId

                globalMediaPlayer.stop()
                globalMediaPlayer.reset()
                globalMediaPlayer.setDataSource(
                    this,
                    Uri.parse("android.resource://${this.packageName}/${selectedTrack?.audio}")
                )
                globalMediaPlayer.prepare()
                globalMediaPlayer.start()

            }
            totTime.text = createTimeLabel(globalMediaPlayer.duration)
        }
    }
    private fun previousMusic(){
        val homeSp = findViewById<ImageView>(R.id.prev)
        homeSp.setOnClickListener(){
            playBtn.setImageResource(R.drawable.pause)
            if(musicId>0){
                musicId--

                selectedTrack = tracksList.find { it.id == musicId }!!
                musicImage.setImageResource(selectedTrack.imageId)
                trackName.text = selectedTrack.title
                artist.text = selectedTrack.artist
                playingTrack = musicId

                globalMediaPlayer.stop()
                globalMediaPlayer.reset()
                globalMediaPlayer.setDataSource(
                    this,
                    Uri.parse("android.resource://${this.packageName}/${selectedTrack?.audio}")
                )
                globalMediaPlayer.prepare()
                globalMediaPlayer.start()

            }
            else{
                musicId = MyPlaylistDataProvider.getTracksData().size - 1

                selectedTrack = tracksList.find { it.id == musicId }!!
                musicImage.setImageResource(selectedTrack.imageId)
                trackName.text = selectedTrack.title
                artist.text = selectedTrack.artist
                playingTrack = musicId

                globalMediaPlayer.stop()
                globalMediaPlayer.reset()
                globalMediaPlayer.setDataSource(
                    this,
                    Uri.parse("android.resource://${this.packageName}/${selectedTrack?.audio}")
                )
                globalMediaPlayer.prepare()
                globalMediaPlayer.start()
            }
            totTime.text = createTimeLabel(globalMediaPlayer.duration)
        }
    }
    fun createTimeLabel(duration: Int): String{
        var timerLabel: String = ""
        var min: Int = duration/1000/60
        var sec: Int = duration/1000%60
        timerLabel += "${min}:"

        if(sec < 10) timerLabel += "0"
        timerLabel +=sec
        return timerLabel
    }
    fun getAudioDuration(audio: Int): Int {
        val mediaPlayer = MediaPlayer.create(this, audio)
        val duration = mediaPlayer.duration
        mediaPlayer.release()
        return duration
    }
    fun nextOnTime(){
        if(musicId<MyPlaylistDataProvider.getTracksData().size - 1){

            musicId++
            selectedTrack = tracksList.find { it.id == musicId }!!
            musicImage.setImageResource(selectedTrack.imageId)
            trackName.text = selectedTrack.title
            artist.text = selectedTrack.artist

            playingTrack = musicId
            globalMediaPlayer.stop()
            globalMediaPlayer.reset()
            globalMediaPlayer.setDataSource(
                this,
                Uri.parse("android.resource://${this.packageName}/${selectedTrack?.audio}")
            )
            globalMediaPlayer.prepare()
            globalMediaPlayer.start()
        }
        else{
            musicId = 0
            selectedTrack = tracksList.find { it.id == musicId }!!
            musicImage.setImageResource(selectedTrack.imageId)
            trackName.text = selectedTrack.title
            artist.text = selectedTrack.artist
            playingTrack = musicId

            globalMediaPlayer.stop()
            globalMediaPlayer.reset()
            globalMediaPlayer.setDataSource(
                this,
                Uri.parse("android.resource://${this.packageName}/${selectedTrack?.audio}")
            )
            globalMediaPlayer.prepare()
            globalMediaPlayer.start()

        }
    }
}