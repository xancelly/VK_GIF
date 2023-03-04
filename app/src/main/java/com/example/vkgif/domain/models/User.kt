package com.example.vkgif.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val avatar_url: String,
    val display_name: String,
    val profile_url: String,
): Parcelable