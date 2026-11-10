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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.SilkscreenDotMatrix
import com.example.ui.theme.SpaceGroteskSans
import com.example.ui.theme.SpaceMonoTech

@Composable
fun NothingWeatherWidget(
    tempUnit: String = "C",
    onClick: () -> Unit = {}
) {
    var tempC by remember { mutableIntStateOf(21) }
    var condition by remember { mutableStateOf("PARTLY CLOUDY") }
    val displayTemp = if (tempUnit == "F") (tempC * 9 / 5 + 32) else tempC

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: Location & Dot
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "NOKIA TA-1004",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted,
                    letterSpacing = 1.sp
                )
                // Nothing red status indicator
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(NothingRed)
                )
            }

            // Center: Weather icon & Temperature
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = "$displayTemp",
                            fontFamily = SilkscreenDotMatrix,
                            fontSize = 38.sp,
                            color = NothingWhite
                        )
                        Text(
                            text = "°$tempUnit",
                            fontFamily = SpaceGroteskSans,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = NothingRed,
                            modifier = Modifier.padding(start = 2.dp, top = 4.dp)
                        )
                    }
                    Text(
                        text = condition,
                        fontFamily = SpaceGroteskSans,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = NothingWhiteMuted,
                        letterSpacing = 0.5.sp
                    )
                }

                // Geometric Nothing Weather glyph
                Canvas(modifier = Modifier.size(46.dp)) {
                    val w = size.width
                    val strokeW = 2.dp.toPx()

                    // Sun ring
                    drawCircle(
                        color = NothingWhite,
                        radius = w * 0.22f,
                        center = Offset(w * 0.65f, w * 0.35f),
                        style = Stroke(width = strokeW)
                    )

                    // Cloud outline
                    val cloud = Path().apply {
                        moveTo(w * 0.15f, w * 0.75f)
                        lineTo(w * 0.70f, w * 0.75f)
                        cubicTo(w * 0.82f, w * 0.75f, w * 0.85f, w * 0.58f, w * 0.75f, w * 0.52f)
                        cubicTo(w * 0.78f, w * 0.38f, w * 0.62f, w * 0.34f, w * 0.54f, w * 0.42f)
                        cubicTo(w * 0.46f, w * 0.34f, w * 0.28f, w * 0.38f, w * 0.25f, w * 0.52f)
                        cubicTo(w * 0.12f, w * 0.55f, w * 0.10f, w * 0.75f, w * 0.15f, w * 0.75f)
                        close()
                    }
                    drawPath(cloud, color = NothingWhite, style = Stroke(width = strokeW))
                }
            }

            // High / Low forecast row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "H: ${if (tempUnit == "F") 75 else 24}°  L: ${if (tempUnit == "F") 62 else 17}°",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted
                )
                Text(
                    text = "HUMIDITY 54%",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted
                )
            }
        }
    }
}
