package com.example.spendee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.collectAsState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.spendee.ui.SpendViewModel
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextField
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSpendScreen(
    vm: SpendViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var amount by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf("") }
    val categories by vm.category.collectAsState(initial = emptyList())

    // pour message d’erreur
    var showCategoryError by rememberSaveable { mutableStateOf(false) }

    val amountRegex = Regex("""^\d*\.?\d{0,2}$""")

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
                title = {
                    Text(
                        "Nouvelle dépense",
                        color = Color.White
                    )
                },
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
        // Fond dégradé + carte formulaire
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
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { new ->
                            if (new.isEmpty() || new.matches(amountRegex)) {
                                amount = new
                            }
                        },
                        label = { Text("Montant") },
                        placeholder = { Text("Ex. 12.50") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    // ----- Menu déroulant Catégorie -----
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = selectedText,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Catégorie") },
                            placeholder = { Text("Sélectionner une catégorie") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            singleLine = true
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { label ->
                                DropdownMenuItem(
                                    text = { Text(label) },
                                    onClick = {
                                        selectedText = label
                                        category = label
                                        expanded = false
                                        showCategoryError = false // réinitialiser l’erreur
                                    }
                                )
                            }
                        }
                    }

                    // message d’erreur si nécessaire
                    if (showCategoryError) {
                        Text(
                            text = "Veuillez choisir une catégorie",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(Modifier.height(4.dp))

                    // ----- Bouton Ajouter -----
                    Button(
                        onClick = {
                            if (category.isBlank()) {
                                showCategoryError = true
                            } else {
                                scope.launch {
                                    val idCategory = vm.categoryIdByName(category)
                                    vm.addSpend(amount.toDouble(), idCategory)
                                    amount = ""
                                    category = ""
                                    selectedText = ""
                                    showCategoryError = false
                                    onBack()
                                }
                            }
                        },
                        enabled = amount.isNotBlank() && category.isNotBlank(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text("Ajouter")
                    }
                }
            }
        }
    }
}
