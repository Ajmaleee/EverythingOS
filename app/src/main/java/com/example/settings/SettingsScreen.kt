package com.example.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LauncherSettings
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
fun SettingsScreen(
    settings: LauncherSettings,
    onUpdateGrid: (Int, Int) -> Unit,
    onUpdateIconSize: (Int) -> Unit,
    onUpdateShowLabels: (Boolean) -> Unit,
    onUpdateMonochromeIcons: (Boolean) -> Unit,
    onUpdateTempUnit: (String) -> Unit,
    onUpdate24H: (Boolean) -> Unit,
    onUpdateWallpaper: (Int) -> Unit,
    onUpdateSwipeDown: (String) -> Unit,
    onAddWidgetToHome: (String) -> Unit,
    onResetDefaults: () -> Unit,
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NothingBlack)
            .padding(top = 36.dp, start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 40.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(NothingSurface)
                        .clickable(onClick = onBack),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = NothingWhite,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(NothingRed)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "NOTHING SETTINGS",
                        fontFamily = SilkscreenDotMatrix,
                        fontSize = 18.sp,
                        color = NothingWhite
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // SECTION 1: HOME SCREEN & GRID
            SettingsSectionHeader("HOME SCREEN & GRID")

            SettingsCard {
                // Columns
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "GRID COLUMNS",
                            fontFamily = SpaceGroteskSans,
                            fontSize = 13.sp,
                            color = NothingWhite
                        )
                        Text(
                            text = "${settings.gridColumns} COLUMNS",
                            fontFamily = SpaceMonoTech,
                            fontSize = 12.sp,
                            color = NothingRed
                        )
                    }
                    Slider(
                        value = settings.gridColumns.toFloat(),
                        onValueChange = { onUpdateGrid(it.toInt(), settings.gridRows) },
                        valueRange = 3f..6f,
                        steps = 2,
                        colors = SliderDefaults.colors(
                            thumbColor = NothingWhite,
                            activeTrackColor = NothingRed,
                            inactiveTrackColor = NothingSurfaceBorder
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Rows
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "GRID ROWS",
                            fontFamily = SpaceGroteskSans,
                            fontSize = 13.sp,
                            color = NothingWhite
                        )
                        Text(
                            text = "${settings.gridRows} ROWS",
                            fontFamily = SpaceMonoTech,
                            fontSize = 12.sp,
                            color = NothingRed
                        )
                    }
                    Slider(
                        value = settings.gridRows.toFloat(),
                        onValueChange = { onUpdateGrid(settings.gridColumns, it.toInt()) },
                        valueRange = 4f..8f,
                        steps = 3,
                        colors = SliderDefaults.colors(
                            thumbColor = NothingWhite,
                            activeTrackColor = NothingRed,
                            inactiveTrackColor = NothingSurfaceBorder
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Icon size
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ICON SIZE",
                            fontFamily = SpaceGroteskSans,
                            fontSize = 13.sp,
                            color = NothingWhite
                        )
                        Text(
                            text = "${settings.iconSizeDp} DP",
                            fontFamily = SpaceMonoTech,
                            fontSize = 12.sp,
                            color = NothingRed
                        )
                    }
                    Slider(
                        value = settings.iconSizeDp.toFloat(),
                        onValueChange = { onUpdateIconSize(it.toInt()) },
                        valueRange = 44f..72f,
                        steps = 6,
                        colors = SliderDefaults.colors(
                            thumbColor = NothingWhite,
                            activeTrackColor = NothingRed,
                            inactiveTrackColor = NothingSurfaceBorder
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // App labels switch
                SettingsSwitchRow(
                    label = "SHOW APP LABELS",
                    checked = settings.showLabels,
                    onCheckedChange = onUpdateShowLabels
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECTION 2: WIDGET LIBRARY - ADD WIDGETS
            SettingsSectionHeader("WIDGET LIBRARY [TAP TO ADD]")

            SettingsCard {
                val widgetOptions = listOf(
                    "DIGITAL_CLOCK" to "DIGITAL CLOCK (NDOT)",
                    "ANALOG_CLOCK" to "ANALOG CLOCK (CLASSIC)",
                    "WEATHER" to "NOTHING WEATHER",
                    "BATTERY" to "BATTERY RING GAUGE",
                    "RAM_STORAGE" to "HARDWARE & RAM MONITOR",
                    "NOTES" to "NOTHING QUICK NOTE",
                    "TODOS" to "TASK CHECKLIST",
                    "COUNTDOWN" to "DAYS COUNTDOWN",
                    "STOPWATCH" to "STOPWATCH & TIMER",
                    "QUICK_TOGGLES" to "CONNECTIVITY TOGGLES",
                    "GLYPH" to "GLYPH LIGHT SIMULATION"
                )

                widgetOptions.forEach { (type, name) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onAddWidgetToHome(type) }
                            .padding(vertical = 10.dp, horizontal = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            fontFamily = SpaceGroteskSans,
                            fontSize = 13.sp,
                            color = NothingWhiteSecondary
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(NothingSurfaceBorder)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "+ ADD",
                                fontFamily = SpaceMonoTech,
                                fontSize = 9.sp,
                                color = NothingWhite
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECTION 3: APPEARANCE & WALLPAPERS
            SettingsSectionHeader("APPEARANCE & WALLPAPERS")

            SettingsCard {
                Text(
                    text = "NOTHING AMOLED WALLPAPER",
                    fontFamily = SpaceMonoTech,
                    fontSize = 11.sp,
                    color = NothingWhiteMuted
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val wallpapers = listOf("PITCH BLACK", "DOT MATRIX", "GEOMETRIC")
                    wallpapers.forEachIndexed { idx, name ->
                        val isSelected = settings.wallpaperIndex == idx
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) NothingRed else NothingSurface)
                                .border(1.dp, if (isSelected) NothingRed else NothingSurfaceBorder, RoundedCornerShape(12.dp))
                                .clickable { onUpdateWallpaper(idx) }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = name,
                                fontFamily = SpaceMonoTech,
                                fontSize = 9.sp,
                                color = if (isSelected) NothingWhite else NothingWhiteMuted
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Time 24h
                SettingsSwitchRow(
                    label = "24-HOUR TIME FORMAT",
                    checked = settings.time24H,
                    onCheckedChange = onUpdate24H
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Temperature unit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "TEMPERATURE UNIT",
                        fontFamily = SpaceGroteskSans,
                        fontSize = 13.sp,
                        color = NothingWhite
                    )
                    Row {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (settings.tempUnit == "C") NothingWhite else NothingSurface)
                                .clickable { onUpdateTempUnit("C") }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "°C",
                                fontFamily = SpaceMonoTech,
                                fontSize = 11.sp,
                                color = if (settings.tempUnit == "C") NothingBlack else NothingWhiteMuted
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (settings.tempUnit == "F") NothingWhite else NothingSurface)
                                .clickable { onUpdateTempUnit("F") }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "°F",
                                fontFamily = SpaceMonoTech,
                                fontSize = 11.sp,
                                color = if (settings.tempUnit == "F") NothingBlack else NothingWhiteMuted
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // SECTION 4: DEVICE SPECIFICATIONS
            SettingsSectionHeader("DEVICE SPECIFICATIONS")

            SettingsCard {
                DeviceSpecRow("TARGET MODEL", "NOKIA TA-1004 (HMD GLOBAL)")
                DeviceSpecRow("CHIPSET", "QUALCOMM SNAPDRAGON 835 (MSM8998)")
                DeviceSpecRow("CPU", "OCTA-CORE KRYO 280")
                DeviceSpecRow("GPU", "ADRENO 540")
                DeviceSpecRow("DISPLAY", "1440 × 2560 QHD+ (560 DPI / 60HZ)")
                DeviceSpecRow("RAM", "3.64 GB LPDDR4X")
                DeviceSpecRow("ANDROID VERSION", "ANDROID 9 PIE (API LEVEL 28)")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Reset button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(NothingSurface)
                    .border(1.dp, NothingRed, RoundedCornerShape(16.dp))
                    .clickable(onClick = onResetDefaults)
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "RESET DEFAULT NOTHING HOMESCREEN",
                    fontFamily = SpaceMonoTech,
                    fontSize = 11.sp,
                    color = NothingRed
                )
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontFamily = SpaceMonoTech,
        fontSize = 11.sp,
        color = NothingWhiteMuted,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = SpaceGroteskSans,
            fontSize = 13.sp,
            color = NothingWhite
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = NothingWhite,
                checkedTrackColor = NothingRed,
                uncheckedThumbColor = NothingWhiteMuted,
                uncheckedTrackColor = NothingBlack
            )
        )
    }
}

@Composable
private fun DeviceSpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontFamily = SpaceMonoTech,
            fontSize = 10.sp,
            color = NothingWhiteMuted
        )
        Text(
            text = value,
            fontFamily = SpaceMonoTech,
            fontSize = 10.sp,
            color = NothingWhiteSecondary
        )
    }
}
