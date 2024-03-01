package com.example.pokemonappchallenge.data.usecases

import com.example.pokemonappchallenge.data.models.GenerationDetailsResponse
import com.example.pokemonappchallenge.domain.repository.PokemonRepository
import com.example.pokemonappchallenge.domain.response.Result
import com.example.pokemonappchallenge.domain.usecases.GetGenerationsUseCase
import com.example.pokemonappchallenge.ui.model.Generation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenerationsUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
): GetGenerationsUseCase {
    override suspend fun invoke(): Flow<Result<List<Generation>>> {
        return repository.getGenerations()
    }
}