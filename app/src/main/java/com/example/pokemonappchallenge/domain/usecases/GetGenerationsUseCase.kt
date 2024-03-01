package com.example.pokemonappchallenge.domain.usecases

import com.example.pokemonappchallenge.data.models.GenerationDetailsResponse
import com.example.pokemonappchallenge.domain.response.Result
import com.example.pokemonappchallenge.ui.model.Generation
import kotlinx.coroutines.flow.Flow

interface GetGenerationsUseCase {

    suspend operator fun invoke(): Flow<Result<List<Generation>>>

}