package com.example.spendee.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


// Entity -> Table dans la base de donnée
@Entity(
    tableName = "spend",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Spend (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "categoryId")
    val categoryId: Int,
    @ColumnInfo(name = "createdAt")
    val createdAt: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        .format(Date())
)