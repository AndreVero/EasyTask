package tasks.domain.repository

import tasks.domain.model.Task

interface TasksRepository {

    suspend fun getTodayTasks(): Result<List<Task>>

}