package io.github.ziginsider.ui.model

sealed class ScreenState {
    class Success(val currencies: List<UiCurrency>, val isBaseRateChange: Boolean) : ScreenState()
    object Error : ScreenState()
    object Loading : ScreenState()
}
