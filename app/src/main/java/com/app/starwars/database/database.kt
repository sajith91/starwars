package com.app.starwars.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.starwars.database.dao.PlanetDao
import com.app.starwars.database.dao.RemoteKeysDao
import com.app.starwars.models.Planet
import com.app.starwars.models.RemoteKeys

@Database(entities = [Planet::class, RemoteKeys::class],version = 1,exportSchema = false)
abstract class StarWarDatabase : RoomDatabase(){
    abstract fun getPlanetDao(): PlanetDao
    abstract fun remoteKeyDao(): RemoteKeysDao

    companion object {
        fun create(context: Context): StarWarDatabase {
            val databaseBuilder =
                Room.databaseBuilder(context, StarWarDatabase::class.java, "starwar.db")
            return databaseBuilder.build()
        }
    }
}