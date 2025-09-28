package com.example.spendee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spendee.data.local.Spend
import com.example.spendee.ui.SpendViewModel

@Composable
fun HomeSpendScreen(
    vm: SpendViewModel,
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit = {},
    onStatClick: () -> Unit = {}
) {
    val spend by vm.spend.collectAsStateWithLifecycle(initialValue = emptyList())
    val totalSpend by vm.totalSpend.collectAsStateWithLifecycle(initialValue = 0.0)

    val gradientBg = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0F172A),
            Color(0xFF111827),
            Color(0xFF0B1220)
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBg)
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .systemBarsPadding()
    ) {
        // En-tête total
        Surface(
            modifier = Modifier.fillMaxWidth(),
            tonalElevation = 8.dp,
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Montant total du mois ",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "$totalSpend €",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))

        // Liste des dépenses
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = spend,
                key = { item -> item.id }
            ) { item ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            Text(
                                item.amount.toString() + " €",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                item.categoryName,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                item.createdAt,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            "🗑",
                            modifier = Modifier
                                .clickable {
                                    val stub = Spend(
                                        id = item.id,
                                        amount = item.amount,
                                        categoryId = 0,
                                        createdAt = item.createdAt
                                    )
                                    vm.deleteSpend(stub)
                                }
                                .padding(8.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }

        // Boutons d’action
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onAddClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text("Ajouter une dépense")
            }

            OutlinedButton(
                onClick = onStatClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Text("Statistiques")
            }
        }
    }
}
