package com.flatcode.simplecomposeapps.blogger.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [BloggerPostEntity::class, BloggerPageEntity::class, BloggerCommentEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BloggerTypeConverters::class)
abstract class BloggerDatabase : RoomDatabase() {
    abstract fun bloggerDao(): BloggerDao
}
