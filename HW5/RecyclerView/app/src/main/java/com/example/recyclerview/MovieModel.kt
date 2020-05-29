package com.example.recyclerview

import android.os.Parcel
import android.os.Parcelable
import android.service.quicksettings.Tile
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList


@Parcelize
@Entity(tableName = "Favorite_Movie")
data class MovieModel(
    val popularity: Float,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String,
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    @ColumnInfo(name = "Movie_name") var title: String?,
    val vote_average: Float,
    val overview: String,
    val release_date: String) : Parcelable {
    fun getURL(): String {
        var url:String = "https://image.tmdb.org/t/p/w500/"
        url = url.plus(poster_path)
        return url
    }

}