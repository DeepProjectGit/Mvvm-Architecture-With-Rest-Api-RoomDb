package com.example.task2.data.resposnse

import com.squareup.moshi.Json

data class ResponseItem(

	@Json(name="id")
	val id: Int? = null,

	@Json(name="title")
	val title: String? = null,

	@Json(name="body")
	val body: String? = null,

	@Json(name="userId")
	val userId: Int? = null
)
