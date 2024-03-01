package com.example.pokemonappchallenge.data.repository

import com.example.pokemonappchallenge.data.utils.Constants
import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.data.network.PokeApi
import com.example.pokemonappchallenge.data.room.PokemonDao
import com.example.pokemonappchallenge.data.utils.toDomain
import com.example.pokemonappchallenge.data.utils.toEntity
import com.example.pokemonappchallenge.data.utils.toUI
import com.example.pokemonappchallenge.domain.repository.PokemonRepository
import com.example.pokemonappchallenge.domain.response.Result
import com.example.pokemonappchallenge.domain.response.Status
import com.example.pokemonappchallenge.ui.model.Generation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokeApi: PokeApi,
    private val dao: PokemonDao,
): PokemonRepository {

    var maxListSize = 0

    override suspend fun getPokemons(): Flow<Result<List<Pokemon>>> {
        return flow {
            emit(Result.Loading())
            try {
                delay(1000)
                if (getLastItem() == 0) {

                    /** Fetch data for first time from remote**/
                    val response = pokeApi.getPokemonList(
                        offset = getLastItem(),
                        limit = Constants.LIMIT_STARTED
                    )
                    if (response.isSuccessful) {
                        maxListSize = response.body()?.count ?: 0
                        dao.savePokemons(response.body()?.results?.map {
                            it.toEntity()
                        } ?: listOf())

                        emit(fetchLocalPokemonList())

                    } else {
                        emit(Result.Error(Status.ERROR, message = "Error when fetch data!"))
                    }
                } else {
                    /** Fetch Data form Local Data Base **/
                    emit(fetchLocalPokemonList())
                }


            } catch (e: Throwable) {
                emit(Result.Error(Status.ERROR, message = e.message.toString()))
            }
        }

    }

    override suspend fun fetchInBackground(): Flow<Result<List<Pokemon>>>{
        return flow {
            emit(Result.Loading())
            try {
                /** Download data from remote periodically as long as the
                 * last item on remote is not downloaded yet**/
                while (getLastItem() < maxListSize || maxListSize == 0){
                    val response = pokeApi.getPokemonList(
                        offset = getLastItem(),
                        limit = Constants.LIMIT
                    )
                    if (response.isSuccessful) {
                        maxListSize = response.body()?.count ?: 0
                        dao.savePokemons(response.body()?.results?.map {
                            it.toEntity()
                        } ?: listOf())

                        emit(fetchLocalPokemonList())

                    } else {
                        emit(Result.Error(Status.ERROR, message = "Error when fetch data!"))
                    }
                    delay(10000)
                }

            } catch (e: Throwable) {
                emit(Result.Error(Status.ERROR, message = e.message.toString()))
            }
        }
    }

    override suspend fun getGenerations(): Flow<Result<List<Generation>>> {
       return flow {
            emit(Result.Loading())
           try {
               val response = pokeApi.getGenerationList()
               if (response.isSuccessful){

                   /**run over every generation to get the details**/

                   val generationList = mutableListOf<Generation>()

                   for (generation in (response.body()?.results ?: listOf())){
                       val generationDetail = pokeApi.getGenerationDetails(
                           generation.url
                       )
                       if (generationDetail.isSuccessful){
                           generationDetail.body()?.let {
                               generationList.add(it.toUI())
                           }
                       } else {
                           emit(Result.Error(Status.ERROR, message = "Error when fetch data!"))
                       }
                   }

                   emit(Result.Success(data = generationList, Status.SUCCESS))

               } else {
                   emit(Result.Error(Status.ERROR, message = "Error when fetch data!"))
               }
           } catch (e: Throwable){
               emit(Result.Error(Status.ERROR, message = "Error when fetch data!"))
           }
        }
    }

    private suspend fun fetchLocalPokemonList(): Result<List<Pokemon>>{
        val pokemonFromDB = dao.getPokemons().first()
        return Result.Success(data = pokemonFromDB.map {
            it.toDomain()
        }, status = Status.SUCCESS)
    }

    private suspend fun getLastItem(): Int {
        return dao.getPokemons().first().size
    }

}