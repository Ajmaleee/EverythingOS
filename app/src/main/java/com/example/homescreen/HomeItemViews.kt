@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.example.homescreen

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.NothingLauncherApp
import com.example.data.FolderEntity
import com.example.data.LauncherItemEntity
import com.example.icon.IconRegistry
import com.example.icon.VectorIconDrawables
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteFaint
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.SpaceGroteskSans
import com.example.ui.theme.SpaceMonoTech

@Composable
fun NormalAppIconItem(
    item: LauncherItemEntity,
    iconSizeDp: Dp,
    showLabel: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val app = context.applicationContext as? NothingLauncherApp
    var monoBitmap by remember(item.packageName) { mutableStateOf<Bitmap?>(null) }
    val customIconName = item.packageName?.let { IconRegistry.getIconNameForPackage(it) }

    LaunchedEffect(item.packageName) {
        if (customIconName == null && item.packageName != null && app != null) {
            monoBitmap = app.iconEngine.getMonochromeIcon(item.packageName)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon container: Nothing style circular black pill
        Box(
            modifier = Modifier
                .size(iconSizeDp)
                .clip(CircleShape)
                .background(NothingSurface)
                .border(1.dp, NothingSurfaceBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (customIconName != null) {
                Canvas(modifier = Modifier.size(iconSizeDp * 0.65f)) {
                    VectorIconDrawables.drawIcon(customIconName, this, NothingWhite)
                }
            } else if (monoBitmap != null) {
                Image(
                    bitmap = monoBitmap!!.asImageBitmap(),
                    contentDescription = item.title,
                    modifier = Modifier.size(iconSizeDp * 0.68f)
                )
            } else {
                // Fallback: Initial letter in SpaceMonoTech inside Nothing squircle
                Text(
                    text = item.title.take(1).uppercase(),
                    fontFamily = SpaceMonoTech,
                    fontSize = 18.sp,
                    color = NothingWhite
                )
            }
        }

        if (showLabel) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.customTitle ?: item.title,
                fontFamily = SpaceGroteskSans,
                fontSize = 11.sp,
                color = NothingWhite,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MaxIconItem(
    item: LauncherItemEntity,
    showLabel: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val app = context.applicationContext as? NothingLauncherApp
    var monoBitmap by remember(item.packageName) { mutableStateOf<Bitmap?>(null) }
    val customIconName = item.packageName?.let { IconRegistry.getIconNameForPackage(it) }

    LaunchedEffect(item.packageName) {
        if (customIconName == null && item.packageName != null && app != null) {
            monoBitmap = app.iconEngine.getMonochromeIcon(item.packageName)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(28.dp))
            .background(NothingSurface)
            .border(1.5.dp, NothingSurfaceBorder, RoundedCornerShape(28.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(NothingBlack)
                    .border(1.dp, NothingSurfaceBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (customIconName != null) {
                    Canvas(modifier = Modifier.size(54.dp)) {
                        VectorIconDrawables.drawIcon(customIconName, this, NothingWhite)
                    }
                } else if (monoBitmap != null) {
                    Image(
                        bitmap = monoBitmap!!.asImageBitmap(),
                        contentDescription = item.title,
                        modifier = Modifier.size(56.dp)
                    )
                } else {
                    Text(
                        text = item.title.take(1).uppercase(),
                        fontFamily = SpaceMonoTech,
                        fontSize = 32.sp,
                        color = NothingWhite
                    )
                }
            }

            if (showLabel) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(NothingRed)
                    )
                    Text(
                        text = " ${item.customTitle ?: item.title}",
                        fontFamily = SpaceGroteskSans,
                        fontSize = 12.sp,
                        color = NothingWhite,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun NormalFolderItem(
    folder: FolderEntity,
    itemsInFolder: List<LauncherItemEntity>,
    iconSizeDp: Dp,
    showLabel: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1x1 Folder Preview: 2x2 mini grid of monochrome icons inside pill
        Box(
            modifier = Modifier
                .size(iconSizeDp)
                .clip(CircleShape)
                .background(NothingSurface)
                .border(1.dp, NothingSurfaceBorder, CircleShape)
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MiniAppIcon(itemsInFolder.getOrNull(0), size = 14.dp)
                    MiniAppIcon(itemsInFolder.getOrNull(1), size = 14.dp)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    MiniAppIcon(itemsInFolder.getOrNull(2), size = 14.dp)
                    MiniAppIcon(itemsInFolder.getOrNull(3), size = 14.dp)
                }
            }
        }

        if (showLabel) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = folder.name,
                fontFamily = SpaceGroteskSans,
                fontSize = 11.sp,
                color = NothingWhite,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MaxFolderItem(
    folder: FolderEntity,
    itemsInFolder: List<LauncherItemEntity>,
    onAppClick: (LauncherItemEntity) -> Unit,
    onFolderOpen: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(28.dp))
            .background(NothingSurface)
            .border(1.5.dp, NothingSurfaceBorder, RoundedCornerShape(28.dp))
            .combinedClickable(
                onClick = onFolderOpen,
                onLongClick = onLongClick
            )
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = folder.name.uppercase(),
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted,
                    letterSpacing = 1.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(NothingRed)
                )
            }

            // 2x2 Direct Clickable apps
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DirectClickableAppCell(itemsInFolder.getOrNull(0), onClick = onAppClick)
                DirectClickableAppCell(itemsInFolder.getOrNull(1), onClick = onAppClick)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DirectClickableAppCell(itemsInFolder.getOrNull(2), onClick = onAppClick)
                // 4th cell: Either 4th app or folder expand button!
                if (itemsInFolder.size > 3) {
                    DirectClickableAppCell(itemsInFolder.getOrNull(3), onClick = onAppClick)
                } else {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(NothingBlack)
                            .border(1.dp, NothingSurfaceBorder, CircleShape)
                            .clickable(onClick = onFolderOpen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Expand",
                            tint = NothingWhiteMuted,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DirectClickableAppCell(
    item: LauncherItemEntity?,
    onClick: (LauncherItemEntity) -> Unit
) {
    if (item == null) {
        Box(modifier = Modifier.size(38.dp))
        return
    }

    val customIconName = item.packageName?.let { IconRegistry.getIconNameForPackage(it) }

    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(NothingBlack)
            .border(1.dp, NothingSurfaceBorder, CircleShape)
            .clickable { onClick(item) },
        contentAlignment = Alignment.Center
    ) {
        if (customIconName != null) {
            Canvas(modifier = Modifier.size(24.dp)) {
                VectorIconDrawables.drawIcon(customIconName, this, NothingWhite)
            }
        } else {
            Text(
                text = item.title.take(1).uppercase(),
                fontFamily = SpaceMonoTech,
                fontSize = 14.sp,
                color = NothingWhite
            )
        }
    }
}

@Composable
private fun MiniAppIcon(item: LauncherItemEntity?, size: Dp) {
    if (item == null) {
        Box(
            modifier = Modifier
                .size(size)
                .background(NothingWhiteFaint, CircleShape)
        )
        return
    }
    val customIconName = item.packageName?.let { IconRegistry.getIconNameForPackage(it) }
    Box(
        modifier = Modifier
            .size(size)
            .background(NothingBlack, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (customIconName != null) {
            Canvas(modifier = Modifier.size(size * 0.75f)) {
                VectorIconDrawables.drawIcon(customIconName, this, NothingWhite)
            }
        } else {
            Box(
                modifier = Modifier
                    .size(size * 0.45f)
                    .background(NothingWhite, CircleShape)
            )
        }
    }
}
