package com.example.pokemonappchallenge.data.models

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Pokemon>
)

data class Pokemon(
    var id: Int = 0,
    val name: String,
    val url: String
)
