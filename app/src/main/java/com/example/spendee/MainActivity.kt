package com.example.spendee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.spendee.router.AppNavGraph
import com.example.spendee.ui.SpendViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: SpendViewModel = viewModel()
            val navController = rememberNavController()
            AppNavGraph(navController = navController, vm = vm)
        }
    }
}
