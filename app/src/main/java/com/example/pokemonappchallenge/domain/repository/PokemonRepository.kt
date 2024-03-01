package com.example.pokemonappchallenge.domain.repository

import com.example.pokemonappchallenge.data.models.GenerationDetailsResponse
import com.example.pokemonappchallenge.data.models.GenerationResult
import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.data.models.PokemonGenerationResponse
import com.example.pokemonappchallenge.domain.response.Result
import com.example.pokemonappchallenge.ui.model.Generation
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemons(): Flow<Result<List<Pokemon>>>

    suspend fun fetchInBackground(): Flow<Result<List<Pokemon>>>

    suspend fun getGenerations(): Flow<Result<List<Generation>>>
}