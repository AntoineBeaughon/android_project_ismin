package com.ismin.csproject

import java.io.Serializable

data class Fountain(
    val id: String,
    val tObject: String,
    val modele: String,
    val numVoie: String,
    val voie: String,
    val commune: String,
    val disponibility: Boolean,
    val fav: Boolean,
    val geoPoint2d: List<Double>
) : Serializable