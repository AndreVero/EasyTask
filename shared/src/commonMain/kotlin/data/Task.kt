package data

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val title: String,
    val description: String
)
