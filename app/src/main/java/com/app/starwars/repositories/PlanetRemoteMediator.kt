package com.app.starwars.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.app.starwars.database.StarWarDatabase
import com.app.starwars.models.Planet
import com.app.starwars.models.RemoteKeys
import com.app.starwars.networking.ApiService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class PlanetRemoteMediator constructor(
    private val db: StarWarDatabase,
    private val apiService: ApiService
)  : RemoteMediator<Int, Planet>() {
    var page =1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Planet>):
            MediatorResult {
        return try {
            page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    1 //initial page = 1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: throw InvalidObjectException("Result is empty")
                    if(remoteKeys.nextKey == null) {
                        remoteKeys.nextKey ?: return MediatorResult.Success(true)
                    }
                    remoteKeys.nextKey
                }
            }

            val response = apiService.fetchPlanets(
                page= page as Int
            )

            val listing = response.planetList
            val endOfList =if(response.next == null) true else false
           // val endOfPaginationReached = listing.size < state.config.pageSize

            db.withTransaction {
                // if refreshing, clear table and start over
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeyDao().clearRemoteKeys()
                    db.getPlanetDao().deleteNewsItems()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfList) null else page + 1
                val keys = listing.map {
                    RemoteKeys(name = it.name, prevKey = prevKey, nextKey = nextKey)
                }

                db.remoteKeyDao().saveRemoteKeys(keys)
                db.getPlanetDao().insert(listing)
            }

            MediatorResult.Success(endOfPaginationReached = endOfList)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }


    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Planet>): RemoteKeys? {
        return state.lastItemOrNull()?.let { planet ->
            db.withTransaction { db.remoteKeyDao().remoteKeysByUserId(planet.name) }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Planet>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { id ->
                db.withTransaction { db.remoteKeyDao().remoteKeysByUserId(id) }
            }
        }
    }

}