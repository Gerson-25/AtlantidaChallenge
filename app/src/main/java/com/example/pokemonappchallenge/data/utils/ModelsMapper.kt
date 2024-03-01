package com.example.pokemonappchallenge.data.utils

import com.example.pokemonappchallenge.data.entities.PokemonEntity
import com.example.pokemonappchallenge.data.models.GenerationDetailsResponse
import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.ui.model.Generation

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        name = name,
        url = url
    )
}

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        url = url
    )
}

fun GenerationDetailsResponse.toUI(): Generation {
    return Generation(
        name= name,
        enable = false,
        pokemonList = pokemon_species.map {
                                          it.url
        },
        start = pokemon_species.map {
            it.url.getPokemonId()
        }.sortedBy {
            it
        }[0],
        end = pokemon_species.map {
            it.url.getPokemonId()
        }.sortedBy {
            it
        }.last()
    )
}
