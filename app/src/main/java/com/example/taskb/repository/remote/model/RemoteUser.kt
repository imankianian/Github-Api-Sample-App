package com.example.taskb.repository.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteUser(

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatarUrl: String)