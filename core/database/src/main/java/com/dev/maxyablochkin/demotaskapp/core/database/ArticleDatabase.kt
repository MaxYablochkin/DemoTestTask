package com.dev.maxyablochkin.demotaskapp.core.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.dev.maxyablochkin.demotaskapp.core.database.dao.ArticleDao
import com.dev.maxyablochkin.demotaskapp.core.database.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 6,
    exportSchema = false
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        const val NAME_DATABASE = "Article_Database"
    }
}
