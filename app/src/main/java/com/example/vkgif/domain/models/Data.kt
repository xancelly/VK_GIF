package com.example.vkgif.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val embed_url: String,
    val id: String,
    val images: Images,
    val import_datetime: String,
    val title: String,
    val url: String,
    val user: User
): Parcelable