package com.example.proj3

data class Event(
    val id: Long,
    val title: String,
    val createDate: String,
    val annotation: String,
    val message: String,
    val imageUrl: String?
) : java.io.Serializable