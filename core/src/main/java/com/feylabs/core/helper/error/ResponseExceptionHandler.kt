package com.feylabs.core.helper.error

import android.accounts.NetworkErrorException
import com.feylabs.core.helper.wrapper.ResponseState
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.HttpException
import java.io.IOException

object ResponseExceptionHandler {
    suspend fun <T> handleException(
        exception: Exception,
        flow: FlowCollector<ResponseState<T>>
    ) {
        when (exception) {
            is HttpException -> {
                val errorMessage = "HTTP error: ${exception.code()} - ${exception.message()}"
                flow.emit(ResponseState.Error(ErrorResponse(errorMessage)))
            }
            is IOException -> flow.emit(ResponseState.Error(ErrorResponse("Network error")))
            is NetworkErrorException -> flow.emit(ResponseState.Error(ErrorResponse("Network connection error")))
            else -> flow.emit(ResponseState.Error(ErrorResponse("An unknown error occurred")))
        }
    }
}