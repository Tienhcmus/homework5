package com.example.recyclerview.dataroom

import androidx.room.*
import com.example.recyclerview.MovieModel


@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM Favorite_Movie")
    fun getAll(): List<MovieModel>

    @Insert
    fun insertAll(vararg movie: MovieModel) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: MovieModel): Long

    @Delete
    fun delete(account: MovieModel)

    @Query("DELETE FROM Favorite_Movie")
    fun deleteAll()
}