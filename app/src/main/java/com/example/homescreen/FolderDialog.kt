package com.example.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.FolderEntity
import com.example.data.LauncherItemEntity
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.SilkscreenDotMatrix
import com.example.ui.theme.SpaceMonoTech

@Composable
fun FolderDialog(
    folder: FolderEntity,
    itemsInFolder: List<LauncherItemEntity>,
    onDismiss: () -> Unit,
    onRenameFolder: (String) -> Unit,
    onAppClick: (LauncherItemEntity) -> Unit,
    onRemoveFromFolder: (LauncherItemEntity) -> Unit
) {
    var folderName by remember { mutableStateOf(folder.name) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(NothingBlack)
                .border(1.5.dp, NothingSurfaceBorder, RoundedCornerShape(28.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Row: Editable Dot Matrix Title + Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(NothingRed)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        BasicTextField(
                            value = folderName,
                            onValueChange = {
                                folderName = it
                                onRenameFolder(it)
                            },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontFamily = SilkscreenDotMatrix,
                                fontSize = 18.sp,
                                color = NothingWhite
                            ),
                            cursorBrush = SolidColor(NothingRed)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(NothingSurface)
                            .clickable(onClick = onDismiss),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = NothingWhite,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Grid of items in folder
                if (itemsInFolder.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "NO APPS IN FOLDER",
                            fontFamily = SpaceMonoTech,
                            fontSize = 12.sp,
                            color = NothingWhiteMuted
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(itemsInFolder, key = { it.id }) { item ->
                            NormalAppIconItem(
                                item = item,
                                iconSizeDp = 52.dp,
                                showLabel = true,
                                onClick = {
                                    onAppClick(item)
                                    onDismiss()
                                },
                                onLongClick = { onRemoveFromFolder(item) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "LONG-PRESS APP TO REMOVE FROM FOLDER",
                    fontFamily = SpaceMonoTech,
                    fontSize = 9.sp,
                    color = NothingWhiteMuted
                )
            }
        }
    }
}
