package com.example.homescreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed

@Composable
fun NothingWallpaperBackground(
    wallpaperIndex: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().background(NothingBlack)) {
        when (wallpaperIndex) {
            1 -> {
                // Dot Matrix Lattice Wallpaper
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val step = 32.dp.toPx()
                    val cols = (size.width / step).toInt() + 1
                    val rows = (size.height / step).toInt() + 1
                    val dotColor = Color.White.copy(alpha = 0.08f)

                    for (x in 0 until cols) {
                        for (y in 0 until rows) {
                            drawCircle(
                                color = dotColor,
                                radius = 1.2.dp.toPx(),
                                center = Offset(x * step, y * step)
                            )
                        }
                    }

                    // A subtle red accent dot near top-right
                    drawCircle(
                        color = NothingRed.copy(alpha = 0.4f),
                        radius = 2.dp.toPx(),
                        center = Offset(size.width - 48.dp.toPx(), 80.dp.toPx())
                    )
                }
            }
            2 -> {
                // Geometric Minimalist Arcs Wallpaper
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width * 0.5f, size.height * 0.42f)
                    val arcColor = Color.White.copy(alpha = 0.06f)
                    val strokeW = 1.5.dp.toPx()

                    drawCircle(
                        color = arcColor,
                        radius = size.width * 0.38f,
                        center = center,
                        style = Stroke(width = strokeW)
                    )
                    drawCircle(
                        color = arcColor,
                        radius = size.width * 0.65f,
                        center = center,
                        style = Stroke(width = strokeW)
                    )
                    drawCircle(
                        color = arcColor,
                        radius = size.width * 0.88f,
                        center = center,
                        style = Stroke(width = strokeW)
                    )

                    // Diagonal subtle guide line
                    drawLine(
                        color = arcColor,
                        start = Offset(0f, size.height * 0.25f),
                        end = Offset(size.width, size.height * 0.75f),
                        strokeWidth = strokeW
                    )
                }
            }
            else -> {
                // Pure Pitch Black AMOLED (Index 0)
            }
        }
    }
}
