package com.nunkung.myapplication.network

import com.nunkung.myapplication.model.UserPostData
import io.ktor.client.*
import io.ktor.client.request.*

/**
 * Created by「 Nun Kung 」on on 06/03/2022 :)
 */
class UserApi (private val client : HttpClient){

    suspend fun getUserKtor(): MutableList<UserPostData> = client.get("https://jsonplaceholder.typicode.com/posts")

    suspend fun getAlbums(): MutableList<UserPostData> = client.get("https://jsonplaceholder.typicode.com/albums")

}