package com.example.pokemonappchallenge.ui.model

data class Generation(
    val name: String,
    val start: Int = 0,
    val end: Int = 0,
    val enable: Boolean = false,
    val pokemonList: List<String>
) {
    fun setStart(){
        var ids = mutableListOf<Int>()
        for (url in pokemonList){
            subtractId(url)
        }
    }

    private fun subtractId(url: String): Int {
        return url.substring(
            url.indexOf("es/")
        ).toInt()

    }
}