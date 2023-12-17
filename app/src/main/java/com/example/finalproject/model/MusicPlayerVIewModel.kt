package com.example.finalproject.model

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel

class MusicPlayerVIewModel: ViewModel() {
    val mediaPlayer = MediaPlayer()
    var isPlaying = false
    var currentAudioPath: String? = null
    var currentPosition: Int = 0
}