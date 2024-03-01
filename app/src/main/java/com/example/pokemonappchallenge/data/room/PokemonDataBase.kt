package com.example.pokemonappchallenge.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemonappchallenge.data.entities.PokemonEntity
import javax.inject.Inject

@Database(entities = arrayOf(PokemonEntity::class), version = 1, exportSchema = false)
public abstract class PokemonDataBase: RoomDatabase() {

    abstract fun dao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: PokemonDataBase? = null

        fun getDataBase(context: Context): PokemonDataBase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonDataBase::class.java,
                    "pokemon_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}

@Database(entities = [PokemonEntity::class], version = 2, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}