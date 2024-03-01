package com.example.pokemonappchallenge.data.utils

fun String.getPokemonId(): Int{
    return this.substringAfterLast( "es/").substringBefore("/").toInt()
}