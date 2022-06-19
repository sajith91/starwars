package com.app.starwars.models

import com.google.gson.annotations.SerializedName

data class PlanetResponse(
    @SerializedName("previous")
    var previous: String?,
    @SerializedName("next")
    var next: String?,
    @SerializedName("results")
    var planetList: ArrayList<Planet>
)