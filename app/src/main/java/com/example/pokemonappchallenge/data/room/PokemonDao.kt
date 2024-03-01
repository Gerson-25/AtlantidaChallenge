package com.example.pokemonappchallenge.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonappchallenge.data.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao{

    @Query("SELECT * FROM pokemon_table ORDER BY name ASC")
    fun getPokemons(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun savePokemons(list: List<PokemonEntity>)

}