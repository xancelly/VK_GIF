package com.example.vkgif.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Original(
    val height: String,
    val url: String,
    val width: String
): Parcelable