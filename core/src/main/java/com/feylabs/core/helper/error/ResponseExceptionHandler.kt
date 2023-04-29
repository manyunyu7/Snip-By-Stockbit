package com.feylabs.core.helper.error

import android.accounts.NetworkErrorException
import com.feylabs.core.helper.wrapper.ResponseState
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

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
            is SocketTimeoutException -> {
                flow.emit(ResponseState.Error(ErrorResponse("Socket timeout error")))
            }
            is ConnectException -> {
                flow.emit(ResponseState.Error(ErrorResponse("Connection error")))
            }
            is UnknownHostException -> {
                flow.emit(ResponseState.Error(ErrorResponse("Unknown host error")))
            }
            is IOException -> {
                flow.emit(ResponseState.Error(ErrorResponse("Network error")))
            }
            is NetworkErrorException -> {
                flow.emit(ResponseState.Error(ErrorResponse("Network connection error")))
            }
            else -> {
                flow.emit(ResponseState.Error(ErrorResponse("An unknown error occurred")))
            }
        }
    }
}