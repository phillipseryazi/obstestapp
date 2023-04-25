package com.example.obstestapp.utils

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

suspend fun <T : Any> executeApiCall(
    execute: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = execute()

        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(IOException(response.message()))
        }
    } catch (exc: HttpException) {
        Result.failure(exc)
    } catch (exc: IOException) {
        Result.failure(exc)
    } catch (exc: UnknownHostException) {
        Result.failure(exc)
    } catch (exc: NullPointerException) {
        Result.failure(exc)
    }
}
