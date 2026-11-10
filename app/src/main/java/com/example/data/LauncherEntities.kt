package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launcher_items")
data class LauncherItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val itemType: String, // "APP", "MAX_ICON", "FOLDER", "MAX_FOLDER", "WIDGET"
    val screenIndex: Int, // 0..N for home pages, -1 for dock
    val cellX: Int,
    val cellY: Int,
    val spanX: Int = 1,
    val spanY: Int = 1,
    val packageName: String? = null,
    val activityName: String? = null,
    val title: String = "",
    val customTitle: String? = null,
    val folderId: Long? = null,
    val widgetType: String? = null,
    val widgetData: String? = null
)

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "Folder",
    val isMaxFolder: Boolean = false,
    val coverIcon: String? = null
)

@Entity(tableName = "todo_items")
data class TodoItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "quick_notes")
data class QuickNoteEntity(
    @PrimaryKey val id: Int = 1,
    val content: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "countdown_events")
data class CountdownEventEntity(
    @PrimaryKey val id: Int = 1,
    val title: String = "LAUNCH EVENT",
    val targetTimestamp: Long = System.currentTimeMillis() + 86400000L * 7
)
