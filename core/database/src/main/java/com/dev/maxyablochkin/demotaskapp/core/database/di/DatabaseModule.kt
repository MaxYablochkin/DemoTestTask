package com.dev.maxyablochkin.demotaskapp.core.database.di

import android.content.Context
import androidx.room3.Room
import com.dev.maxyablochkin.demotaskapp.core.database.ArticleDatabase
import com.dev.maxyablochkin.demotaskapp.core.database.dao.ArticleDao
import org.koin.dsl.module
import org.koin.plugin.module.dsl.create

fun provideDatabase(context: Context): ArticleDatabase =
    Room.databaseBuilder(
        context.applicationContext,
        ArticleDatabase::class.java,
        ArticleDatabase.NAME_DATABASE
    ).fallbackToDestructiveMigration(true).build()

fun provideArticleDao(db: ArticleDatabase): ArticleDao = db.articleDao()

val coreDatabaseModule = module {
    single { create(::provideDatabase) }
    single { create(::provideArticleDao) }
}
