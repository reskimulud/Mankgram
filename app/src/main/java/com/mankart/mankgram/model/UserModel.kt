package com.mankart.mankgram.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(

	@field:SerializedName("name")
	val name: String? = null,

	val email: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("token")
	val token: String? = null
) : Parcelable
