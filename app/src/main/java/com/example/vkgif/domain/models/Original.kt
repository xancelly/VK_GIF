package com.example.vkgif.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Original(
    val frames: String,
    val hash: String,
    val height: String,
    val size: String,
    val url: String,
    val width: String
): Parcelable