package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        LauncherItemEntity::class,
        FolderEntity::class,
        TodoItemEntity::class,
        QuickNoteEntity::class,
        CountdownEventEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LauncherDatabase : RoomDatabase() {
    abstract fun launcherDao(): LauncherDao

    companion object {
        @Volatile
        private var INSTANCE: LauncherDatabase? = null

        fun getInstance(context: Context): LauncherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LauncherDatabase::class.java,
                    "nothing_launcher.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
