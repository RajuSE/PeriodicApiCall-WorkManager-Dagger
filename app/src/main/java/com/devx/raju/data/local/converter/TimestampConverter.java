package com.devx.raju.data.local.converter;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.devx.raju.AppConstants.DATE_TIME_FORMAT;


public class TimestampConverter {

    private static DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);

    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                TimeZone timeZone = TimeZone.getTimeZone("IST");
                df.setTimeZone(timeZone);
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }


    @TypeConverter
    public static String dateToTimestamp(Date value) {
        TimeZone timeZone = TimeZone.getTimeZone("IST");
        df.setTimeZone(timeZone);
        return value == null ? null : df.format(value);
    }
}