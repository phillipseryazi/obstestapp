package com.example.obstestapp.domain

data class Games(
    val city: String,
    val year: Int,
    var athletes: List<Athlete> = emptyList()
)

data class Athlete(
    val athleteId: Int,
    val name: String,
    val surname: String,
    val dob: String,
    val bio: String,
    val weight: Int,
    val height: Int,
    val photoId: Int,
    val results: List<Results>
)

data class Results(
    val city: String,
    val year: Int,
    val gold: Int,
    val silver: Int,
    val bronze: Int,
    val score: Int
)
