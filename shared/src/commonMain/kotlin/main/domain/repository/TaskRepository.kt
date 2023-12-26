package main.domain.repository

import main.domain.model.Task

interface TaskRepository {

    suspend fun getTasks() : List<Task>

}