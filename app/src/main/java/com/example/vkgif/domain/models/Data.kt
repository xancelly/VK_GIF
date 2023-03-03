package com.example.vkgif.domain.models

data class Data(
    val embed_url: String,
    val id: String,
    val images: Images,
    val import_datetime: String,
    val rating: String,
    val slug: String,
    val source: String,
    val title: String,
    val trending_datetime: String,
    val type: String,
    val url: String,
    val user: User,
    val username: String
)