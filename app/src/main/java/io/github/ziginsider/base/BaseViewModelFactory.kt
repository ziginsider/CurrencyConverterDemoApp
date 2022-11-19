package io.github.ziginsider.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory<T>(private val viewModelProvider: Provider<T>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }
}
