package com.example.widgets

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun NothingDigitalClockWidget(
    is24H: Boolean = true,
    onClick: () -> Unit = {}
) {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000)
        }
    }

    val cal = Calendar.getInstance().apply { timeInMillis = currentTime }
    val hourFormat = if (is24H) "%02d" else "%02d"
    val hourVal = if (is24H) cal.get(Calendar.HOUR_OF_DAY) else (cal.get(Calendar.HOUR).let { if (it == 0) 12 else it })
    val hourStr = String.format(Locale.US, hourFormat, hourVal)
    val minStr = String.format(Locale.US, "%02d", cal.get(Calendar.MINUTE))
    val secStr = String.format(Locale.US, "%02d", cal.get(Calendar.SECOND))
    val dateStr = SimpleDateFormat("EEE, dd MMM", Locale.US).format(Date(currentTime)).uppercase()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Time with Nothing NDot red colon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = hourStr,
                    fontFamily = SilkscreenDotMatrix,
                    fontSize = 42.sp,
                    color = NothingWhite,
                    letterSpacing = 1.sp
                )
                // Nothing red blinking colon
                Text(
                    text = ":",
                    fontFamily = SilkscreenDotMatrix,
                    fontSize = 38.sp,
                    color = if (cal.get(Calendar.SECOND) % 2 == 0) NothingRed else NothingWhiteMuted,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    text = minStr,
                    fontFamily = SilkscreenDotMatrix,
                    fontSize = 42.sp,
                    color = NothingWhite,
                    letterSpacing = 1.sp
                )
                Text(
                    text = secStr,
                    fontFamily = SpaceMonoTech,
                    fontSize = 14.sp,
                    color = NothingRed,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .align(Alignment.Bottom)
                        .padding(bottom = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Date in SpaceGrotesk
            Text(
                text = dateStr,
                fontFamily = SpaceGroteskSans,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.5.sp,
                color = NothingWhiteMuted
            )
        }
    }
}

@Composable
fun NothingAnalogClockWidget(onClick: () -> Unit = {}) {
    var currentTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = System.currentTimeMillis()
            delay(1000)
        }
    }

    val cal = Calendar.getInstance().apply { timeInMillis = currentTime }
    val hours = cal.get(Calendar.HOUR)
    val minutes = cal.get(Calendar.MINUTE)
    val seconds = cal.get(Calendar.SECOND)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = (size.minDimension / 2f) - 8f

            // Outer ring
            drawCircle(
                color = NothingWhite,
                radius = radius,
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )

            // Hour tick marks (12 ticks)
            for (i in 0 until 12) {
                val angle = Math.toRadians((i * 30.0 - 90.0))
                val isMajor = (i % 3 == 0)
                val tickLen = if (isMajor) 14f else 7f
                val r1 = radius - tickLen
                val r2 = radius - 2f
                val start = Offset((center.x + r1 * Math.cos(angle)).toFloat(), (center.y + r1 * Math.sin(angle)).toFloat())
                val end = Offset((center.x + r2 * Math.cos(angle)).toFloat(), (center.y + r2 * Math.sin(angle)).toFloat())
                drawLine(
                    color = if (isMajor) NothingWhite else NothingWhiteMuted,
                    start = start,
                    end = end,
                    strokeWidth = if (isMajor) 2.5f else 1.5f
                )
            }

            // Hour Hand
            val hourAngle = Math.toRadians(((hours + minutes / 60f) * 30.0 - 90.0))
            val hourLen = radius * 0.50f
            drawLine(
                color = NothingWhite,
                start = center,
                end = Offset((center.x + hourLen * Math.cos(hourAngle)).toFloat(), (center.y + hourLen * Math.sin(hourAngle)).toFloat()),
                strokeWidth = 3.5.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Minute Hand
            val minAngle = Math.toRadians((minutes * 6.0 - 90.0))
            val minLen = radius * 0.72f
            drawLine(
                color = NothingWhite,
                start = center,
                end = Offset((center.x + minLen * Math.cos(minAngle)).toFloat(), (center.y + minLen * Math.sin(minAngle)).toFloat()),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Second Hand with Red Dot
            val secAngle = Math.toRadians((seconds * 6.0 - 90.0))
            val secLen = radius * 0.82f
            val secEnd = Offset((center.x + secLen * Math.cos(secAngle)).toFloat(), (center.y + secLen * Math.sin(secAngle)).toFloat())
            drawLine(
                color = NothingRed,
                start = center,
                end = secEnd,
                strokeWidth = 1.2.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawCircle(color = NothingRed, radius = 4.dp.toPx(), center = secEnd)
            drawCircle(color = NothingWhite, radius = 3.dp.toPx(), center = center)
        }
    }
}

@Composable
fun NothingTimerStopwatchWidget() {
    var isRunning by remember { mutableStateOf(false) }
    var elapsedSeconds by remember { mutableLongStateOf(0L) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000)
            elapsedSeconds++
        }
    }

    val mins = elapsedSeconds / 60
    val secs = elapsedSeconds % 60
    val timeFormatted = String.format(Locale.US, "%02d:%02d", mins, secs)

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
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "STOPWATCH",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted,
                    letterSpacing = 1.sp
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isRunning) NothingRed else NothingWhiteMuted)
                )
            }

            Text(
                text = timeFormatted,
                fontFamily = SilkscreenDotMatrix,
                fontSize = 28.sp,
                color = NothingWhite
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isRunning) NothingRed else NothingWhite)
                        .clickable { isRunning = !isRunning }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isRunning) "PAUSE" else "START",
                        fontFamily = SpaceMonoTech,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = NothingBlack
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(12.dp))
                        .clickable {
                            isRunning = false
                            elapsedSeconds = 0L
                        }
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "RESET",
                        fontFamily = SpaceMonoTech,
                        fontSize = 10.sp,
                        color = NothingWhiteSecondary
                    )
                }
            }
        }
    }
}
