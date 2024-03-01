package com.example.pokemonappchallenge.data.usecases

import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.domain.repository.PokemonRepository
import com.example.pokemonappchallenge.domain.response.Result
import com.example.pokemonappchallenge.domain.usecases.GetPokemonsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonsUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
): GetPokemonsUseCase {

    override suspend fun invoke(): Flow<Result<List<Pokemon>>> {
        return repository.getPokemons()
    }


}