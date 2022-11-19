package io.github.ziginsider.data.api

import io.github.ziginsider.data.api.model.Currency
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("currency")
    suspend fun getCurrencies(): List<Currency>

    @GET("currency/{base}")
    suspend fun getCurrencyByBase(@Path("base") base: String): Currency
}
