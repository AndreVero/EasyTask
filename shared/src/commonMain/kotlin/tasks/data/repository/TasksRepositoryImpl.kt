package tasks.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import tasks.data.remote.dto.TaskDto
import tasks.data.remote.dto.toTask
import tasks.domain.model.Task
import tasks.domain.repository.TasksRepository
import utils.SafeApiHandler

class TasksRepositoryImpl : TasksRepository {

    override suspend fun getTodayTasks(): Result<List<Task>> {
        val firebaseFirestore = Firebase.firestore
        return SafeApiHandler.safeApiCall {
            val userResponse = firebaseFirestore.collection("TodayTasks").get()
            val tasks = userResponse.documents.map<DocumentSnapshot, TaskDto> { it.data() }
            tasks.map { it.toTask() }
        }
    }

}