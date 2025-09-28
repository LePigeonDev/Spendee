package com.example.spendee.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendee.data.SpendRepository
import com.example.spendee.data.local.AppDatabase
import com.example.spendee.data.local.Spend
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.spendee.data.local.SpendDao.CategoryUsage


class SpendViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = SpendRepository(AppDatabase.get(app).spendDao())

    val spend = repository.spend.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val totalSpend = repository.totalSpend.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed((5_000)),
        initialValue = 0.00
    )

    val category =  repository.category.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed((5_000)),
        initialValue = emptyList()
    )

    suspend fun categoryIdByName(name: String): Int {
        return repository.categoryIdByName(name)
    }

    suspend fun totalBetween(startMonth: String, endMonth: String): Double {
        return repository.totalBetween(startMonth, endMonth)
    }

    suspend fun categoryUsageBetween(start: String, end: String): List<CategoryUsage> {
        return repository.categoryUsageBetween(start, end)
    }
    fun addSpend(amount: Double, categoryId: Int) = viewModelScope.launch {
        repository.add(amount, categoryId)
    }

    fun deleteSpend(spend: Spend) = viewModelScope.launch {
        repository.delete(spend)
    }


}