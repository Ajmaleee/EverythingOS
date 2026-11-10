package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LauncherDao {
    @Query("SELECT * FROM launcher_items ORDER BY screenIndex ASC, cellY ASC, cellX ASC")
    fun getAllLauncherItemsFlow(): Flow<List<LauncherItemEntity>>

    @Query("SELECT * FROM launcher_items WHERE screenIndex = :screenIndex")
    suspend fun getItemsOnScreen(screenIndex: Int): List<LauncherItemEntity>

    @Query("SELECT * FROM launcher_items WHERE folderId = :folderId ORDER BY cellY ASC, cellX ASC")
    fun getItemsInFolderFlow(folderId: Long): Flow<List<LauncherItemEntity>>

    @Query("SELECT * FROM launcher_items WHERE folderId = :folderId ORDER BY cellY ASC, cellX ASC")
    suspend fun getItemsInFolder(folderId: Long): List<LauncherItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLauncherItem(item: LauncherItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLauncherItems(items: List<LauncherItemEntity>): List<Long>

    @Update
    suspend fun updateLauncherItem(item: LauncherItemEntity)

    @Delete
    suspend fun deleteLauncherItem(item: LauncherItemEntity)

    @Query("DELETE FROM launcher_items WHERE id = :itemId")
    suspend fun deleteLauncherItemById(itemId: Long)

    @Query("DELETE FROM launcher_items WHERE folderId = :folderId")
    suspend fun deleteItemsInFolder(folderId: Long)

    @Query("DELETE FROM launcher_items WHERE packageName = :packageName")
    suspend fun deleteItemsForPackage(packageName: String)

    // Folders
    @Query("SELECT * FROM folders")
    fun getAllFoldersFlow(): Flow<List<FolderEntity>>

    @Query("SELECT * FROM folders WHERE id = :folderId")
    suspend fun getFolderById(folderId: Long): FolderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: FolderEntity): Long

    @Update
    suspend fun updateFolder(folder: FolderEntity)

    @Query("DELETE FROM folders WHERE id = :folderId")
    suspend fun deleteFolder(folderId: Long)

    // Productivity: Todo
    @Query("SELECT * FROM todo_items ORDER BY createdAt DESC")
    fun getAllTodosFlow(): Flow<List<TodoItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoItemEntity): Long

    @Update
    suspend fun updateTodo(todo: TodoItemEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoItemEntity)

    // Productivity: Quick Notes
    @Query("SELECT * FROM quick_notes WHERE id = 1")
    fun getQuickNoteFlow(): Flow<QuickNoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateQuickNote(note: QuickNoteEntity)

    // Countdown
    @Query("SELECT * FROM countdown_events WHERE id = 1")
    fun getCountdownFlow(): Flow<CountdownEventEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCountdown(event: CountdownEventEntity)
}
