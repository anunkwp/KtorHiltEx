package com.nunkung.myapplication.repository

import com.nunkung.myapplication.model.UserPostData
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

/**
 * Created by「 Nun Kung 」on on 07/03/2022 :)
 */
class MainRepository @Inject constructor(private val client: HttpClient) {

    suspend fun getUserKtor(): MutableList<UserPostData> = client.get("https://jsonplaceholder.typicode.com/posts")
}