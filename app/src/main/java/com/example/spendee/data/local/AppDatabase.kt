package com.example.spendee.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Spend::class, Category::class], version = 5, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun spendDao(): SpendDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        private const val SEED_PATH = "databases/populate.sql"

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "spendee.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            val sql = context.assets.open(SEED_PATH).bufferedReader().use { it.readText() }
                            val statements = sql
                                .lineSequence()
                                .map { it.trim() }
                                .filter { it.isNotEmpty() && !it.startsWith("--") }
                                .joinToString("\n")
                                .split(';')
                                .map { it.trim() }
                                .filter { it.isNotEmpty() }

                            db.beginTransaction()
                            try {
                                statements.forEach { db.execSQL(it) }
                                db.setTransactionSuccessful()
                            } finally {
                                db.endTransaction()
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
