package io.github.ziginsider.data.api.model

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("date")
    val date: String,
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("base")
    val base: String
)
