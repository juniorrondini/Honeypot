package com.agendaprobeauty.app.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {
    private val zoneId: ZoneId = ZoneId.systemDefault()
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    private val inputDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM")

    fun now(): Long = System.currentTimeMillis()

    fun today(): LocalDate = LocalDate.now(zoneId)

    fun startOfDay(date: LocalDate): Long = date.atStartOfDay(zoneId).toInstant().toEpochMilli()

    fun endOfDay(date: LocalDate): Long = date.plusDays(1).atStartOfDay(zoneId).toInstant().toEpochMilli()

    fun startOfCurrentMonth(): Long = YearMonth.now(zoneId).atDay(1).atStartOfDay(zoneId).toInstant().toEpochMilli()

    fun startOfNextMonth(): Long = YearMonth.now(zoneId).plusMonths(1).atDay(1).atStartOfDay(zoneId).toInstant().toEpochMilli()

    fun currentMonthKey(): String = YearMonth.now(zoneId).format(monthFormatter)

    fun millisFor(date: LocalDate, time: LocalTime): Long =
        LocalDateTime.of(date, time).atZone(zoneId).toInstant().toEpochMilli()

    fun formatDate(date: LocalDate): String = date.format(dateFormatter)

    fun formatInputDate(date: LocalDate): String = date.format(inputDateFormatter)

    fun parseInputDate(value: String): LocalDate = LocalDate.parse(value, inputDateFormatter)

    fun parseInputTime(value: String): LocalTime = LocalTime.parse(value, timeFormatter)

    fun formatTime(millis: Long): String =
        LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(millis), zoneId).toLocalTime().format(timeFormatter)
}
