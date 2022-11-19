package io.github.ziginsider.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.github.ziginsider.base.AppResources
import io.github.ziginsider.databinding.CurrencyIdemBinding
import io.github.ziginsider.ui.model.UiCurrency

class CurrencyAdapter(
    private val listener: OnClickListener,
    private val appResources: AppResources
) : ListAdapter<UiCurrency, CurrencyViewHolder>(itemComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = CurrencyIdemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding, appResources, listener)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    fun handleRateChange() {
        notifyItemRangeChanged(1, itemCount - 1)
    }

    interface OnClickListener {

        fun onRateClick(item: UiCurrency)

        fun onRateChanged(item: UiCurrency?, newRate: Double?)
    }

    private companion object {
        private val itemComparator = object : DiffUtil.ItemCallback<UiCurrency>() {

            override fun areItemsTheSame(oldItem: UiCurrency, newItem: UiCurrency): Boolean {
                return oldItem.descriptionId == newItem.descriptionId
            }

            override fun areContentsTheSame(oldItem: UiCurrency, newItem: UiCurrency): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: UiCurrency, newItem: UiCurrency): Any {
                return Any()
            }
        }
    }
}
