package io.github.ziginsider.data.repository

import io.github.ziginsider.base.AppConstants.TIME_REFRESH_MS
import io.github.ziginsider.data.api.ApiService
import io.github.ziginsider.data.api.model.Currency
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepo @Inject constructor(private val apiService: ApiService) {

    private var isObtain = true

    fun getCurrenciesStream(): Flow<List<Currency>> {
        return flow {
            while (true) {
                if (isObtain) {
                    val currencies = apiService.getCurrencies()
                    emit(currencies)
                    delay(TIME_REFRESH_MS)
                }
            }
        }
    }

    fun startObtain() {
        isObtain = true
    }

    fun stopObtain() {
        isObtain = false
    }
}
