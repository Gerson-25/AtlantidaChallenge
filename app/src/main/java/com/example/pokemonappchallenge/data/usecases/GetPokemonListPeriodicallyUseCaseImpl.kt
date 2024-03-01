package com.example.pokemonappchallenge.data.usecases

import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.domain.repository.PokemonRepository
import com.example.pokemonappchallenge.domain.response.Result
import com.example.pokemonappchallenge.domain.usecases.GetPokemonListPerodicallyUseCase
import com.example.pokemonappchallenge.domain.usecases.GetPokemonsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListPeriodicallyUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
): GetPokemonListPerodicallyUseCase {

    override suspend fun invoke(): Flow<Result<List<Pokemon>>> {
        return repository.fetchInBackground()
    }


}