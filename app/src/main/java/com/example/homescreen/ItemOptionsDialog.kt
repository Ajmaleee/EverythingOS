package com.example.homescreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import com.example.ui.theme.NothingWhiteSecondary
import com.example.ui.theme.SilkscreenDotMatrix
import com.example.ui.theme.SpaceGroteskSans
import com.example.ui.theme.SpaceMonoTech

@Composable
fun ItemOptionsDialog(
    item: LauncherItemEntity,
    folder: FolderEntity?,
    onDismiss: () -> Unit,
    onToggleMax: () -> Unit,
    onRemoveFromHome: () -> Unit,
    onAddToFolder: () -> Unit
) {
    val context = LocalContext.current
    val isMax = item.itemType == "MAX_ICON" || (folder?.isMaxFolder == true)

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(NothingBlack)
                .border(1.5.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(NothingRed)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = item.customTitle ?: item.title.ifBlank { folder?.name ?: "ITEM" },
                        fontFamily = SilkscreenDotMatrix,
                        fontSize = 16.sp,
                        color = NothingWhite
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Option 1: Toggle Max Icon / Max Folder
                if (item.itemType == "APP" || item.itemType == "MAX_ICON" || item.itemType == "FOLDER" || item.itemType == "MAX_FOLDER") {
                    DialogOptionRow(
                        icon = if (isMax) Icons.Default.ZoomOut else Icons.Default.ZoomIn,
                        label = if (isMax) "MINIMIZE TO 1x1" else "MAX RESIZE (2x2 NOTHING STYLE)",
                        onClick = {
                            onToggleMax()
                            onDismiss()
                        }
                    )
                }

                // Option 2: Remove from Home
                DialogOptionRow(
                    icon = Icons.Default.Delete,
                    label = "REMOVE FROM HOME",
                    onClick = {
                        onRemoveFromHome()
                        onDismiss()
                    }
                )

                // Option 3: App info
                if (!item.packageName.isNullOrBlank()) {
                    DialogOptionRow(
                        icon = Icons.Default.Info,
                        label = "APP INFO",
                        onClick = {
                            try {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.parse("package:${item.packageName}")
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                context.startActivity(intent)
                            } catch (e: Exception) {}
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DialogOptionRow(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = NothingWhite,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = label,
            fontFamily = SpaceGroteskSans,
            fontSize = 13.sp,
            color = NothingWhiteSecondary,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}
