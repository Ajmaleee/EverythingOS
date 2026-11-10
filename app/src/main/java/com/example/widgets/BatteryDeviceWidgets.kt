package com.example.widgets

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Environment
import android.os.StatFs
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import java.util.Locale

@Composable
fun NothingBatteryRingWidget() {
    val context = LocalContext.current
    var batteryPct by remember { mutableIntStateOf(85) }
    var isCharging by remember { mutableStateOf(false) }
    var batteryTemp by remember { mutableFloatStateOf(29.0f) }

    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(c: Context?, intent: Intent?) {
                if (intent != null) {
                    val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    if (level != -1 && scale != -1) {
                        batteryPct = (level * 100 / scale.toFloat()).toInt()
                    }
                    val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
                    isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
                    val temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
                    batteryTemp = temp / 10.0f
                }
            }
        }
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val sticky = context.registerReceiver(receiver, filter)
        if (sticky != null) {
            val level = sticky.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = sticky.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            if (level != -1 && scale != -1) {
                batteryPct = (level * 100 / scale.toFloat()).toInt()
            }
            val status = sticky.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
            val temp = sticky.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
            batteryTemp = temp / 10.0f
        }

        onDispose {
            try {
                context.unregisterReceiver(receiver)
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Circular Ring Gauge
            Box(
                modifier = Modifier.size(76.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 6.dp.toPx()
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val radius = (size.minDimension - strokeWidth) / 2f

                    // Background track
                    drawCircle(
                        color = NothingWhiteFaint,
                        radius = radius,
                        center = center,
                        style = Stroke(width = strokeWidth)
                    )

                    // Foreground battery arc
                    val sweep = (batteryPct / 100f) * 360f
                    drawArc(
                        color = if (batteryPct <= 15) NothingRed else NothingWhite,
                        startAngle = -90f,
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                // Value inside ring
                Text(
                    text = "$batteryPct%",
                    fontFamily = SilkscreenDotMatrix,
                    fontSize = 13.sp,
                    color = NothingWhite
                )
            }

            // Stats column
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "BATTERY",
                    fontFamily = SpaceMonoTech,
                    fontSize = 11.sp,
                    color = NothingWhiteMuted,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (isCharging) NothingRed else NothingWhite)
                    )
                    Text(
                        text = if (isCharging) " CHARGING" else " DISCHARGING",
                        fontFamily = SpaceGroteskSans,
                        fontSize = 11.sp,
                        color = if (isCharging) NothingRed else NothingWhiteSecondary
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = String.format(Locale.US, "TEMP: %.1f°C", batteryTemp),
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted
                )
            }
        }
    }
}

@Composable
fun NothingRamStorageWidget() {
    val context = LocalContext.current
    var availableRamMb by remember { mutableLongStateOf(1600L) }
    var totalRamMb by remember { mutableLongStateOf(3727L) } // ~3.64GB
    var availableStorageGb by remember { mutableFloatStateOf(17.4f) }
    var totalStorageGb by remember { mutableFloatStateOf(64.0f) }

    DisposableEffect(Unit) {
        try {
            val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
            val memInfo = ActivityManager.MemoryInfo()
            actManager?.getMemoryInfo(memInfo)
            availableRamMb = memInfo.availMem / (1024 * 1024)
            totalRamMb = memInfo.totalMem / (1024 * 1024)

            val statFs = StatFs(Environment.getDataDirectory().path)
            val bytesAvailable = statFs.availableBlocksLong * statFs.blockSizeLong
            val bytesTotal = statFs.blockCountLong * statFs.blockSizeLong
            availableStorageGb = bytesAvailable / (1024f * 1024f * 1024f)
            totalStorageGb = bytesTotal / (1024f * 1024f * 1024f)
        } catch (e: Exception) {
            // Safe fallback
        }
        onDispose { }
    }

    val ramUsedPct = (((totalRamMb - availableRamMb).toFloat() / totalRamMb) * 100).toInt().coerceIn(0, 100)
    val storageUsedPct = (((totalStorageGb - availableStorageGb) / totalStorageGb) * 100).toInt().coerceIn(0, 100)

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
                    text = "HARDWARE MONITOR",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "SNAPDRAGON 835",
                    fontFamily = SpaceMonoTech,
                    fontSize = 9.sp,
                    color = NothingRed
                )
            }

            // RAM bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "RAM: ${availableRamMb / 1024f}GB FREE",
                        fontFamily = SpaceGroteskSans,
                        fontSize = 11.sp,
                        color = NothingWhite
                    )
                    Text(
                        text = "$ramUsedPct%",
                        fontFamily = SilkscreenDotMatrix,
                        fontSize = 11.sp,
                        color = NothingWhite
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                // Progress track
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(NothingWhiteFaint)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(ramUsedPct / 100f)
                            .height(5.dp)
                            .background(NothingWhite)
                    )
                }
            }

            // Storage bar
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = String.format(Locale.US, "INTERNAL: %.1fGB FREE", availableStorageGb),
                        fontFamily = SpaceGroteskSans,
                        fontSize = 11.sp,
                        color = NothingWhite
                    )
                    Text(
                        text = "$storageUsedPct%",
                        fontFamily = SilkscreenDotMatrix,
                        fontSize = 11.sp,
                        color = NothingWhite
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(NothingWhiteFaint)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(storageUsedPct / 100f)
                            .height(5.dp)
                            .background(NothingRed)
                    )
                }
            }
        }
    }
}
