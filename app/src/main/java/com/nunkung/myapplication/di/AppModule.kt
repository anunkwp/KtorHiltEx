package com.nunkung.myapplication.di

import android.util.Log
import com.nunkung.myapplication.repository.MainRepository
import com.nunkung.myapplication.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Singleton

/**
 * Created by「 Nun Kung 」on on 07/03/2022 :)
 */

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    private const val TIME_OUT = 60_000

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
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

    @Provides
    @Singleton
    fun provideMainRepository(client: HttpClient): MainRepository {
        return MainRepository(client)
    }

    @Provides
    @Singleton
    fun provideMainViewModel(repository: MainRepository) : MainViewModel{
        return MainViewModel(repository)
    }
}