package com.example.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.data.CountdownEventEntity
import com.example.data.FolderEntity
import com.example.data.LauncherItemEntity
import com.example.data.QuickNoteEntity
import com.example.data.TodoItemEntity
import com.example.widgets.NothingWidgetHost

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenPageGrid(
    pageIndex: Int,
    columns: Int,
    rows: Int,
    items: List<LauncherItemEntity>,
    folders: List<FolderEntity>,
    itemsInFoldersMap: Map<Long, List<LauncherItemEntity>>,
    iconSizeDp: Dp,
    showLabels: Boolean,
    tempUnit: String,
    is24H: Boolean,
    quickNote: QuickNoteEntity?,
    todos: List<TodoItemEntity>,
    countdown: CountdownEventEntity?,
    onAppClick: (LauncherItemEntity) -> Unit,
    onAppLongClick: (LauncherItemEntity) -> Unit,
    onFolderClick: (FolderEntity) -> Unit,
    onFolderLongClick: (FolderEntity, LauncherItemEntity) -> Unit,
    onEmptySpaceLongClick: () -> Unit,
    onOpenNoteEdit: () -> Unit,
    onToggleTodo: (TodoItemEntity) -> Unit,
    onAddTodo: () -> Unit,
    onOpenCountdownEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = {},
                onLongClick = onEmptySpaceLongClick
            )
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        val gridWidth = maxWidth
        val gridHeight = maxHeight

        val cellWidth = gridWidth / columns
        val cellHeight = gridHeight / rows

        // Place all items on this page
        items.forEach { item ->
            val spanX = item.spanX.coerceIn(1, columns)
            val spanY = item.spanY.coerceIn(1, rows)
            val cellX = item.cellX.coerceIn(0, columns - spanX)
            val cellY = item.cellY.coerceIn(0, rows - spanY)

            val itemOffsetX = cellWidth * cellX
            val itemOffsetY = cellHeight * cellY
            val itemWidth = cellWidth * spanX
            val itemHeight = cellHeight * spanY

            Box(
                modifier = Modifier
                    .offset(x = itemOffsetX, y = itemOffsetY)
                    .size(width = itemWidth, height = itemHeight)
                    .padding(4.dp)
            ) {
                when (item.itemType) {
                    "APP" -> {
                        NormalAppIconItem(
                            item = item,
                            iconSizeDp = iconSizeDp,
                            showLabel = showLabels,
                            onClick = { onAppClick(item) },
                            onLongClick = { onAppLongClick(item) }
                        )
                    }
                    "MAX_ICON" -> {
                        MaxIconItem(
                            item = item,
                            showLabel = showLabels,
                            onClick = { onAppClick(item) },
                            onLongClick = { onAppLongClick(item) }
                        )
                    }
                    "FOLDER" -> {
                        val folder = folders.find { it.id == item.folderId }
                        val appsInFolder = itemsInFoldersMap[item.folderId ?: -1L] ?: emptyList()
                        if (folder != null) {
                            if (folder.isMaxFolder) {
                                MaxFolderItem(
                                    folder = folder,
                                    itemsInFolder = appsInFolder,
                                    onAppClick = onAppClick,
                                    onFolderOpen = { onFolderClick(folder) },
                                    onLongClick = { onFolderLongClick(folder, item) }
                                )
                            } else {
                                NormalFolderItem(
                                    folder = folder,
                                    itemsInFolder = appsInFolder,
                                    iconSizeDp = iconSizeDp,
                                    showLabel = showLabels,
                                    onClick = { onFolderClick(folder) },
                                    onLongClick = { onFolderLongClick(folder, item) }
                                )
                            }
                        }
                    }
                    "MAX_FOLDER" -> {
                        val folder = folders.find { it.id == item.folderId }
                        val appsInFolder = itemsInFoldersMap[item.folderId ?: -1L] ?: emptyList()
                        if (folder != null) {
                            MaxFolderItem(
                                folder = folder,
                                itemsInFolder = appsInFolder,
                                onAppClick = onAppClick,
                                onFolderOpen = { onFolderClick(folder) },
                                onLongClick = { onFolderLongClick(folder, item) }
                            )
                        }
                    }
                    "WIDGET" -> {
                        NothingWidgetHost(
                            item = item,
                            tempUnit = tempUnit,
                            is24H = is24H,
                            note = quickNote,
                            todos = todos,
                            countdown = countdown,
                            onOpenNoteEdit = onOpenNoteEdit,
                            onToggleTodo = onToggleTodo,
                            onAddTodo = onAddTodo,
                            onOpenCountdownEdit = onOpenCountdownEdit
                        )
                    }
                }
            }
        }
    }
}
