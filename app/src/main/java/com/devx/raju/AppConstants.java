package com.devx.raju;

import com.devx.raju.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface AppConstants {

    String QUERY_SORT = "stars";
    String QUERY_ORDER = "desc";
    String QUERY_API = "android";

    String PAGE_MAX_SIZE = "6";
    String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    String INTENT_POST = "intent_post";

    Map<String, Integer> LANGUAGE_COLOR_MAP = Collections.unmodifiableMap(
            new HashMap<String, Integer>() {{
                put("Java", R.color.color_orange);
                put("Kotlin", R.color.colorPrimary);
                put("Dart", R.color.colorPrimaryDark);
                put("JavaScript", R.color.color_yellow);
                put("CSS", R.color.color_purple);
            }});
}
