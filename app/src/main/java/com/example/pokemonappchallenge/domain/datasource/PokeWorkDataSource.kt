package com.example.pokemonappchallenge.domain.datasource


interface PokeWorkDataSource {
    suspend fun getPokemonList()
}