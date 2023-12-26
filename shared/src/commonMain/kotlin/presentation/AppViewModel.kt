package presentation

import data.Task
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier

class AppViewModel : ViewModel() {

    suspend fun getTasks(): List<Task> {
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