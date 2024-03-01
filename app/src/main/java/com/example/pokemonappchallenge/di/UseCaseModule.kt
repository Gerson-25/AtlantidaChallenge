package com.example.pokemonappchallenge.di

import com.example.pokemonappchallenge.data.usecases.GetGenerationsUseCaseImpl
import com.example.pokemonappchallenge.data.usecases.GetPokemonListPeriodicallyUseCaseImpl
import com.example.pokemonappchallenge.data.usecases.GetPokemonsUseCaseImpl
import com.example.pokemonappchallenge.domain.repository.PokemonRepository
import com.example.pokemonappchallenge.domain.usecases.GetGenerationsUseCase
import com.example.pokemonappchallenge.domain.usecases.GetPokemonListPerodicallyUseCase
import com.example.pokemonappchallenge.domain.usecases.GetPokemonsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetPokemonListUseCase(repository: PokemonRepository): GetPokemonsUseCase = GetPokemonsUseCaseImpl(repository = repository)

    @Singleton
    @Provides
    fun providesGetListPeriodicallyUseCase(repository: PokemonRepository): GetPokemonListPerodicallyUseCase = GetPokemonListPeriodicallyUseCaseImpl(repository)

    @Singleton
    @Provides
    fun provideGetGenerationsUseCase(repository: PokemonRepository): GetGenerationsUseCase = GetGenerationsUseCaseImpl(repository)
}