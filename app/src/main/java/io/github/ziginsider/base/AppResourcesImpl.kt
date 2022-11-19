package io.github.ziginsider.base

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import javax.inject.Inject

class AppResourcesImpl @Inject constructor(
    private val context: Context
) : AppResources {

    private val resources = context.resources

    override fun getString(@StringRes stringId: Int): String {
        return resources.getString(stringId)
    }

    override fun getStringByName(resourceName: String, @StringRes defaultStringRes: Int): String {
        val resId = getStringResIdByName(resourceName)
        return if (resId != 0) resources.getString(resId) else resources.getString(defaultStringRes)
    }

    override fun getStringByName(resourceName: String): String {
        return resources.getString(getStringResIdByName(resourceName))
    }

    override fun getStringByName(resourceName: String, defaultString: String): String {
        val resId = getStringResIdByName(resourceName)
        return if (resId != 0) resources.getString(resId) else defaultString
    }

    @StringRes
    override fun getStringResIdByName(resourceName: String, @StringRes defaultValue: Int): Int {
        return if (resourceName.isEmpty()) {
            defaultValue
        } else {
            val resId = resources.getIdentifier(resourceName, "string", context.packageName)
            if (resId != 0) resId else defaultValue
        }
    }

    @RawRes
    fun getRawResIdByName(resourceName: String): Int {
        return resources.getIdentifier(resourceName, "raw", context.packageName)
    }

    override fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }
}