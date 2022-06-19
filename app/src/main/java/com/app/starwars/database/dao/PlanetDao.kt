package com.app.starwars.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.starwars.models.Planet

@Dao
interface PlanetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<Planet>)

    @Query("SELECT * FROM planet")
    fun getAllPlanets(): PagingSource<Int, Planet>

    @Query("DELETE FROM planet")
    fun deleteNewsItems(): Int
}