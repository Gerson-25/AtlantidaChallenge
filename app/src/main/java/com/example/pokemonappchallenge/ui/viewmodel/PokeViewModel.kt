package com.example.pokemonappchallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.domain.datasource.PokeWorkDataSource
import com.example.pokemonappchallenge.domain.response.Result
import com.example.pokemonappchallenge.domain.usecases.GetGenerationsUseCase
import com.example.pokemonappchallenge.domain.usecases.GetPokemonsUseCase
import com.example.pokemonappchallenge.ui.model.Generation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonsUseCase,
    private val getGenerationsUseCase: GetGenerationsUseCase
): ViewModel() {

    private var _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var _pokemonList = MutableLiveData<List<Pokemon>> ()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private var _generations = MutableLiveData<List<Generation>> ()
    val generations: LiveData<List<Generation>> = _generations

    fun getPokemonList() = viewModelScope.launch(Dispatchers.IO) {
        getPokemonListUseCase.invoke().collectLatest { response ->
            withContext(Dispatchers.Main)
            {
                when (response) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                    is Result.Success -> {
                        _isLoading.value = false
                        _pokemonList.value = response.data ?: listOf()
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                    }
                }
            }
        }
    }

    fun getGenerations() = viewModelScope.launch(Dispatchers.IO) {
        getGenerationsUseCase.invoke().collectLatest {
            withContext(Dispatchers.Main){
                when(it){
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        _generations.value =  it.data ?: listOf()
                    }
                    is Result.Error -> {

                    }
                }
            }

        }
    }

}