package com.mankart.mankgram.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryModel(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("photoUrl")
    val image: String? = null,

    @field:SerializedName("description")
    val description: String? = null
) : Parcelable
