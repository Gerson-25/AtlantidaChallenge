package com.example.pokemonappchallenge.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.processing.Generated

@Entity("pokemon_table")
class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int  = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
)