package com.example.spendee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.spendee.data.local.SpendDao
import com.example.spendee.ui.SpendViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsSpendScreen(
    vm: SpendViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    var startDate by remember { mutableStateOf(Date()) }
    var endDate by remember { mutableStateOf(Date()) }

    var total by remember { mutableStateOf(0.0) }
    var usages by remember { mutableStateOf(listOf<SpendDao.CategoryUsage>()) }

    val uiFormatter = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val dbFormatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }
    val scope = rememberCoroutineScope()

    fun startOfDayString(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return dbFormatter.format(cal.time)
    }

    fun nextDayStartString(date: Date): String {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        cal.add(Calendar.DATE, 1)
        return dbFormatter.format(cal.time)
    }

    val gradientBg = Brush.verticalGradient(
        listOf(
            Color(0xFF0F172A), // bleu nuit
            Color(0xFF111827), // ardoise
            Color(0xFF0B1220)  // profond
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Statistiques", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color(0xFF60A5FA)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color(0xFF60A5FA)
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBg)
                .padding(innerPadding)
        ) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Période", style = MaterialTheme.typography.titleMedium)

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        OutlinedButton(onClick = { showStartPicker = true }) {
                            Text("Début : ${uiFormatter.format(startDate)}")
                        }
                        OutlinedButton(onClick = { showEndPicker = true }) {
                            Text("Fin : ${uiFormatter.format(endDate)}")
                        }
                    }

                    Button(onClick = {
                        val startStr = startOfDayString(startDate)
                        val endStr = nextDayStartString(endDate)
                        scope.launch {
                            total = vm.totalBetween(startStr, endStr)
                            usages = vm.categoryUsageBetween(startStr, endStr)
                        }
                    }) {
                        Text("Calculer")
                    }

                    Text(
                        "Dépense totale : %.2f €".format(Locale.getDefault(), total),
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (usages.isNotEmpty()) {
                        val totalCount = usages.sumOf { it.usageCount }
                        Text("Catégories les plus utilisées", style = MaterialTheme.typography.titleMedium)

                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Catégorie", style = MaterialTheme.typography.labelLarge)
                            Text("%", style = MaterialTheme.typography.labelLarge)
                        }
                        Divider()

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(usages) { row ->
                                val pct = if (totalCount == 0) 0.0
                                else (row.usageCount.toDouble() * 100.0 / totalCount.toDouble())

                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(row.categoryName)
                                    Text(String.format(Locale.getDefault(), "%.1f%%", pct))
                                }
                                Divider()
                            }
                        }
                    } else {
                        Text("Aucune dépense sur cette période.")
                    }
                }
            }
        }
    }

    if (showStartPicker) {
        val state = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showStartPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { millis ->
                        val now = System.currentTimeMillis()
                        startDate = if (millis <= now) Date(millis) else Date(now)
                    }
                    showStartPicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showStartPicker = false }) { Text("Annuler") } }
        ) {
            DatePicker(state = state)
        }
    }

    if (showEndPicker) {
        val state = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showEndPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { millis ->
                        val now = System.currentTimeMillis()
                        endDate = if (millis <= now) Date(millis) else Date(now)
                    }
                    showEndPicker = false
                }) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showEndPicker = false }) { Text("Annuler") } }
        ) {
            DatePicker(state = state)
        }
    }
}
