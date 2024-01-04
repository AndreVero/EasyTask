package tasks.data.remote.dto

import tasks.domain.model.Task

data class TaskDto(
    val title: String,
    val description: String,
    val icon: String,
    val motivation: String
)

fun TaskDto.toTask() : Task {
    return Task(
        title = this.title,
        description = this.description,
        icon = this.icon,
        motivation = this.motivation
    )
}