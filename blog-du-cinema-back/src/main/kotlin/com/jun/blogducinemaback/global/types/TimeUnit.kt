package com.jun.blogducinemaback.global.types

enum class TimeUnit(private val type: String, private val duration: Long) {
    YEAR("year", 365),
    DAY("day", 365),
    HOUR("hour", 24),
    MINUTE("minute", 60),
    SECOND("second", 60),
    MILLISECOND("millisecond", 1000);

    fun getLifetimeInMilli(duration: Long): Long {
        var lifetimeInMilli = 0L
        var mulitiply = false

        for (timeUnit in entries) {
            if (timeUnit.type == type) {
                mulitiply = true
                lifetimeInMilli = duration
                continue
            }
            if (mulitiply) {
                lifetimeInMilli *= timeUnit.duration
            }
        }
        return lifetimeInMilli
    }
}