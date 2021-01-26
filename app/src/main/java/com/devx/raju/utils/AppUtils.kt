package com.devx.raju.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.view.View
import android.webkit.WebSettings
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.devx.raju.AppConstants
import com.devx.raju.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {
    fun getDate(dateString: String?): String {
        return try {
            val format1 = SimpleDateFormat(AppConstants.DATE_TIME_FORMAT)
            val date = format1.parse(dateString)
            val sdf: DateFormat = SimpleDateFormat("MMM d, yyyy")
            sdf.format(date)
        } catch (ex: Exception) {
            ex.printStackTrace()
            "xx"
        }
    }

    fun getTime(dateString: String?): String {
        return try {
            val format1 = SimpleDateFormat(AppConstants.DATE_TIME_FORMAT)
            val date = format1.parse(dateString)
            val sdf: DateFormat = SimpleDateFormat("h:mm a")
            sdf.format(date)
        } catch (ex: Exception) {
            ex.printStackTrace()
            "xx"
        }
    }

    fun getWebViewCacheMode(context: Context): Int {
        return if (isConnected(context)) WebSettings.LOAD_DEFAULT else WebSettings.LOAD_CACHE_ELSE_NETWORK
    }

    fun isConnected(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connection = manager.activeNetworkInfo
        return if (connection != null && connection.isConnectedOrConnecting) {
            true
        } else false
    }

    fun getTransitionElements(context: Context,
                              imageView: View?,
                              titleView: View?,
                              revealView: View,
                              languageView: View): Array<Pair<*, *>?> {
        val pairs = ArrayList<Pair<View, String>>()
        pairs.add(Pair<View, String>(imageView, context.getString(R.string.transition_image)))
        pairs.add(Pair<View, String>(titleView, context.getString(R.string.transition_title)))
        if (revealView.visibility == View.VISIBLE) Pair<Any?, Any?>(revealView, context.getString(R.string.transition_background))
        if (languageView.visibility == View.VISIBLE) Pair<Any?, Any?>(languageView, context.getString(R.string.transition_language))
        var pairArr = arrayOfNulls<Pair<*, *>?>(pairs.size)
        pairArr = pairs.toArray(pairArr)
        return pairArr
    }

    fun getColorByLanguage(context: Context?,
                           language: String?): Int {
        return if (AppConstants.LANGUAGE_COLOR_MAP.containsKey(language)) ContextCompat.getColor(context!!, AppConstants.LANGUAGE_COLOR_MAP[language]!!) else ContextCompat.getColor(context!!, R.color.color_purple)
    }

    fun updateStatusBarColor(activity: Activity,
                             color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = color
        }
    }

    fun lighten(color: Int, fraction: Double): Int {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        red = lightenColor(red, fraction)
        green = lightenColor(green, fraction)
        blue = lightenColor(blue, fraction)
        val alpha = Color.alpha(color)
        return Color.argb(alpha, red, green, blue)
    }

    private fun lightenColor(color: Int, fraction: Double): Int {
        return Math.min(color + color * fraction, 255.0).toInt()
    }

    fun updateGradientDrawableColor(context: Context,
                                    bgColor: Int): Drawable {
        val drawable = context.resources.getDrawable(R.drawable.ic_circle) as GradientDrawable
        drawable.setColor(bgColor)
        return drawable
    }

    fun updateStateListDrawableColor(stateListDrawable: Drawable,
                                     bgColor: Int): Drawable {
        val drawableContainerState = stateListDrawable.constantState as DrawableContainer.DrawableContainerState
        val children = drawableContainerState.children
        val selectedDrawable = children[0] as GradientDrawable
        val unselectedDrawable = children[1] as GradientDrawable
        selectedDrawable.setColor(lighten(bgColor, 0.1))
        unselectedDrawable.setColor(bgColor)
        return stateListDrawable
    }

    fun updateStateListDrawableStrokeColor(stateListDrawable: Drawable,
                                           bgColor: Int): Drawable {
        val drawableContainerState = stateListDrawable.constantState as DrawableContainer.DrawableContainerState
        val children = drawableContainerState.children
        val selectedDrawable = children[0] as GradientDrawable
        val unselectedDrawable = children[1] as GradientDrawable
        selectedDrawable.setColor(lighten(bgColor, 0.1))
        unselectedDrawable.setStroke(1, bgColor)
        return stateListDrawable
    }
}