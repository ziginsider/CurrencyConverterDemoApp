package io.github.ziginsider.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.github.ziginsider.base.AppConstants.ROUND_NUMBER_DECIMAL_PLACES
import io.github.ziginsider.base.BaseViewModelFactory
import io.github.ziginsider.data.interactor.CurrencyInteractor
import io.github.ziginsider.ui.model.ScreenState
import io.github.ziginsider.ui.model.UiCurrency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject
import javax.inject.Provider

class MainViewModel @Inject constructor(
    private val interactor: CurrencyInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<ScreenState>(ScreenState.Loading)

    val state: StateFlow<ScreenState> = _state.asStateFlow()

    private var isConversionMode = false

    fun startReceivingData() {
        if (!isConversionMode) {
            interactor.startApi()
        }
    }

    fun stopReceivingData() {
        interactor.stopApi()
    }

    fun onItemRateClick(item: UiCurrency) {
        isConversionMode = true
        stopReceivingData()
        if (_state.value is ScreenState.Success) {
            val newCurrencies: CopyOnWriteArrayList<UiCurrency> =
                CopyOnWriteArrayList((_state.value as ScreenState.Success).currencies)
            newCurrencies.forEach { currency ->
                if (currency.descriptionId == item.descriptionId) {
                    newCurrencies.remove(currency).also {
                        newCurrencies.add(0, currency)
                    }
                }
            }
            _state.value = ScreenState.Success(newCurrencies, false)
        }
    }

    fun getCurrency() {
        viewModelScope.launch {
            interactor.getUiCurrenciesStream()
                .catch { e ->
                    e.localizedMessage?.let { Log.e("TAG", it) }
                    emit(emptyList())
                }
                .collectLatest { currencies ->
                    _state.value = if (currencies.isNotEmpty()) {
                        ScreenState.Success(currencies, false)
                    } else {
                        ScreenState.Error
                    }
                }
        }
    }

    fun changeBaseRate(currency: UiCurrency?, newRate: Double?) {
        val currency = currency ?: return
        val newRate = newRate ?: 1.0
        val multiplier = newRate / currency.value
        if (_state.value is ScreenState.Success) {
            val newCurrencies: CopyOnWriteArrayList<UiCurrency> =
                CopyOnWriteArrayList((_state.value as ScreenState.Success).currencies)

            newCurrencies.forEach { newCurrency ->
                if (currency.descriptionId != newCurrency.descriptionId) {
                    newCurrency.value =
                        BigDecimal(newCurrency.value * multiplier).setScale(
                            ROUND_NUMBER_DECIMAL_PLACES,
                            RoundingMode.HALF_EVEN
                        ).toDouble()
                } else {
                    newCurrency.value = newRate
                }
            }

            _state.value = ScreenState.Success(newCurrencies, true)
        }
    }

    class Factory @Inject constructor(
        provider: Provider<MainViewModel>
    ) : ViewModelProvider.Factory by BaseViewModelFactory(provider)
}
