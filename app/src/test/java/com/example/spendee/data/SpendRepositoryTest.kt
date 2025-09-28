package com.example.spendee.data

import com.example.spendee.data.local.SpendDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

// DAO factice sans Room, pour un test unitaire simple
private class FakeSpendDao : SpendDao {
    data class Row(val id:Int, val amount:Double, val categoryId:Int, val createdAt:String)
    private val rows = mutableListOf(
        Row(1, 10.0, 1, "2025-01-10 10:00:00"),
        Row(2,  5.5, 2, "2025-01-11 09:00:00")
    )
    private val monthly = MutableStateFlow<List<SpendDao.SpendWithCategory>>(emptyList())
    private val total = MutableStateFlow(0.0)
    private val cats = MutableStateFlow(listOf("Alimentation", "Transport"))

    init { refresh() }
    private fun refresh() {
        val list = rows.map {
            SpendDao.SpendWithCategory(
                id = it.id, amount = it.amount, createdAt = it.createdAt,
                categoryName = if (it.categoryId==1) "Alimentation" else "Transport"
            )
        }
        monthly.value = list
        total.value = list.sumOf { it.amount }
    }

    override fun getMonthlySpend(): Flow<List<SpendDao.SpendWithCategory>> = monthly.asStateFlow()
    override fun getTotalMonthlySpend(): Flow<Double> = total.asStateFlow()
    override suspend fun totalBetween(startMonth: String, endMonth: String): Double = rows.sumOf { it.amount }
    override suspend fun categoryUsageBetween(startDate: String, endDate: String)
            = listOf(SpendDao.CategoryUsage("Alimentation", 1), SpendDao.CategoryUsage("Transport", 1))
    override fun getCategory(): Flow<List<String>> = cats.asStateFlow()
    override suspend fun insertSpend(amount: Double, categoryId: Int) { rows += Row(rows.size+1, amount, categoryId, "2025-01-12 08:00:00"); refresh() }
    override suspend fun getCategoryIdByName(name: String): Int = if (name=="Alimentation") 1 else 2
    override suspend fun deleteSpendById(id: Int) { /* no-op */ }
}

class SpendRepositoryTest {
    @Test
    fun categoryIdByName_ok() = runBlocking {
        val repo = SpendRepository(FakeSpendDao())
        assertEquals(2, repo.categoryIdByName("Transport"))
    }

    @Test
    fun add_augmente_totalBetween() = runBlocking {
        val repo = SpendRepository(FakeSpendDao())
        val before = repo.totalBetween("2000-01-01 00:00:00","2100-01-01 00:00:00")
        repo.add(4.5, 2)
        val after  = repo.totalBetween("2000-01-01 00:00:00","2100-01-01 00:00:00")
        assertEquals(before + 4.5, after, 1e-6)
    }
}
