package com.example.vkgif.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    val original: Original,
    val preview_gif: PreviewGif
): Parcelable