package com.example.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LauncherItemEntity
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteFaint
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.SpaceMonoTech

@Composable
fun NothingDockView(
    dockItems: List<LauncherItemEntity>,
    currentPage: Int,
    totalPages: Int,
    iconSizeDp: Dp,
    onAppClick: (LauncherItemEntity) -> Unit,
    onAppLongClick: (LauncherItemEntity) -> Unit,
    onSwipeUpToDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Page Dots Indicator (Nothing OS style)
        if (totalPages > 1) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                for (p in 0 until totalPages) {
                    val isSelected = p == currentPage
                    Box(
                        modifier = Modifier
                            .size(if (isSelected) 6.dp else 4.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) NothingRed else NothingWhiteFaint)
                    )
                }
            }
        }

        // Dock Apps Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            dockItems.take(5).forEach { item ->
                Box(
                    modifier = Modifier.size(iconSizeDp + 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    NormalAppIconItem(
                        item = item,
                        iconSizeDp = iconSizeDp,
                        showLabel = false,
                        onClick = { onAppClick(item) },
                        onLongClick = { onAppLongClick(item) }
                    )
                }
            }
        }

        // Swipe up indicator line
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .size(width = 36.dp, height = 3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(NothingWhiteMuted.copy(alpha = 0.5f))
                .clickable(onClick = onSwipeUpToDrawer)
        )
    }
}
