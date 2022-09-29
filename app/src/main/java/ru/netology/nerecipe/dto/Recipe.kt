package ru.netology.nerecipe.dto

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Long,
    val author: String,
    val title: String,
    val category: String,
    val isFavorite: Boolean = false,
    val indexPosition: Long
)