package com.example.finalproject.data

import com.example.finalproject.R
import com.example.finalproject.model.Track

object MyPlaylistDataProvider {
    fun getTracksData(): List<Track> {
        return listOf(
            Track(
                id = 0,
                title = "Smack That",
                artist = "Akon",
                imageId = R.drawable.akon_smack,
                audio = R.raw.akon_smack
            ),
            Track(
                id = 1,
                title = "My Way",
                artist = "Calvin Harris",
                imageId = R.drawable.calvin_myway,
                audio = R.raw.calvin_myway
            ),
            Track(
                id = 2,
                title = "Shape of You",
                artist = "Ed Sheeran",
                imageId = R.drawable.ed_shape,
                audio = R.raw.sheeran_shape
            ),
            Track(
                id = 3,
                artist = "МакSим",
                title = "Знаешь ли ты",
                imageId = R.drawable.maksim_znayesh,
                audio = R.raw.maksim_znayesh
            ),
            Track(
                id = 4,
                artist = "Evanescence",
                title = "Bring Me to Life",
                imageId = R.drawable.evanescence_bring,
                audio = R.raw.evanescence_bring
            ),
            Track(
                id = 5,
                title = "How Deep Is Your Love",
                artist = "Calvin Harris, Disciples",
                imageId = R.drawable.calvin_howdeep,
                audio = R.raw.calvin_howdeep
            ),
            Track(
                id = 6,
                artist = "Michael Jackson",
                title = "Billie Jean",
                imageId = R.drawable.michael_billie,
                audio = R.raw.michael_billie
            ),
            Track(
                id = 7,
                artist = "Charlie Puth, Selena Gomez",
                title = "We Don't Talk Anymore",
                imageId = R.drawable.charlie_wedont,
                audio = R.raw.charlie_wedont
            ),
            Track(
                id = 8,
                artist = "The Kid LAROI, Justin Bieber",
                title = "STAY",
                imageId = R.drawable.laroi_stay,
                audio = R.raw.laroi_stay
            ),
            Track(
                id = 9,
                artist = "Marshmello, Anne-Marie",
                title = "Friends",
                imageId = R.drawable.marshmello_friends,
                audio = R.raw.marshmello_friends
            ),
            Track(
                id = 10,
                artist = "Rihanna",
                title = "Diamonds",
                imageId = R.drawable.rihanna_diamonds,
                audio = R.raw.rihanna_diamonds
            ),

        )
    }
}