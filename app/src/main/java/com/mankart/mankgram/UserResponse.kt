package com.mankart.mankgram

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("loginResult")
	val loginResult: UserModel? = null

) : Parcelable
