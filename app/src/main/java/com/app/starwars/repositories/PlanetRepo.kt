package com.app.starwars.repositories

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.app.starwars.database.StarWarDatabase
import com.app.starwars.models.Planet
import com.app.starwars.networking.APIClient
import com.app.starwars.networking.ApiService
import kotlinx.coroutines.flow.Flow

class PlanetRepo(context: Context){
    private val database =StarWarDatabase.create(context)
    private val apiService = APIClient.getClient().create(ApiService::class.java)

    @OptIn(ExperimentalPagingApi::class)
    fun fetchPlanets(): Flow<PagingData<Planet>> {
        return Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 3),

            remoteMediator = PlanetRemoteMediator(database,apiService),
            pagingSourceFactory = { database.getPlanetDao().getAllPlanets()
            }
        ).flow
    }

}