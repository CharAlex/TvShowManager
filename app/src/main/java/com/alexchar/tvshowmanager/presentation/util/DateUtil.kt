package com.alexchar.tvshowmanager.presentation.util

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun String.toLocalDate(format: String = "yyyy-MM-dd"): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern(format)
        LocalDate.parse(this, formatter).toString()
    } catch (e: Exception) {
        val something = this.split("T")

        something.first()
    }
}

fun LocalDate.toStringDate(format: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(format)
    return this.format(formatter)
}
