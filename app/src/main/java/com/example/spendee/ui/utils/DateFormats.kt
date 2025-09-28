package com.example.spendee.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val df = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.getDefault())

fun formatDate(epochMillis: Long): String = df.format(Date(epochMillis))
