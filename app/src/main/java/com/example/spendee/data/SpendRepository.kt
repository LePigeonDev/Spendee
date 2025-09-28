package com.example.spendee.data

import com.example.spendee.data.local.Spend
import com.example.spendee.data.local.Category
import com.example.spendee.data.local.SpendDao
import kotlinx.coroutines.flow.Flow

// Intermediaire entre l'UI est la base de donnée
// Permet d'avoir un code modulaire
class SpendRepository(private val dao: SpendDao) {
    val spend = dao.getMonthlySpend()
    val totalSpend = dao.getTotalMonthlySpend()
    val category = dao.getCategory()

    suspend fun categoryUsageBetween(startDate: String, endDate: String) =
        dao.categoryUsageBetween(startDate, endDate)
    suspend fun totalBetween(startMonth: String, endMonth: String) =
        dao.totalBetween(startMonth, endMonth)
    suspend fun categoryIdByName(name: String) =
        dao.getCategoryIdByName(name)
    suspend fun add(amount: Double, categoryId: Int) =
        dao.insertSpend(amount,categoryId)

    suspend fun delete(spend: Spend) =
        dao.deleteSpendById(spend.id)
}
