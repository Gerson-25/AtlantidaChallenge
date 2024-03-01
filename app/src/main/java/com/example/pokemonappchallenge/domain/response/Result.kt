package com.example.pokemonappchallenge.domain.response



sealed class Result<out T> (
    val status: Status = Status.LOADING,
    val data: T? = null,
    val message: String = "") {

    class Loading<T>: Result<T>()

    class Success<T>(data: T, status: Status): Result<T>(status, data)

    class Error<T>(status: Status, message: String): Result<T>(status, message = message)
}