package com.alexchar.tvshowmanager.domain.util

fun Any?.toDateString(): String? { //todo change here the actual date parsing
    return try {
        this.toString()
    } catch (e: Exception) {
        null
    }
}