package com.example.taskb.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("login")
    val loginName: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("repos_url")
    val reposUrl: String
    )