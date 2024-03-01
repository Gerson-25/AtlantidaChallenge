package com.example.pokemonappchallenge.data.utils

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val INITIAL_OFFSET =  0
    const val LIMIT_STARTED = 15
    const val LIMIT = 10

    val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
        "Verbose WorkManager Notifications"
    const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
        "Shows notifications whenever work starts"
    val NOTIFICATION_TITLE: CharSequence = "POKEDEX"
    const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
    const val NOTIFICATION_ID = 1

    // The name of the image manipulation work
    const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

    const val DELAY_TIME_MILLIS: Long = 3000
}