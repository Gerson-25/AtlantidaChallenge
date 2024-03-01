package com.example.pokemonappchallenge.domain.usecases

import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.domain.response.Result
import kotlinx.coroutines.flow.Flow


interface GetPokemonsUseCase {

    suspend operator fun invoke(): Flow<Result<List<Pokemon>>>
}