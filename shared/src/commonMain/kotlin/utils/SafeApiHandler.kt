package utils

import kotlinx.coroutines.CancellationException

object SafeApiHandler {

    suspend fun <T> safeApiCall(func : suspend () -> T) : Result<T> {
        return try {
            val result = func()
            Result.success(result)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}