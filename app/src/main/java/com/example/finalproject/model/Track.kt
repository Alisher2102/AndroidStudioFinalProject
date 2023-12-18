package com.example.finalproject.model

import android.provider.MediaStore.Audio
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class Track(
    val id: Int,
    val title: String,
    val artist: String,
    @DrawableRes val imageId: Int,
    @RawRes val audio: Int,
)