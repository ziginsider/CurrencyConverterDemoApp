package io.github.ziginsider.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.ziginsider.R
import io.github.ziginsider.appComponent
import io.github.ziginsider.base.AppResources
import io.github.ziginsider.databinding.ActivityMainBinding
import io.github.ziginsider.ui.adapter.CurrencyAdapter
import io.github.ziginsider.ui.model.ScreenState
import io.github.ziginsider.ui.model.UiCurrency
import io.github.ziginsider.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CurrencyAdapter.OnClickListener, AppBackgroundListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels() { viewModelFactory }

    private val adapter by lazy { CurrencyAdapter(this, appResources) }

    @Inject
    lateinit var viewModelFactory: MainViewModel.Factory

    @Inject
    lateinit var appResources: AppResources

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUiData()

        viewModel.getCurrency()
    }

    private fun initUiData() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.setHasFixedSize(true)

        lifecycle.addObserver(BackgroundObserver(this))

        initCollectors()
    }

    private fun initCollectors() {
        lifecycleScope.launch {
            viewModel.state
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect(::handle)
        }
    }

    private fun handle(state: ScreenState) {
        when (state) {
            ScreenState.Error -> showError()
            ScreenState.Loading -> showLoading()
            is ScreenState.Success -> showCurrencies(state.currencies, state.isBaseRateChange)
        }
    }

    private fun showError() {
        with(binding) {
            recyclerView.isVisible = false
            progressBar.isVisible = false
            errorMsg.isVisible = true
            errorMsg.text = getString(R.string.error)
        }
    }

    private fun showLoading() {
        with(binding) {
            recyclerView.isVisible = false
            progressBar.isVisible = true
            errorMsg.isVisible = false
        }
    }

    private fun showCurrencies(currencies: List<UiCurrency>, isBaseRateChange: Boolean) {
        with(binding) {
            progressBar.isVisible = false
            errorMsg.isVisible = false
            recyclerView.isVisible = true
            adapter.submitList(currencies)
            if (isBaseRateChange) {
                adapter.handleRateChange()
            }
        }
    }

    override fun onRateClick(item: UiCurrency) {
        viewModel.onItemRateClick(item)
    }

    override fun onRateChanged(item: UiCurrency?, newRate: Double?) {
        viewModel.changeBaseRate(item, newRate)
    }

    override fun appOnForeground() {
        viewModel.startReceivingData()
    }

    override fun appOnBackground() {
        viewModel.stopReceivingData()
    }

    class BackgroundObserver(private val listener: AppBackgroundListener) : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            listener.appOnForeground()
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            listener.appOnBackground()
        }
    }
}

interface AppBackgroundListener {

    fun appOnForeground()

    fun appOnBackground()
}