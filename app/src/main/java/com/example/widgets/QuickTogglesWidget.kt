package com.example.widgets

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Wifi
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.SpaceMonoTech

@Composable
fun NothingQuickTogglesWidget() {
    val context = LocalContext.current
    var isFlashlightOn by remember { mutableStateOf(false) }

    fun toggleFlashlight() {
        try {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as? CameraManager
            val cameraId = cameraManager?.cameraIdList?.firstOrNull()
            if (cameraId != null) {
                isFlashlightOn = !isFlashlightOn
                cameraManager.setTorchMode(cameraId, isFlashlightOn)
            }
        } catch (e: Exception) {
            // Flashlight hardware not accessible or permitted
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "QUICK TOGGLES",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted,
                    letterSpacing = 1.sp
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(NothingRed)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Wi-Fi
                QuickToggleItem(
                    icon = Icons.Default.Wifi,
                    label = "WIFI",
                    isActive = false,
                    onClick = {
                        try {
                            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                        } catch (e: Exception) {}
                    }
                )

                // Bluetooth
                QuickToggleItem(
                    icon = Icons.Default.Bluetooth,
                    label = "BT",
                    isActive = false,
                    onClick = {
                        try {
                            context.startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                        } catch (e: Exception) {}
                    }
                )

                // Flashlight
                QuickToggleItem(
                    icon = Icons.Default.FlashlightOn,
                    label = "TORCH",
                    isActive = isFlashlightOn,
                    onClick = { toggleFlashlight() }
                )

                // Location
                QuickToggleItem(
                    icon = Icons.Default.LocationOn,
                    label = "GPS",
                    isActive = false,
                    onClick = {
                        try {
                            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            })
                        } catch (e: Exception) {}
                    }
                )
            }
        }
    }
}

@Composable
private fun QuickToggleItem(
    icon: ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(if (isActive) NothingRed else NothingBlack)
                .border(1.dp, if (isActive) NothingRed else NothingSurfaceBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isActive) NothingWhite else NothingWhite,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = label,
            fontFamily = SpaceMonoTech,
            fontSize = 9.sp,
            color = if (isActive) NothingRed else NothingWhiteMuted,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
