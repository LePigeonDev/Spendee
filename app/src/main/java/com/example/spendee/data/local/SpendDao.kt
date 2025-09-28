package com.example.spendee.data.local

import androidx.room.Dao
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface SpendDao {

    data class SpendWithCategory(
        val id: Int,
        val amount: Double,
        val createdAt: String,
        val categoryName: String

    )

    data class CategoryUsage(
        val categoryName: String,
        val usageCount: Int
    )

    @Query("""
        SELECT s.id,
               s.amount,
               s.createdAt,
               c.name AS categoryName
        FROM spend AS s
        INNER JOIN category AS c ON s.categoryId = c.id
        WHERE date(s.createdAt) >= date('now','start of month')
          AND date(s.createdAt) <  date('now','start of month','+1 month')
        ORDER BY s.createdAt DESC
    """)
    fun getMonthlySpend(): Flow<List<SpendWithCategory>>

    @Query("""
        SELECT COALESCE(SUM(s.amount), 0.0)
        FROM spend AS s
        WHERE date(s.createdAt) >= date('now','start of month')
          AND date(s.createdAt) <  date('now','start of month','+1 month')
    """)
    fun getTotalMonthlySpend(): Flow<Double>

    @Query("""
        SELECT COALESCE(SUM(amount), 0)
        FROM Spend
        WHERE createdAt >= :startMonth
          AND createdAt <  :endMonth
    """)
    suspend fun totalBetween(startMonth: String, endMonth: String): Double

    @Query("""
        SELECT c.name AS categoryName,
               COUNT(*) AS usageCount
        FROM spend AS s
        INNER JOIN category AS c ON s.categoryId = c.id
        WHERE s.createdAt >= :startDate
          AND s.createdAt <  :endDate
        GROUP BY c.id
        ORDER BY usageCount DESC
    """)
    suspend fun categoryUsageBetween(startDate: String, endDate: String): List<CategoryUsage>

    @Query("SELECT DISTINCT(name) FROM category ORDER BY name ASC")
    fun getCategory(): Flow<List<String>>

    @Query("INSERT INTO Spend (amount, categoryId, createdAt) VALUES (:amount, :categoryId, datetime('now', 'localtime'))")
    suspend fun insertSpend(amount: Double, categoryId: Int)

    @Query("SELECT id FROM Category WHERE name = :name LIMIT 1")
    suspend fun getCategoryIdByName(name: String): Int

    @Query("DELETE FROM spend WHERE id = :id")
    suspend fun deleteSpendById(id: Int)
}

