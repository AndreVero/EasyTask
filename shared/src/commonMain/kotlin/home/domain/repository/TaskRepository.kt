package home.domain.repository

import home.domain.model.Task

interface TaskRepository {

    suspend fun getTasks() : List<Task>

}