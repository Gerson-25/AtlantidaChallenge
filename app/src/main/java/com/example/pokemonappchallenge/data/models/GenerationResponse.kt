package com.example.pokemonappchallenge.data.models


data class PokemonGenerationResponse(
    val count: Int,
    val next: String?, // Nullable because "next" can be null
    val previous: String?, // Nullable because "previous" can be null
    val results: List<GenerationResult>
)

data class GenerationResult(
    val name: String,
    val url: String
)

data class GenerationDetailsResponse(
    val abilities: List<Any>, // You can replace 'Any' with a more specific type if needed
    val id: Int,
    val main_region: Region,
    val moves: List<Move>,
    val name: String,
    val names: List<Name>,
    val pokemon_species: List<PokemonSpecies>,
    val types: List<Type>,
    val version_groups: List<VersionGroup>
)

data class Region(
    val name: String,
    val url: String
)

data class Move(
    val name: String,
    val url: String
)

data class Name(
    val language: Language,
    val name: String
)

data class Language(
    val name: String,
    val url: String
)

data class PokemonSpecies(
    val name: String,
    val url: String
)

data class Type(
    val name: String,
    val url: String
)

data class VersionGroup(
    val name: String,
    val url: String
)

