package io.github.ziginsider.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import io.github.ziginsider.base.AppResources
import io.github.ziginsider.databinding.CurrencyIdemBinding
import io.github.ziginsider.ui.model.UiCurrency
import java.text.NumberFormat
import java.text.ParseException

class CurrencyViewHolder(
    private val binding: CurrencyIdemBinding,
    private val appResources: AppResources,
    private val listener: CurrencyAdapter.OnClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private var newBaseCurrencyValue: Double? = 0.0
    private var numberFormat: NumberFormat = NumberFormat.getInstance()
    private var newBaseCurrency: UiCurrency? = null
    private var baseCurrencyEditText: EditText? = null
    private var isConversionMode = false

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            if (text.isNullOrEmpty() || !isConversionMode) {
                return
            }
            try {
                newBaseCurrencyValue = numberFormat.parse(text.toString())?.toDouble()
                listener.onRateChanged(newBaseCurrency, newBaseCurrencyValue)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    fun bind(currencyItem: UiCurrency) {
        with(binding) {
            currencyCode.text = currencyItem.code
            currencyDescription.text = appResources.getString(currencyItem.descriptionId)
            currencyFlag.setImageDrawable(appResources.getDrawable(currencyItem.imageId))
            currencyRate.setText(currencyItem.value.toString())

            if (layoutPosition == 0) {
                currencyRate.addTextChangedListener(textWatcher)
                newBaseCurrency = currencyItem
                baseCurrencyEditText = currencyRate
            }

            currencyRate.setOnFocusChangeListener { _, hasFocus ->
                isConversionMode = hasFocus
                if (hasFocus) {
                    listener.onRateClick(currencyItem)

                    if (currencyItem.descriptionId != newBaseCurrency?.descriptionId) {
                        newBaseCurrency = currencyItem
                        baseCurrencyEditText?.removeTextChangedListener(textWatcher)
                        baseCurrencyEditText = currencyRate
                        currencyRate.addTextChangedListener(textWatcher)
                    }
                }
            }
        }
    }
}
