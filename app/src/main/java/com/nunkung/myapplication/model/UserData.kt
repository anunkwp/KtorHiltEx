package com.nunkung.myapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by「 Nun Kung 」on on 06/03/2022 :)
 */
@Serializable
data class UserData(
    @SerialName("userId") val userId: Int,
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String,
)
