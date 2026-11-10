@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.example.drawer

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.NothingLauncherApp
import com.example.icon.IconRegistry
import com.example.icon.VectorIconDrawables
import com.example.model.AppCategory
import com.example.model.AppItem
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteFaint
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.NothingWhiteSecondary
import com.example.ui.theme.SilkscreenDotMatrix
import com.example.ui.theme.SpaceGroteskSans
import com.example.ui.theme.SpaceMonoTech

@Composable
fun AppDrawerScreen(
    allApps: List<AppItem>,
    onAppClick: (AppItem) -> Unit,
    onAppLongClick: (AppItem) -> Unit,
    onCloseDrawer: () -> Unit
) {
    BackHandler { onCloseDrawer() }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(AppCategory.ALL) }

    val filteredApps = remember(allApps, searchQuery, selectedCategory) {
        allApps.filter { app ->
            !app.isHidden &&
            (selectedCategory == AppCategory.ALL || app.category == selectedCategory) &&
            (searchQuery.isBlank() || app.label.contains(searchQuery, ignoreCase = true) || app.packageName.contains(searchQuery, ignoreCase = true))
        }.sortedBy { it.label.lowercase() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NothingBlack)
            .padding(top = 36.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // 1. Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(NothingSurface)
                    .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = NothingWhiteMuted,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "SEARCH NOTHING APPS...",
                            fontFamily = SpaceMonoTech,
                            fontSize = 12.sp,
                            color = NothingWhiteMuted
                        )
                    }
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = SpaceGroteskSans,
                            fontSize = 13.sp,
                            color = NothingWhite
                        ),
                        cursorBrush = SolidColor(NothingRed),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                if (searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = NothingWhiteMuted,
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { searchQuery = "" }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 2. Category Chips
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(AppCategory.values()) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) NothingWhite else NothingSurface)
                            .border(1.dp, if (isSelected) NothingWhite else NothingSurfaceBorder, RoundedCornerShape(14.dp))
                            .clickable { selectedCategory = category }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = category.displayName,
                            fontFamily = SpaceMonoTech,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal,
                            color = if (isSelected) NothingBlack else NothingWhiteMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Header info: App Count & Close button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredApps.size} APPLICATIONS",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted,
                    letterSpacing = 1.sp
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(NothingSurface)
                        .clickable(onClick = onCloseDrawer)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "CLOSE [V]",
                        fontFamily = SpaceMonoTech,
                        fontSize = 9.sp,
                        color = NothingRed
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Vertical Grid of applications
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredApps, key = { "${it.packageName}/${it.activityName}" }) { appItem ->
                    DrawerAppIconItem(
                        appItem = appItem,
                        onClick = { onAppClick(appItem) },
                        onLongClick = { onAppLongClick(appItem) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawerAppIconItem(
    appItem: AppItem,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val context = LocalContext.current
    val app = context.applicationContext as? NothingLauncherApp
    var monoBitmap by remember(appItem.packageName) { mutableStateOf<Bitmap?>(null) }
    val customIconName = IconRegistry.getIconNameForPackage(appItem.packageName)

    LaunchedEffect(appItem.packageName) {
        if (customIconName == null && app != null) {
            monoBitmap = app.iconEngine.getMonochromeIcon(appItem.packageName)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(NothingSurface)
                .border(1.dp, NothingSurfaceBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (customIconName != null) {
                Canvas(modifier = Modifier.size(34.dp)) {
                    VectorIconDrawables.drawIcon(customIconName, this, NothingWhite)
                }
            } else if (monoBitmap != null) {
                Image(
                    bitmap = monoBitmap!!.asImageBitmap(),
                    contentDescription = appItem.label,
                    modifier = Modifier.size(36.dp)
                )
            } else {
                Text(
                    text = appItem.label.take(1).uppercase(),
                    fontFamily = SpaceMonoTech,
                    fontSize = 18.sp,
                    color = NothingWhite
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = appItem.label,
            fontFamily = SpaceGroteskSans,
            fontSize = 11.sp,
            color = NothingWhite,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
