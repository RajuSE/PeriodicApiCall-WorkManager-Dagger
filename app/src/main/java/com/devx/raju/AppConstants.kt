package com.devx.raju

import java.util.*

interface AppConstants {
    companion object {
        const val QUERY_SORT = "stars"
        const val QUERY_ORDER = "desc"
        const val QUERY_API = "android"
        const val PAGE_MAX_SIZE = "6"
        const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        const val INTENT_POST = "intent_post"
        val LANGUAGE_COLOR_MAP: MutableMap<String, Int> = Collections.unmodifiableMap<String, Int>(
                object : HashMap<String, Int>() {
                    init {
                        put("Java", R.color.color_orange)
                        put("Kotlin", R.color.colorPrimary)
                        put("Dart", R.color.colorPrimaryDark)
                        put("JavaScript", R.color.color_yellow)
                        put("CSS", R.color.color_purple)
                    }
                })
    }
}