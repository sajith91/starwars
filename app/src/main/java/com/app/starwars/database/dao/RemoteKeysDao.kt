package com.app.starwars.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.starwars.models.RemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRemoteKeys(remoteKey:List<RemoteKeys> )

//    @Query("SELECT * FROM remoteKey ORDER BY email DESC")
//    suspend fun getRemoteKeys(): List<RemoteKeys>

    @Query("SELECT * FROM remoteKey WHERE name = :name")
    fun remoteKeysByUserId(name: String): RemoteKeys?

    @Query("DELETE FROM remoteKey")
    fun clearRemoteKeys()
}