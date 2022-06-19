package com.app.starwars.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "remoteKey")
data class RemoteKeys(
    @PrimaryKey
    val name: String,
    val prevKey: Int?,
    val nextKey: Int?
) : Serializable
