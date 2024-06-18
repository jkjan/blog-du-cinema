package com.jun.blogducinemaback.global.types

enum class TimeUnit(private val duration: Long) {
    YEAR(365),
    DAY(365),
    HOUR(24),
    MINUTE(60),
    SECOND(60),
    MILLISECOND(1000);

    fun getLifetimeInMilli(duration: Long): Long {
        var lifetimeInMilli = duration

        for (i in ordinal + 1 ..< entries.size) {
            lifetimeInMilli *= TimeUnit.entries[i].duration
        }

        return lifetimeInMilli
    }
}