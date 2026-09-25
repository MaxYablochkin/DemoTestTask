package com.dev.maxyablochkin.demotaskapp.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "name") val name: String? = null
)