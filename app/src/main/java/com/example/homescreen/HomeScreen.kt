package com.example.homescreen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CountdownEventEntity
import com.example.data.FolderEntity
import com.example.data.LauncherItemEntity
import com.example.data.LauncherSettings
import com.example.data.QuickNoteEntity
import com.example.data.TodoItemEntity
import com.example.drawer.AppDrawerScreen
import com.example.model.AppItem
import com.example.settings.SettingsScreen
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.SilkscreenDotMatrix
import com.example.ui.theme.SpaceMonoTech

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NothingHomeScreen(
    settings: LauncherSettings,
    launcherItems: List<LauncherItemEntity>,
    folders: List<FolderEntity>,
    allApps: List<AppItem>,
    quickNote: QuickNoteEntity?,
    todos: List<TodoItemEntity>,
    countdown: CountdownEventEntity?,
    onLaunchApp: (String, String?) -> Unit,
    onToggleMaxIcon: (LauncherItemEntity) -> Unit,
    onToggleMaxFolder: (FolderEntity, LauncherItemEntity) -> Unit,
    onRemoveItem: (LauncherItemEntity) -> Unit,
    onRenameFolder: (Long, String) -> Unit,
    onRemoveAppFromFolder: (LauncherItemEntity) -> Unit,
    onAddAppToHome: (AppItem) -> Unit,
    onAddWidgetToHome: (String) -> Unit,
    onUpdateGrid: (Int, Int) -> Unit,
    onUpdateIconSize: (Int) -> Unit,
    onUpdateShowLabels: (Boolean) -> Unit,
    onUpdateMonochromeIcons: (Boolean) -> Unit,
    onUpdateTempUnit: (String) -> Unit,
    onUpdate24H: (Boolean) -> Unit,
    onUpdateWallpaper: (Int) -> Unit,
    onUpdateSwipeDown: (String) -> Unit,
    onSaveQuickNote: (String) -> Unit,
    onToggleTodo: (TodoItemEntity) -> Unit,
    onAddTodo: (String) -> Unit,
    onResetDefaults: () -> Unit
) {
    val context = LocalContext.current

    // Screen states
    var isDrawerOpen by remember { mutableStateOf(false) }
    var isSettingsOpen by remember { mutableStateOf(false) }

    // Dialog states
    var selectedFolderForDialog by remember { mutableStateOf<FolderEntity?>(null) }
    var selectedItemForOptions by remember { mutableStateOf<LauncherItemEntity?>(null) }
    var selectedFolderForOptions by remember { mutableStateOf<FolderEntity?>(null) }
    var isNoteEditOpen by remember { mutableStateOf(false) }
    var isAddTodoOpen by remember { mutableStateOf(false) }

    // Multi-page Pager (Pages 0 & 1)
    val pageCount = 2
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })

    // Separate items into Page 0, Page 1, and Dock (-1)
    val page0Items = remember(launcherItems) { launcherItems.filter { it.screenIndex == 0 } }
    val page1Items = remember(launcherItems) { launcherItems.filter { it.screenIndex == 1 } }
    val dockItems = remember(launcherItems) { launcherItems.filter { it.screenIndex == -1 } }
    val itemsInFoldersMap = remember(launcherItems) {
        launcherItems.filter { it.folderId != null }.groupBy { it.folderId!! }
    }

    // Gesture handling: Vertical swipe tracking for Drawer (up) and Notifications (down)
    var dragOffsetY by remember { mutableFloatStateOf(0f) }
    val draggableState = rememberDraggableState { delta ->
        dragOffsetY += delta
    }

    fun expandNotificationShade() {
        try {
            val sbservice = context.getSystemService("statusbar")
            val statusbarManager = Class.forName("android.app.StatusBarManager")
            val showsb = statusbarManager.getMethod("expandNotificationsPanel")
            showsb.invoke(sbservice)
        } catch (e: Exception) {
            // Ignore if restricted
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .draggable(
                state = draggableState,
                orientation = Orientation.Vertical,
                onDragStopped = {
                    if (dragOffsetY < -120f) {
                        // Swipe Up -> Open App Drawer
                        isDrawerOpen = true
                    } else if (dragOffsetY > 140f) {
                        // Swipe Down -> Notifications
                        if (settings.swipeDownAction == "NOTIFICATIONS") {
                            expandNotificationShade()
                        } else {
                            isDrawerOpen = true
                        }
                    }
                    dragOffsetY = 0f
                }
            )
    ) {
        // 1. Wallpaper Layer
        NothingWallpaperBackground(wallpaperIndex = settings.wallpaperIndex)

        // 2. Main Launcher Content (Top Bar, Grid Pages, Dock)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 28.dp)
        ) {
            // Top Bar: Nothing dot and Settings Shortcut
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(NothingRed)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "NOTHING (R)",
                        fontFamily = SilkscreenDotMatrix,
                        fontSize = 13.sp,
                        color = NothingWhite,
                        letterSpacing = 1.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(NothingSurface)
                        .border(1.dp, NothingSurfaceBorder, CircleShape)
                        .clickable { isSettingsOpen = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = NothingWhite,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Central Pager Grid Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val pageItems = if (page == 0) page0Items else page1Items
                    HomeScreenPageGrid(
                        pageIndex = page,
                        columns = settings.gridColumns,
                        rows = settings.gridRows,
                        items = pageItems,
                        folders = folders,
                        itemsInFoldersMap = itemsInFoldersMap,
                        iconSizeDp = settings.iconSizeDp.dp,
                        showLabels = settings.showLabels,
                        tempUnit = settings.tempUnit,
                        is24H = settings.time24H,
                        quickNote = quickNote,
                        todos = todos,
                        countdown = countdown,
                        onAppClick = { itm ->
                            if (itm.packageName != null) {
                                onLaunchApp(itm.packageName, itm.activityName)
                            }
                        },
                        onAppLongClick = { itm ->
                            selectedItemForOptions = itm
                            selectedFolderForOptions = null
                        },
                        onFolderClick = { fld ->
                            selectedFolderForDialog = fld
                        },
                        onFolderLongClick = { fld, itm ->
                            selectedFolderForOptions = fld
                            selectedItemForOptions = itm
                        },
                        onEmptySpaceLongClick = {
                            isSettingsOpen = true
                        },
                        onOpenNoteEdit = { isNoteEditOpen = true },
                        onToggleTodo = onToggleTodo,
                        onAddTodo = { isAddTodoOpen = true },
                        onOpenCountdownEdit = { isSettingsOpen = true }
                    )
                }
            }

            // Bottom Dock
            NothingDockView(
                dockItems = dockItems,
                currentPage = pagerState.currentPage,
                totalPages = pageCount,
                iconSizeDp = settings.iconSizeDp.dp,
                onAppClick = { itm ->
                    if (itm.packageName != null) {
                        onLaunchApp(itm.packageName, itm.activityName)
                    }
                },
                onAppLongClick = { itm ->
                    selectedItemForOptions = itm
                    selectedFolderForOptions = null
                },
                onSwipeUpToDrawer = { isDrawerOpen = true }
            )
        }

        // 3. App Drawer Overlay with Slide Animation
        AnimatedVisibility(
            visible = isDrawerOpen,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            AppDrawerScreen(
                allApps = allApps,
                onAppClick = { appItem ->
                    onLaunchApp(appItem.packageName, appItem.activityName)
                    isDrawerOpen = false
                },
                onAppLongClick = { appItem ->
                    onAddAppToHome(appItem)
                    isDrawerOpen = false
                },
                onCloseDrawer = { isDrawerOpen = false }
            )
        }

        // 4. Settings Screen Overlay
        AnimatedVisibility(
            visible = isSettingsOpen,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            SettingsScreen(
                settings = settings,
                onUpdateGrid = onUpdateGrid,
                onUpdateIconSize = onUpdateIconSize,
                onUpdateShowLabels = onUpdateShowLabels,
                onUpdateMonochromeIcons = onUpdateMonochromeIcons,
                onUpdateTempUnit = onUpdateTempUnit,
                onUpdate24H = onUpdate24H,
                onUpdateWallpaper = onUpdateWallpaper,
                onUpdateSwipeDown = onUpdateSwipeDown,
                onAddWidgetToHome = {
                    onAddWidgetToHome(it)
                    isSettingsOpen = false
                },
                onResetDefaults = {
                    onResetDefaults()
                    isSettingsOpen = false
                },
                onBack = { isSettingsOpen = false }
            )
        }

        // 5. Folder Content Dialog
        selectedFolderForDialog?.let { folder ->
            val appsInThisFolder = itemsInFoldersMap[folder.id] ?: emptyList()
            FolderDialog(
                folder = folder,
                itemsInFolder = appsInThisFolder,
                onDismiss = { selectedFolderForDialog = null },
                onRenameFolder = { newName -> onRenameFolder(folder.id, newName) },
                onAppClick = { itm ->
                    if (itm.packageName != null) {
                        onLaunchApp(itm.packageName, itm.activityName)
                    }
                    selectedFolderForDialog = null
                },
                onRemoveFromFolder = { itm ->
                    onRemoveAppFromFolder(itm)
                }
            )
        }

        // 6. Item Options Dialog (Max Icon resize / remove / app info)
        selectedItemForOptions?.let { item ->
            ItemOptionsDialog(
                item = item,
                folder = selectedFolderForOptions,
                onDismiss = {
                    selectedItemForOptions = null
                    selectedFolderForOptions = null
                },
                onToggleMax = {
                    if (selectedFolderForOptions != null) {
                        onToggleMaxFolder(selectedFolderForOptions!!, item)
                    } else {
                        onToggleMaxIcon(item)
                    }
                },
                onRemoveFromHome = {
                    onRemoveItem(item)
                },
                onAddToFolder = {}
            )
        }

        // 7. Quick Note Edit Dialog
        if (isNoteEditOpen) {
            QuickNoteEditDialog(
                initialText = quickNote?.content ?: "",
                onSave = { onSaveQuickNote(it) },
                onDismiss = { isNoteEditOpen = false }
            )
        }

        // 8. Add Todo Dialog
        if (isAddTodoOpen) {
            AddTodoDialog(
                onAdd = { onAddTodo(it) },
                onDismiss = { isAddTodoOpen = false }
            )
        }
    }
}
