package com.app.starwars.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "planet")
data class Planet(
    @SerializedName("name")
    @PrimaryKey
    val name: String,
    @SerializedName("climate")
    val climate: String,
    @SerializedName("orbital_period")
    val orbital_period: String,
    @SerializedName("gravity")
    val gravity: String,
) : Serializable