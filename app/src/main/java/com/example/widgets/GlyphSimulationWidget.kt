package com.example.widgets

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteFaint
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.SpaceMonoTech

@Composable
fun NothingGlyphSimulationWidget() {
    var modeIndex by remember { mutableIntStateOf(0) }
    val modes = listOf("GLYPH PULSE", "RHYTHM BEAT", "CHARGING LIGHT")
    val currentMode = modes[modeIndex]

    val infiniteTransition = rememberInfiniteTransition(label = "glyph_anim")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sweep_angle"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .clickable { modeIndex = (modeIndex + 1) % modes.size }
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
                    text = "GLYPH INTERFACE",
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

            // Canvas drawing Nothing Glyph light strips simulation
            Canvas(modifier = Modifier.size(72.dp)) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val radius = size.minDimension / 2.2f
                val strokeW = 4.dp.toPx()

                // Background glyph track
                drawCircle(
                    color = NothingWhiteFaint,
                    radius = radius,
                    center = center,
                    style = Stroke(width = strokeW)
                )

                when (modeIndex) {
                    0 -> {
                        // Pulse: Illuminating segmented arcs
                        drawArc(
                            color = Color.White.copy(alpha = pulseAlpha),
                            startAngle = -45f,
                            sweepAngle = 90f,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeW * 1.2f, cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = Color.White.copy(alpha = pulseAlpha),
                            startAngle = 135f,
                            sweepAngle = 90f,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeW * 1.2f, cap = StrokeCap.Round)
                        )
                        // Diagonal slash
                        drawLine(
                            color = Color.White.copy(alpha = pulseAlpha),
                            start = Offset(center.x - radius * 0.4f, center.y - radius * 0.4f),
                            end = Offset(center.x + radius * 0.4f, center.y + radius * 0.4f),
                            strokeWidth = strokeW,
                            cap = StrokeCap.Round
                        )
                    }
                    1 -> {
                        // Rhythm rotating beam
                        drawArc(
                            color = Color.White,
                            startAngle = sweepAngle,
                            sweepAngle = 100f,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeW * 1.3f, cap = StrokeCap.Round)
                        )
                    }
                    else -> {
                        // Charging light gauge
                        drawArc(
                            color = NothingRed,
                            startAngle = -90f,
                            sweepAngle = 270f,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeW, cap = StrokeCap.Round)
                        )
                    }
                }

                // Center red glyph indicator dot
                drawCircle(
                    color = NothingRed,
                    radius = 3.dp.toPx(),
                    center = Offset(center.x + radius * 0.7f, center.y - radius * 0.7f)
                )
            }

            Text(
                text = "$currentMode [TAP]",
                fontFamily = SpaceMonoTech,
                fontSize = 9.sp,
                color = NothingWhiteMuted,
                letterSpacing = 0.5.sp
            )
        }
    }
}
