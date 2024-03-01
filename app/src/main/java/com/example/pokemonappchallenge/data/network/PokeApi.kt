package com.example.pokemonappchallenge.data.network

import com.example.pokemonappchallenge.data.models.GenerationDetailsResponse
import com.example.pokemonappchallenge.data.models.PokemonGenerationResponse
import com.example.pokemonappchallenge.data.models.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ) : Response<PokemonResponse>

    @GET("generation")
    suspend fun getGenerationList() : Response<PokemonGenerationResponse>

    @GET()
    suspend fun getGenerationDetails(
        @Url url: String
    ) : Response<GenerationDetailsResponse>
}