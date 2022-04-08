package com.mankart.mankgram

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryModel(
    val name: String? = null,
    val image: String? = null,
    val description: String? = null
) : Parcelable
