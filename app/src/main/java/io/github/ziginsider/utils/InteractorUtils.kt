package io.github.ziginsider.utils

import io.github.ziginsider.R
import io.github.ziginsider.data.api.model.Currency
import io.github.ziginsider.ui.model.UiCurrency
import kotlin.math.roundToInt

object InteractorUtils {

    private val uiCurrencyMap = hashMapOf<String, Pair<Int, Int>>()

    init {
        uiCurrencyMap[CurrencyCode.USD.toString()] = Pair(R.drawable.ic_usd_flag, R.string.currency_usd_name)
        uiCurrencyMap[CurrencyCode.EUR.toString()] = Pair(R.drawable.ic_eur_flag, R.string.currency_eur_name)
        uiCurrencyMap[CurrencyCode.INR.toString()] = Pair(R.drawable.ic_inr_flag, R.string.currency_inr_name)
        uiCurrencyMap[CurrencyCode.CHF.toString()] = Pair(R.drawable.ic_chf_flag, R.string.currency_chf_name)
        uiCurrencyMap[CurrencyCode.HRK.toString()] = Pair(R.drawable.ic_hrk_flag, R.string.currency_hrk_name)
        uiCurrencyMap[CurrencyCode.MXN.toString()] = Pair(R.drawable.ic_mxn_flag, R.string.currency_mxn_name)
        uiCurrencyMap[CurrencyCode.ZAR.toString()] = Pair(R.drawable.ic_zar_flag, R.string.currency_zar_name)
        uiCurrencyMap[CurrencyCode.CNY.toString()] = Pair(R.drawable.ic_cny_flag, R.string.currency_cny_name)
        uiCurrencyMap[CurrencyCode.THB.toString()] = Pair(R.drawable.ic_thb_flag, R.string.currency_thb_name)
        uiCurrencyMap[CurrencyCode.AUD.toString()] = Pair(R.drawable.ic_aud_flag, R.string.currency_aud_name)
        uiCurrencyMap[CurrencyCode.ILS.toString()] = Pair(R.drawable.ic_ils_flag, R.string.currency_ils_name)
        uiCurrencyMap[CurrencyCode.KRW.toString()] = Pair(R.drawable.ic_krw_flag, R.string.currency_krw_name)
        uiCurrencyMap[CurrencyCode.JPY.toString()] = Pair(R.drawable.ic_jpy_flag, R.string.currency_jpy_name)
        uiCurrencyMap[CurrencyCode.PLN.toString()] = Pair(R.drawable.ic_pln_flag, R.string.currency_pln_name)
        uiCurrencyMap[CurrencyCode.GBP.toString()] = Pair(R.drawable.ic_gbp_flag, R.string.currency_gbp_name)
        uiCurrencyMap[CurrencyCode.IDR.toString()] = Pair(R.drawable.ic_idr_flag, R.string.currency_idr_name)
        uiCurrencyMap[CurrencyCode.HUF.toString()] = Pair(R.drawable.ic_huf_flag, R.string.currency_huf_name)
        uiCurrencyMap[CurrencyCode.PHP.toString()] = Pair(R.drawable.ic_php_flag, R.string.currency_php_name)
        uiCurrencyMap[CurrencyCode.TRY.toString()] = Pair(R.drawable.ic_try_flag, R.string.currency_try_name)
        uiCurrencyMap[CurrencyCode.RUB.toString()] = Pair(R.drawable.ic_rub_flag, R.string.currency_rub_name)
        uiCurrencyMap[CurrencyCode.HKD.toString()] = Pair(R.drawable.ic_hkd_flag, R.string.currency_hkd_name)
        uiCurrencyMap[CurrencyCode.ISK.toString()] = Pair(R.drawable.ic_isk_flag, R.string.currency_isk_name)
        uiCurrencyMap[CurrencyCode.DKK.toString()] = Pair(R.drawable.ic_dkk_flag, R.string.currency_dkk_name)
        uiCurrencyMap[CurrencyCode.CAD.toString()] = Pair(R.drawable.ic_cad_flag, R.string.currency_cad_name)
        uiCurrencyMap[CurrencyCode.MYR.toString()] = Pair(R.drawable.ic_myr_flag, R.string.currency_myr_name)
        uiCurrencyMap[CurrencyCode.BGN.toString()] = Pair(R.drawable.ic_bgn_flag, R.string.currency_bgn_name)
        uiCurrencyMap[CurrencyCode.NOK.toString()] = Pair(R.drawable.ic_nok_flag, R.string.currency_nok_name)
        uiCurrencyMap[CurrencyCode.RON.toString()] = Pair(R.drawable.ic_ron_flag, R.string.currency_ron_name)
        uiCurrencyMap[CurrencyCode.SGD.toString()] = Pair(R.drawable.ic_sgd_flag, R.string.currency_sgd_name)
        uiCurrencyMap[CurrencyCode.CZK.toString()] = Pair(R.drawable.ic_czk_flag, R.string.currency_czk_name)
        uiCurrencyMap[CurrencyCode.SEK.toString()] = Pair(R.drawable.ic_sek_flag, R.string.currency_sek_name)
        uiCurrencyMap[CurrencyCode.NZD.toString()] = Pair(R.drawable.ic_nzd_flag, R.string.currency_nzd_name)
        uiCurrencyMap[CurrencyCode.BRL.toString()] = Pair(R.drawable.ic_brl_flag, R.string.currency_brl_name)
    }

    fun mapServerDataToUiData(currencies: List<Currency>): List<UiCurrency> {
        val uiCurrencyList: MutableList<UiCurrency> = arrayListOf()
        uiCurrencyMap.forEach { (key, value) ->
            uiCurrencyList.add(
                UiCurrency(
                    value.first,
                    value.second,
                    key,
                    ((currencies.find { currency -> currency.base == key }?.rate
                        ?: 0.0) * 10000000).roundToInt().toDouble() / 100000,
                )
            )
        }
        return uiCurrencyList
    }
}