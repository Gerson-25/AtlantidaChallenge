package com.example.pokemonappchallenge.data.room

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokemonappchallenge.data.entities.PokemonEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.Console
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {
    private lateinit var dao: PokemonDao
    private lateinit var dataBase: AppDataBase

    @Before
    fun createDataBase(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        dataBase = Room.inMemoryDatabaseBuilder(
            appContext, AppDataBase::class.java
        ).build()

        dao = dataBase.pokemonDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        dataBase.close()
    }

    @Test
    @Throws(Exception::class)
    fun assert_pokemon_is_added() = runBlocking{
        val pokemonList = listOf(
            PokemonEntity(name = "pikachu", url = "https://google.com")
        )

        dao.savePokemons(pokemonList)
        val pokemon = dao.getPokemons().first()

        assertEquals(pokemon[0].name, "pikachu")
    }

    @Test
    @Throws(Exception::class)
    fun assert_save_same_id_element() = runBlocking {
        val pokemonList = listOf(
            PokemonEntity(name = "pikachu", url = "https://google.com"),
            PokemonEntity(name = "squirrel", url = "https://google.com"),
            PokemonEntity(name = "bulbasor", url = "https://google.com")
        )

        dao.savePokemons(pokemonList)
        dao.savePokemons(pokemonList)
        val pokemon = dao.getPokemons().first()

        assertEquals(pokemon.filter { it.id == 1 }[0].name, "pikachu")
    }
}