package com.agendaprobeauty.app.core.util

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToLong

object MoneyUtils {
    private val brCurrency = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    fun format(cents: Long): String = brCurrency.format(cents / 100.0)

    fun parseToCents(value: String): Long {
        val normalized = value
            .replace("R$", "")
            .replace(".", "")
            .replace(",", ".")
            .trim()
        return ((normalized.toDoubleOrNull() ?: 0.0) * 100).roundToLong()
    }
}
