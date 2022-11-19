package io.github.ziginsider.data.interactor

import android.util.Log
import io.github.ziginsider.data.repository.CurrencyRepo
import io.github.ziginsider.ui.model.UiCurrency
import io.github.ziginsider.utils.InteractorUtils.mapServerDataToUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrencyInteractor @Inject constructor(private val repo: CurrencyRepo) {

    fun getUiCurrenciesStream(): Flow<List<UiCurrency>> {
        return repo.getCurrenciesStream()
            .catch { e ->
                e.localizedMessage?.let { Log.e("TAG", it) }
                emit(emptyList())
            }
            .map { currencyData -> mapServerDataToUiData(currencyData) }
            .flowOn(Dispatchers.IO)
    }

    fun startApi() {
        repo.startObtain()
    }

    fun stopApi() {
        repo.stopObtain()
    }
}
