package home.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import home.domain.model.Task
import home.domain.repository.TaskRepository

class TaskRepositoryImpl : TaskRepository {

    override suspend fun getTasks(): List<Task> {
        val firebaseFirestore = Firebase.firestore
        try {
            val userResponse =
                firebaseFirestore.collection("Tasks").get()
            return userResponse.documents.map {
                it.data()
            }
        } catch (e: Exception) {
            throw e
        }
    }

}