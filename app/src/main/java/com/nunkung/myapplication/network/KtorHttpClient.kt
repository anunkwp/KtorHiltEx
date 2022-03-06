package com.nunkung.myapplication.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.client.statement.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders

/**
 * Created by「 Nun Kung 」on on 06/03/2022 :)
 */
object KtorHttpClient {
    private const val TIME_OUT = 60_000
    val ktorHttpClient = HttpClient(Android) {

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })

            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("", message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value
                println("HTTP status: $statusCode")
                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response)
                    in 400..499 -> throw ClientRequestException(response)
                    in 500..599 -> throw ServerResponseException(response)
                }

                if (statusCode >= 600) {
                    throw ResponseException(response)
                }
            }
            handleResponseException { cause: Throwable ->
                throw cause
            }
        }
    }
}