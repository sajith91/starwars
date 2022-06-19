package com.app.starwars.presentation.planetList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.app.starwars.models.Planet
import com.app.starwars.repositories.PlanetRepo
import kotlinx.coroutines.flow.Flow

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = PlanetRepo(application)

    fun fetchPlanets(): Flow<PagingData<Planet>> {
        return repo.fetchPlanets().cachedIn(viewModelScope)
    }

}