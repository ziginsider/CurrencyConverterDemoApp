package io.github.ziginsider.base

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface AppResources {

    fun getString(@StringRes stringId: Int): String

    fun getStringByName(resourceName: String): String

    fun getStringByName(resourceName: String, @StringRes defaultStringRes: Int): String

    fun getStringByName(resourceName: String, defaultString: String): String

    @StringRes
    fun getStringResIdByName(resourceName: String, @StringRes defaultValue: Int = 0): Int

    fun getDrawable(@DrawableRes resId: Int): Drawable?
}