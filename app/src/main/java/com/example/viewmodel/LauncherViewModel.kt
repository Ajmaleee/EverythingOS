package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.NothingLauncherApp
import com.example.data.CountdownEventEntity
import com.example.data.FolderEntity
import com.example.data.LauncherItemEntity
import com.example.data.LauncherSettings
import com.example.data.QuickNoteEntity
import com.example.data.TodoItemEntity
import com.example.icon.IconRegistry
import com.example.model.AppCategory
import com.example.model.AppItem
import com.example.receiver.PackageReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LauncherViewModel(application: Application) : AndroidViewModel(application) {

    private val app = application as NothingLauncherApp
    private val dao = app.database.launcherDao()
    private val settingsRepo = app.settingsRepository

    val settings: StateFlow<LauncherSettings> = settingsRepo.settingsFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, LauncherSettings())

    val launcherItems: StateFlow<List<LauncherItemEntity>> = dao.getAllLauncherItemsFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val folders: StateFlow<List<FolderEntity>> = dao.getAllFoldersFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val todos: StateFlow<List<TodoItemEntity>> = dao.getAllTodosFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val quickNote: StateFlow<QuickNoteEntity?> = dao.getQuickNoteFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val countdown: StateFlow<CountdownEventEntity?> = dao.getCountdownFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _installedApps = MutableStateFlow<List<AppItem>>(emptyList())
    val installedApps: StateFlow<List<AppItem>> = _installedApps.asStateFlow()

    init {
        loadInstalledApps()

        viewModelScope.launch {
            PackageReceiver.packageEvents.collect {
                loadInstalledApps()
            }
        }

        viewModelScope.launch {
            checkAndSeedDefaultLayout()
        }
    }

    fun loadInstalledApps() {
        viewModelScope.launch(Dispatchers.IO) {
            val pm = getApplication<Application>().packageManager
            val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val resolveInfos: List<ResolveInfo> = pm.queryIntentActivities(mainIntent, 0)
            val currentPkg = getApplication<Application>().packageName

            val apps = resolveInfos.mapNotNull { ri ->
                val pkgName = ri.activityInfo.packageName
                if (pkgName == currentPkg) return@mapNotNull null // Don't list launcher inside its own drawer

                val actName = ri.activityInfo.name
                val label = ri.loadLabel(pm).toString()
                val iconName = IconRegistry.getIconNameForPackage(pkgName)
                val category = categorizeApp(pkgName, label)

                AppItem(
                    packageName = pkgName,
                    activityName = actName,
                    label = label,
                    iconName = iconName,
                    category = category
                )
            }.sortedBy { it.label.lowercase() }

            _installedApps.value = apps
        }
    }

    private fun categorizeApp(pkg: String, label: String): AppCategory {
        val p = pkg.lowercase()
        val l = label.lowercase()
        return when {
            p.contains("whatsapp") || p.contains("telegram") || p.contains("mms") || p.contains("messaging") || p.contains("dialer") || p.contains("phone") || p.contains("contacts") -> AppCategory.COMMUNICATION
            p.contains("discord") || p.contains("instagram") || p.contains("reddit") || p.contains("snapchat") || p.contains("twitter") || p.contains("facebook") || p.contains("tiktok") -> AppCategory.SOCIAL
            p.contains("youtube") || p.contains("spotify") || p.contains("vlc") || p.contains("music") || p.contains("video") || p.contains("netflix") || p.contains("game") || p.contains("roblox") -> AppCategory.MEDIA
            p.contains("doc") || p.contains("sheet") || p.contains("keep") || p.contains("calendar") || p.contains("notes") || p.contains("chatgpt") || p.contains("claude") || p.contains("mail") || p.contains("gmail") || p.contains("github") -> AppCategory.PRODUCTIVITY
            p.contains("settings") || p.contains("system") || p.contains("launcher") || p.contains("android") -> AppCategory.SYSTEM
            else -> AppCategory.UTILITIES
        }
    }

    private suspend fun checkAndSeedDefaultLayout() = withContext(Dispatchers.IO) {
        val existing = dao.getItemsOnScreen(0)
        if (existing.isEmpty()) {
            val initialItems = mutableListOf<LauncherItemEntity>()

            // 1. Digital Clock Widget (2x2) at Top (0, 0)
            initialItems.add(
                LauncherItemEntity(
                    itemType = "WIDGET",
                    screenIndex = 0,
                    cellX = 0,
                    cellY = 0,
                    spanX = 2,
                    spanY = 2,
                    widgetType = "DIGITAL_CLOCK",
                    title = "Digital Clock"
                )
            )

            // 2. Weather Widget (2x2) at Top-Right (2, 0)
            initialItems.add(
                LauncherItemEntity(
                    itemType = "WIDGET",
                    screenIndex = 0,
                    cellX = 2,
                    cellY = 0,
                    spanX = 2,
                    spanY = 2,
                    widgetType = "WEATHER",
                    title = "Weather"
                )
            )

            // 3. Quick Note Widget (2x2) at (0, 2)
            initialItems.add(
                LauncherItemEntity(
                    itemType = "WIDGET",
                    screenIndex = 0,
                    cellX = 0,
                    cellY = 2,
                    spanX = 2,
                    spanY = 2,
                    widgetType = "NOTES",
                    title = "Quick Note"
                )
            )

            // 4. Battery Ring Widget (2x2) at (2, 2)
            initialItems.add(
                LauncherItemEntity(
                    itemType = "WIDGET",
                    screenIndex = 0,
                    cellX = 2,
                    cellY = 2,
                    spanX = 2,
                    spanY = 2,
                    widgetType = "BATTERY",
                    title = "Battery"
                )
            )

            // 5. Default Priority App Icons at Row 4 (1x1 each)
            val row4Apps = listOf(
                "com.android.chrome" to "Chrome",
                "com.google.android.youtube" to "YouTube",
                "com.android.settings" to "Settings",
                "com.google.android.apps.photos" to "Photos"
            )
            row4Apps.forEachIndexed { col, (pkg, label) ->
                initialItems.add(
                    LauncherItemEntity(
                        itemType = "APP",
                        screenIndex = 0,
                        cellX = col,
                        cellY = 4,
                        spanX = 1,
                        spanY = 1,
                        packageName = pkg,
                        title = label
                    )
                )
            }

            // 6. Dock Apps (screenIndex = -1, cells 0..3)
            val dockApps = listOf(
                "com.google.android.dialer" to "Phone",
                "com.google.android.apps.messaging" to "Messages",
                "com.android.camera" to "Camera",
                "com.spotify.music" to "Spotify"
            )
            dockApps.forEachIndexed { col, (pkg, label) ->
                initialItems.add(
                    LauncherItemEntity(
                        itemType = "APP",
                        screenIndex = -1,
                        cellX = col,
                        cellY = 0,
                        spanX = 1,
                        spanY = 1,
                        packageName = pkg,
                        title = label
                    )
                )
            }

            dao.insertLauncherItems(initialItems)

            // Seed initial sample note & todo
            dao.insertOrUpdateQuickNote(
                QuickNoteEntity(
                    id = 1,
                    content = "Nothing OS Launcher for Nokia TA-1004. Monochrome icons, Max folders & widgets loaded."
                )
            )
            dao.insertTodo(TodoItemEntity(text = "Experience Nothing dot matrix UI", isCompleted = true))
            dao.insertTodo(TodoItemEntity(text = "Try Max Icon 2x2 resize", isCompleted = false))
            dao.insertTodo(TodoItemEntity(text = "Swipe up for smart app drawer", isCompleted = false))
        }
    }

    fun toggleMaxIcon(item: LauncherItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val isCurrentlyMax = item.itemType == "MAX_ICON"
            val newItem = if (isCurrentlyMax) {
                item.copy(itemType = "APP", spanX = 1, spanY = 1)
            } else {
                item.copy(itemType = "MAX_ICON", spanX = 2, spanY = 2)
            }
            dao.updateLauncherItem(newItem)
        }
    }

    fun toggleMaxFolder(folder: FolderEntity, folderItem: LauncherItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val newIsMax = !folder.isMaxFolder
            dao.updateFolder(folder.copy(isMaxFolder = newIsMax))
            val newSpan = if (newIsMax) 2 else 1
            dao.updateLauncherItem(folderItem.copy(spanX = newSpan, spanY = newSpan))
        }
    }

    fun removeItem(item: LauncherItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteLauncherItem(item)
            if (item.folderId != null) {
                dao.deleteItemsInFolder(item.folderId)
                dao.deleteFolder(item.folderId)
            }
        }
    }

    fun renameFolder(folderId: Long, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val folder = dao.getFolderById(folderId)
            if (folder != null) {
                dao.updateFolder(folder.copy(name = newName))
            }
        }
    }

    fun removeAppFromFolder(item: LauncherItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteLauncherItem(item)
        }
    }

    fun addAppToHomeScreen(appItem: AppItem) {
        viewModelScope.launch(Dispatchers.IO) {
            // Find next empty cell on page 0
            val existing = dao.getItemsOnScreen(0)
            val occupied = mutableSetOf<Pair<Int, Int>>()
            existing.forEach { itm ->
                for (x in itm.cellX until (itm.cellX + itm.spanX)) {
                    for (y in itm.cellY until (itm.cellY + itm.spanY)) {
                        occupied.add(x to y)
                    }
                }
            }

            var targetCell: Pair<Int, Int>? = null
            for (y in 0 until 6) {
                for (x in 0 until 4) {
                    if (!occupied.contains(x to y)) {
                        targetCell = x to y
                        break
                    }
                }
                if (targetCell != null) break
            }

            val (cx, cy) = targetCell ?: (0 to 5)
            dao.insertLauncherItem(
                LauncherItemEntity(
                    itemType = "APP",
                    screenIndex = 0,
                    cellX = cx,
                    cellY = cy,
                    spanX = 1,
                    spanY = 1,
                    packageName = appItem.packageName,
                    activityName = appItem.activityName,
                    title = appItem.label
                )
            )
        }
    }

    fun addWidgetToHome(widgetType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Add to page 1 or available spot
            dao.insertLauncherItem(
                LauncherItemEntity(
                    itemType = "WIDGET",
                    screenIndex = 0,
                    cellX = 0,
                    cellY = 4,
                    spanX = 2,
                    spanY = 2,
                    widgetType = widgetType,
                    title = widgetType
                )
            )
        }
    }

    fun updateGrid(columns: Int, rows: Int) {
        viewModelScope.launch { settingsRepo.updateGrid(columns, rows) }
    }

    fun updateIconSize(sizeDp: Int) {
        viewModelScope.launch { settingsRepo.updateIconSize(sizeDp) }
    }

    fun updateShowLabels(show: Boolean) {
        viewModelScope.launch { settingsRepo.updateShowLabels(show) }
    }

    fun updateMonochromeIcons(enabled: Boolean) {
        viewModelScope.launch { settingsRepo.updateMonochromeIcons(enabled) }
    }

    fun updateTempUnit(unit: String) {
        viewModelScope.launch { settingsRepo.updateTempUnit(unit) }
    }

    fun updateTime24H(is24H: Boolean) {
        viewModelScope.launch { settingsRepo.updateTime24H(is24H) }
    }

    fun updateWallpaper(index: Int) {
        viewModelScope.launch { settingsRepo.updateWallpaper(index) }
    }

    fun updateSwipeDown(action: String) {
        viewModelScope.launch { settingsRepo.updateSwipeDownAction(action) }
    }

    fun saveQuickNote(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertOrUpdateQuickNote(QuickNoteEntity(id = 1, content = content))
        }
    }

    fun toggleTodo(todo: TodoItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateTodo(todo.copy(isCompleted = !todo.isCompleted))
        }
    }

    fun addTodo(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertTodo(TodoItemEntity(text = text, isCompleted = false))
        }
    }

    fun updateCountdown(title: String, targetTimestamp: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertOrUpdateCountdown(CountdownEventEntity(id = 1, title = title, targetTimestamp = targetTimestamp))
        }
    }

    fun resetToDefaults() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getItemsOnScreen(0).forEach { dao.deleteLauncherItem(it) }
            dao.getItemsOnScreen(-1).forEach { dao.deleteLauncherItem(it) }
            checkAndSeedDefaultLayout()
        }
    }
}
