package com.example.taskb.repository.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteRepo(

    @SerializedName("name")
    val name: String,

    @SerializedName("updated_at")
    val lastUpdate: String,

    @SerializedName("stargazers_count")
    val stars: Int,

    @SerializedName("language")
    val language: String?,

    @SerializedName("html_url")
    val htmlUrl: String)
