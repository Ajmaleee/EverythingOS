package com.example.icon

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

object VectorIconDrawables {

    fun drawIcon(name: String, drawScope: DrawScope, color: Color = Color.White) {
        with(drawScope) {
            val w = size.width
            val h = size.height
            val strokeW = w * 0.07f

            when (name.lowercase()) {
                "phone" -> drawPhone(w, h, strokeW, color)
                "messages" -> drawMessages(w, h, strokeW, color)
                "camera", "nokia_camera" -> drawCamera(w, h, strokeW, color)
                "chrome", "browser" -> drawChrome(w, h, strokeW, color)
                "youtube" -> drawYoutube(w, h, strokeW, color)
                "whatsapp" -> drawWhatsApp(w, h, strokeW, color)
                "telegram" -> drawTelegram(w, h, strokeW, color)
                "spotify" -> drawSpotify(w, h, strokeW, color)
                "github" -> drawGithub(w, h, strokeW, color)
                "discord" -> drawDiscord(w, h, strokeW, color)
                "chatgpt" -> drawChatGPT(w, h, strokeW, color)
                "claude" -> drawClaude(w, h, strokeW, color)
                "instagram" -> drawInstagram(w, h, strokeW, color)
                "reddit" -> drawReddit(w, h, strokeW, color)
                "gmail" -> drawGmail(w, h, strokeW, color)
                "maps" -> drawMaps(w, h, strokeW, color)
                "photos" -> drawPhotos(w, h, strokeW, color)
                "drive" -> drawDrive(w, h, strokeW, color)
                "keep" -> drawKeep(w, h, strokeW, color)
                "calendar" -> drawCalendar(w, h, strokeW, color)
                "calculator" -> drawCalculator(w, h, strokeW, color)
                "vlc" -> drawVlc(w, h, strokeW, color)
                "snapchat" -> drawSnapchat(w, h, strokeW, color)
                "roblox" -> drawRoblox(w, h, strokeW, color)
                "play_store" -> drawPlayStore(w, h, strokeW, color)
                "files" -> drawFiles(w, h, strokeW, color)
                "settings" -> drawSettings(w, h, strokeW, color)
                "clock" -> drawClock(w, h, strokeW, color)
                "contacts" -> drawContacts(w, h, strokeW, color)
                "music" -> drawMusic(w, h, strokeW, color)
                "weather" -> drawWeather(w, h, strokeW, color)
                else -> drawGenericApp(w, h, strokeW, color)
            }
        }
    }

    private fun DrawScope.drawPhone(w: Float, h: Float, strokeW: Float, color: Color) {
        val path = Path().apply {
            moveTo(w * 0.32f, h * 0.28f)
            cubicTo(w * 0.32f, h * 0.22f, w * 0.40f, h * 0.20f, w * 0.46f, h * 0.26f)
            lineTo(w * 0.52f, h * 0.34f)
            cubicTo(w * 0.56f, h * 0.38f, w * 0.54f, h * 0.44f, w * 0.48f, h * 0.48f)
            lineTo(w * 0.44f, h * 0.52f)
            cubicTo(w * 0.48f, h * 0.60f, w * 0.54f, h * 0.66f, w * 0.62f, h * 0.70f)
            lineTo(w * 0.66f, h * 0.66f)
            cubicTo(w * 0.70f, h * 0.60f, w * 0.76f, h * 0.58f, w * 0.80f, h * 0.62f)
            lineTo(w * 0.88f, h * 0.68f)
            cubicTo(w * 0.94f, h * 0.74f, w * 0.92f, h * 0.82f, w * 0.86f, h * 0.86f)
            cubicTo(w * 0.72f, h * 0.94f, w * 0.48f, h * 0.88f, w * 0.30f, h * 0.70f)
            cubicTo(w * 0.12f, h * 0.52f, w * 0.06f, h * 0.28f, w * 0.14f, h * 0.14f)
            cubicTo(w * 0.18f, h * 0.08f, w * 0.26f, h * 0.06f, w * 0.32f, h * 0.12f)
        }
        drawPath(path, color = color, style = Fill)
    }

    private fun DrawScope.drawMessages(w: Float, h: Float, strokeW: Float, color: Color) {
        val bubble = Path().apply {
            addRoundRect(
                androidx.compose.ui.geometry.RoundRect(
                    Rect(w * 0.15f, h * 0.20f, w * 0.85f, h * 0.70f),
                    CornerRadius(w * 0.15f)
                )
            )
            moveTo(w * 0.30f, h * 0.70f)
            lineTo(w * 0.20f, h * 0.85f)
            lineTo(w * 0.42f, h * 0.70f)
        }
        drawPath(bubble, color = color, style = Stroke(width = strokeW))
        // 3 Nothing dots
        drawCircle(color, radius = w * 0.04f, center = Offset(w * 0.35f, h * 0.45f))
        drawCircle(color, radius = w * 0.04f, center = Offset(w * 0.50f, h * 0.45f))
        drawCircle(color, radius = w * 0.04f, center = Offset(w * 0.65f, h * 0.45f))
    }

    private fun DrawScope.drawCamera(w: Float, h: Float, strokeW: Float, color: Color) {
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.15f, h * 0.25f),
            size = Size(w * 0.70f, h * 0.55f),
            cornerRadius = CornerRadius(w * 0.12f),
            style = Stroke(width = strokeW)
        )
        // Center lens
        drawCircle(color = color, radius = w * 0.16f, center = Offset(w * 0.50f, h * 0.52f), style = Stroke(width = strokeW))
        // Red Nothing status dot
        drawCircle(color = Color(0xFFD71920), radius = w * 0.05f, center = Offset(w * 0.70f, h * 0.38f))
    }

    private fun DrawScope.drawChrome(w: Float, h: Float, strokeW: Float, color: Color) {
        val center = Offset(w * 0.5f, h * 0.5f)
        val outerRadius = w * 0.36f
        drawCircle(color = color, radius = outerRadius, center = center, style = Stroke(width = strokeW))
        drawCircle(color = color, radius = w * 0.15f, center = center, style = Fill)
        // Segment dividers
        drawLine(color = color, start = Offset(w * 0.5f, h * 0.5f - outerRadius), end = Offset(w * 0.65f, h * 0.5f), strokeWidth = strokeW)
        drawLine(color = color, start = Offset(w * 0.5f + outerRadius * 0.86f, h * 0.5f + outerRadius * 0.5f), end = Offset(w * 0.42f, h * 0.62f), strokeWidth = strokeW)
        drawLine(color = color, start = Offset(w * 0.5f - outerRadius * 0.86f, h * 0.5f + outerRadius * 0.5f), end = Offset(w * 0.42f, h * 0.38f), strokeWidth = strokeW)
    }

    private fun DrawScope.drawYoutube(w: Float, h: Float, strokeW: Float, color: Color) {
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.12f, h * 0.24f),
            size = Size(w * 0.76f, h * 0.52f),
            cornerRadius = CornerRadius(w * 0.16f),
            style = Stroke(width = strokeW)
        )
        // Play triangle
        val playPath = Path().apply {
            moveTo(w * 0.44f, h * 0.38f)
            lineTo(w * 0.64f, h * 0.50f)
            lineTo(w * 0.44f, h * 0.62f)
            close()
        }
        drawPath(playPath, color = color, style = Fill)
    }

    private fun DrawScope.drawWhatsApp(w: Float, h: Float, strokeW: Float, color: Color) {
        val bubble = Path().apply {
            addRoundRect(
                androidx.compose.ui.geometry.RoundRect(
                    Rect(w * 0.16f, h * 0.16f, w * 0.84f, h * 0.84f),
                    CornerRadius(w * 0.34f)
                )
            )
            moveTo(w * 0.30f, h * 0.76f)
            lineTo(w * 0.18f, h * 0.88f)
            lineTo(w * 0.38f, h * 0.80f)
        }
        drawPath(bubble, color = color, style = Stroke(width = strokeW))
        // Mini phone inside
        drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.50f, h * 0.50f))
    }

    private fun DrawScope.drawTelegram(w: Float, h: Float, strokeW: Float, color: Color) {
        drawCircle(color = color, radius = w * 0.36f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = strokeW))
        val plane = Path().apply {
            moveTo(w * 0.26f, h * 0.48f)
            lineTo(w * 0.74f, h * 0.30f)
            lineTo(w * 0.62f, h * 0.72f)
            lineTo(w * 0.46f, h * 0.58f)
            lineTo(w * 0.38f, h * 0.66f)
            lineTo(w * 0.36f, h * 0.54f)
            lineTo(w * 0.66f, h * 0.38f)
            close()
        }
        drawPath(plane, color = color, style = Fill)
    }

    private fun DrawScope.drawSpotify(w: Float, h: Float, strokeW: Float, color: Color) {
        drawCircle(color = color, radius = w * 0.36f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = strokeW))
        // 3 curved sound waves
        val arc1 = Path().apply {
            moveTo(w * 0.30f, h * 0.42f)
            cubicTo(w * 0.42f, h * 0.35f, w * 0.58f, h * 0.35f, w * 0.70f, h * 0.42f)
        }
        val arc2 = Path().apply {
            moveTo(w * 0.34f, h * 0.52f)
            cubicTo(w * 0.44f, h * 0.46f, w * 0.56f, h * 0.46f, w * 0.66f, h * 0.52f)
        }
        val arc3 = Path().apply {
            moveTo(w * 0.38f, h * 0.62f)
            cubicTo(w * 0.46f, h * 0.57f, w * 0.54f, h * 0.57f, w * 0.62f, h * 0.62f)
        }
        drawPath(arc1, color = color, style = Stroke(width = strokeW * 1.3f, cap = StrokeCap.Round))
        drawPath(arc2, color = color, style = Stroke(width = strokeW * 1.1f, cap = StrokeCap.Round))
        drawPath(arc3, color = color, style = Stroke(width = strokeW * 0.9f, cap = StrokeCap.Round))
    }

    private fun DrawScope.drawGithub(w: Float, h: Float, strokeW: Float, color: Color) {
        val path = Path().apply {
            moveTo(w * 0.50f, h * 0.18f)
            cubicTo(w * 0.32f, h * 0.18f, w * 0.18f, h * 0.32f, w * 0.18f, h * 0.50f)
            cubicTo(w * 0.18f, h * 0.65f, w * 0.28f, h * 0.77f, w * 0.42f, h * 0.81f)
            lineTo(w * 0.42f, h * 0.72f)
            cubicTo(w * 0.35f, h * 0.74f, w * 0.33f, h * 0.68f, w * 0.33f, h * 0.68f)
            cubicTo(w * 0.31f, h * 0.62f, w * 0.28f, h * 0.60f, w * 0.28f, h * 0.60f)
            lineTo(w * 0.34f, h * 0.60f)
            cubicTo(w * 0.38f, h * 0.64f, w * 0.40f, h * 0.68f, w * 0.40f, h * 0.68f)
            cubicTo(w * 0.46f, h * 0.68f, w * 0.50f, h * 0.64f, w * 0.52f, h * 0.62f)
            cubicTo(w * 0.52f, h * 0.56f, w * 0.55f, h * 0.52f, w * 0.58f, h * 0.50f)
            cubicTo(w * 0.44f, h * 0.48f, w * 0.30f, h * 0.43f, w * 0.30f, h * 0.33f)
            cubicTo(w * 0.30f, h * 0.27f, w * 0.33f, h * 0.22f, w * 0.37f, h * 0.19f)
            cubicTo(w * 0.43f, h * 0.19f, w * 0.49f, h * 0.23f, w * 0.50f, h * 0.24f)
            cubicTo(w * 0.55f, h * 0.22f, w * 0.60f, h * 0.22f, w * 0.65f, h * 0.24f)
            cubicTo(w * 0.70f, h * 0.20f, w * 0.75f, h * 0.19f, w * 0.81f, h * 0.19f)
            close()
        }
        drawPath(path, color = color, style = Stroke(width = strokeW))
    }

    private fun DrawScope.drawDiscord(w: Float, h: Float, strokeW: Float, color: Color) {
        val face = Path().apply {
            moveTo(w * 0.25f, h * 0.30f)
            cubicTo(w * 0.40f, h * 0.26f, w * 0.60f, h * 0.26f, w * 0.75f, h * 0.30f)
            lineTo(w * 0.82f, h * 0.64f)
            cubicTo(w * 0.70f, h * 0.74f, w * 0.60f, h * 0.74f, w * 0.56f, h * 0.70f)
            lineTo(w * 0.52f, h * 0.65f)
            cubicTo(w * 0.51f, h * 0.65f, w * 0.49f, h * 0.65f, w * 0.48f, h * 0.65f)
            lineTo(w * 0.44f, h * 0.70f)
            cubicTo(w * 0.40f, h * 0.74f, w * 0.30f, h * 0.74f, w * 0.18f, h * 0.64f)
            close()
        }
        drawPath(face, color = color, style = Stroke(width = strokeW, cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawCircle(color = color, radius = w * 0.05f, center = Offset(w * 0.38f, h * 0.50f))
        drawCircle(color = color, radius = w * 0.05f, center = Offset(w * 0.62f, h * 0.50f))
    }

    private fun DrawScope.drawChatGPT(w: Float, h: Float, strokeW: Float, color: Color) {
        val center = Offset(w * 0.5f, h * 0.5f)
        for (i in 0 until 6) {
            val angle = Math.toRadians((i * 60.0))
            val p = Path().apply {
                val r1 = w * 0.15f
                val r2 = w * 0.32f
                val x1 = (center.x + r1 * Math.cos(angle)).toFloat()
                val y1 = (center.y + r1 * Math.sin(angle)).toFloat()
                val x2 = (center.x + r2 * Math.cos(angle + 0.5)).toFloat()
                val y2 = (center.y + r2 * Math.sin(angle + 0.5)).toFloat()
                moveTo(x1, y1)
                lineTo(x2, y2)
            }
            drawPath(p, color = color, style = Stroke(width = strokeW, cap = StrokeCap.Round))
        }
        drawCircle(color = color, radius = w * 0.06f, center = center)
    }

    private fun DrawScope.drawClaude(w: Float, h: Float, strokeW: Float, color: Color) {
        val center = Offset(w * 0.5f, h * 0.5f)
        val rays = 8
        for (i in 0 until rays) {
            val angle = Math.toRadians((i * (360.0 / rays)))
            val x2 = (center.x + w * 0.32f * Math.cos(angle)).toFloat()
            val y2 = (center.y + h * 0.32f * Math.sin(angle)).toFloat()
            drawLine(color = color, start = center, end = Offset(x2, y2), strokeWidth = strokeW * 1.2f, cap = StrokeCap.Round)
        }
    }

    private fun DrawScope.drawInstagram(w: Float, h: Float, strokeW: Float, color: Color) {
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.20f, h * 0.20f),
            size = Size(w * 0.60f, h * 0.60f),
            cornerRadius = CornerRadius(w * 0.18f),
            style = Stroke(width = strokeW)
        )
        drawCircle(color = color, radius = w * 0.15f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = strokeW))
        drawCircle(color = color, radius = w * 0.035f, center = Offset(w * 0.66f, h * 0.34f))
    }

    private fun DrawScope.drawReddit(w: Float, h: Float, strokeW: Float, color: Color) {
        drawCircle(color = color, radius = w * 0.30f, center = Offset(w * 0.5f, h * 0.55f), style = Stroke(width = strokeW))
        // Antenna
        drawLine(color = color, start = Offset(w * 0.5f, h * 0.25f), end = Offset(w * 0.62f, h * 0.18f), strokeWidth = strokeW, cap = StrokeCap.Round)
        drawCircle(color = color, radius = w * 0.04f, center = Offset(w * 0.65f, h * 0.18f))
        // Eyes
        drawCircle(color = color, radius = w * 0.04f, center = Offset(w * 0.40f, h * 0.52f))
        drawCircle(color = color, radius = w * 0.04f, center = Offset(w * 0.60f, h * 0.52f))
    }

    private fun DrawScope.drawGmail(w: Float, h: Float, strokeW: Float, color: Color) {
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.15f, h * 0.25f),
            size = Size(w * 0.70f, h * 0.50f),
            cornerRadius = CornerRadius(w * 0.10f),
            style = Stroke(width = strokeW)
        )
        val m = Path().apply {
            moveTo(w * 0.18f, h * 0.28f)
            lineTo(w * 0.50f, h * 0.55f)
            lineTo(w * 0.82f, h * 0.28f)
        }
        drawPath(m, color = color, style = Stroke(width = strokeW, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }

    private fun DrawScope.drawMaps(w: Float, h: Float, strokeW: Float, color: Color) {
        val pin = Path().apply {
            moveTo(w * 0.50f, h * 0.82f)
            cubicTo(w * 0.34f, h * 0.60f, w * 0.24f, h * 0.44f, w * 0.24f, h * 0.36f)
            cubicTo(w * 0.24f, h * 0.22f, w * 0.36f, h * 0.18f, w * 0.50f, h * 0.18f)
            cubicTo(w * 0.64f, h * 0.18f, w * 0.76f, h * 0.22f, w * 0.76f, h * 0.36f)
            cubicTo(w * 0.76f, h * 0.44f, w * 0.66f, h * 0.60f, w * 0.50f, h * 0.82f)
            close()
        }
        drawPath(pin, color = color, style = Stroke(width = strokeW))
        drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.50f, h * 0.36f))
    }

    private fun DrawScope.drawPhotos(w: Float, h: Float, strokeW: Float, color: Color) {
        val center = Offset(w * 0.5f, h * 0.5f)
        // 4 geometric petals
        for (i in 0 until 4) {
            val angle = Math.toRadians((i * 90.0))
            val petal = Path().apply {
                val cx = (center.x + w * 0.16f * Math.cos(angle)).toFloat()
                val cy = (center.y + h * 0.16f * Math.sin(angle)).toFloat()
                addOval(Rect(cx - w * 0.12f, cy - h * 0.08f, cx + w * 0.12f, cy + h * 0.08f))
            }
            drawPath(petal, color = color, style = Stroke(width = strokeW * 0.8f))
        }
        drawCircle(color = color, radius = w * 0.05f, center = center)
    }

    private fun DrawScope.drawDrive(w: Float, h: Float, strokeW: Float, color: Color) {
        val path = Path().apply {
            moveTo(w * 0.50f, h * 0.20f)
            lineTo(w * 0.82f, h * 0.75f)
            lineTo(w * 0.18f, h * 0.75f)
            close()
        }
        drawPath(path, color = color, style = Stroke(width = strokeW * 1.2f, cap = StrokeCap.Round, join = StrokeJoin.Round))
        drawLine(color = color, start = Offset(w * 0.38f, h * 0.40f), end = Offset(w * 0.68f, h * 0.75f), strokeWidth = strokeW)
    }

    private fun DrawScope.drawKeep(w: Float, h: Float, strokeW: Float, color: Color) {
        val bulb = Path().apply {
            moveTo(w * 0.38f, h * 0.68f)
            lineTo(w * 0.38f, h * 0.54f)
            cubicTo(w * 0.28f, h * 0.48f, w * 0.26f, h * 0.32f, w * 0.36f, h * 0.22f)
            cubicTo(w * 0.44f, h * 0.14f, w * 0.56f, h * 0.14f, w * 0.64f, h * 0.22f)
            cubicTo(w * 0.74f, h * 0.32f, w * 0.72f, h * 0.48f, w * 0.62f, h * 0.54f)
            lineTo(w * 0.62f, h * 0.68f)
            close()
        }
        drawPath(bulb, color = color, style = Stroke(width = strokeW))
        drawLine(color = color, start = Offset(w * 0.42f, h * 0.75f), end = Offset(w * 0.58f, h * 0.75f), strokeWidth = strokeW)
    }

    private fun DrawScope.drawCalendar(w: Float, h: Float, strokeW: Float, color: Color) {
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.18f, h * 0.24f),
            size = Size(w * 0.64f, h * 0.56f),
            cornerRadius = CornerRadius(w * 0.10f),
            style = Stroke(width = strokeW)
        )
        drawLine(color = color, start = Offset(w * 0.18f, h * 0.40f), end = Offset(w * 0.82f, h * 0.40f), strokeWidth = strokeW)
        // Binding rings
        drawLine(color = color, start = Offset(w * 0.34f, h * 0.16f), end = Offset(w * 0.34f, h * 0.26f), strokeWidth = strokeW)
        drawLine(color = color, start = Offset(w * 0.66f, h * 0.16f), end = Offset(w * 0.66f, h * 0.26f), strokeWidth = strokeW)
        // Calendar date dots
        drawCircle(color = color, radius = w * 0.03f, center = Offset(w * 0.35f, h * 0.52f))
        drawCircle(color = color, radius = w * 0.03f, center = Offset(w * 0.50f, h * 0.52f))
        drawCircle(color = color, radius = w * 0.03f, center = Offset(w * 0.65f, h * 0.52f))
        drawCircle(color = color, radius = w * 0.03f, center = Offset(w * 0.35f, h * 0.66f))
        drawCircle(color = Color(0xFFD71920), radius = w * 0.035f, center = Offset(w * 0.50f, h * 0.66f)) // Active red dot
    }

    private fun DrawScope.drawCalculator(w: Float, h: Float, strokeW: Float, color: Color) {
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.20f, h * 0.18f),
            size = Size(w * 0.60f, h * 0.64f),
            cornerRadius = CornerRadius(w * 0.12f),
            style = Stroke(width = strokeW)
        )
        // Screen bar
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.28f, h * 0.26f),
            size = Size(w * 0.44f, h * 0.12f),
            cornerRadius = CornerRadius(w * 0.04f),
            style = Fill
        )
        // Math symbols dots
        drawCircle(color = color, radius = w * 0.03f, center = Offset(w * 0.36f, h * 0.50f))
        drawCircle(color = color, radius = w * 0.03f, center = Offset(w * 0.64f, h * 0.50f))
        drawCircle(color = color, radius = w * 0.03f, center = Offset(w * 0.36f, h * 0.68f))
        drawCircle(color = color, radius = w * 0.03f, center = Offset(w * 0.64f, h * 0.68f))
    }

    private fun DrawScope.drawVlc(w: Float, h: Float, strokeW: Float, color: Color) {
        val cone = Path().apply {
            moveTo(w * 0.44f, h * 0.20f)
            lineTo(w * 0.56f, h * 0.20f)
            lineTo(w * 0.74f, h * 0.74f)
            lineTo(w * 0.26f, h * 0.74f)
            close()
        }
        drawPath(cone, color = color, style = Stroke(width = strokeW))
        drawLine(color = color, start = Offset(w * 0.20f, h * 0.78f), end = Offset(w * 0.80f, h * 0.78f), strokeWidth = strokeW)
    }

    private fun DrawScope.drawSnapchat(w: Float, h: Float, strokeW: Float, color: Color) {
        val ghost = Path().apply {
            moveTo(w * 0.50f, h * 0.22f)
            cubicTo(w * 0.36f, h * 0.22f, w * 0.30f, h * 0.32f, w * 0.30f, h * 0.48f)
            lineTo(w * 0.26f, h * 0.52f)
            lineTo(w * 0.32f, h * 0.60f)
            cubicTo(w * 0.28f, h * 0.70f, w * 0.38f, h * 0.76f, w * 0.50f, h * 0.74f)
            cubicTo(w * 0.62f, h * 0.76f, w * 0.72f, h * 0.70f, w * 0.68f, h * 0.60f)
            lineTo(w * 0.74f, h * 0.52f)
            lineTo(w * 0.70f, h * 0.48f)
            cubicTo(w * 0.70f, h * 0.32f, w * 0.64f, h * 0.22f, w * 0.50f, h * 0.22f)
            close()
        }
        drawPath(ghost, color = color, style = Stroke(width = strokeW))
    }

    private fun DrawScope.drawRoblox(w: Float, h: Float, strokeW: Float, color: Color) {
        val path = Path().apply {
            moveTo(w * 0.32f, h * 0.20f)
            lineTo(w * 0.80f, h * 0.32f)
            lineTo(w * 0.68f, h * 0.80f)
            lineTo(w * 0.20f, h * 0.68f)
            close()
        }
        drawPath(path, color = color, style = Stroke(width = strokeW * 1.2f))
        // Inner hollow square
        val inner = Path().apply {
            moveTo(w * 0.45f, h * 0.42f)
            lineTo(w * 0.58f, h * 0.45f)
            lineTo(w * 0.55f, h * 0.58f)
            lineTo(w * 0.42f, h * 0.55f)
            close()
        }
        drawPath(inner, color = color, style = Fill)
    }

    private fun DrawScope.drawPlayStore(w: Float, h: Float, strokeW: Float, color: Color) {
        val play = Path().apply {
            moveTo(w * 0.28f, h * 0.20f)
            lineTo(w * 0.76f, h * 0.50f)
            lineTo(w * 0.28f, h * 0.80f)
            close()
        }
        drawPath(play, color = color, style = Stroke(width = strokeW, join = StrokeJoin.Round))
        drawLine(color = color, start = Offset(w * 0.28f, h * 0.20f), end = Offset(w * 0.54f, h * 0.64f), strokeWidth = strokeW)
    }

    private fun DrawScope.drawFiles(w: Float, h: Float, strokeW: Float, color: Color) {
        val folder = Path().apply {
            moveTo(w * 0.18f, h * 0.30f)
            lineTo(w * 0.42f, h * 0.30f)
            lineTo(w * 0.50f, h * 0.38f)
            lineTo(w * 0.82f, h * 0.38f)
            lineTo(w * 0.82f, h * 0.74f)
            lineTo(w * 0.18f, h * 0.74f)
            close()
        }
        drawPath(folder, color = color, style = Stroke(width = strokeW, join = StrokeJoin.Round))
    }

    private fun DrawScope.drawSettings(w: Float, h: Float, strokeW: Float, color: Color) {
        val center = Offset(w * 0.5f, h * 0.5f)
        val outerR = w * 0.32f
        val teeth = 8
        for (i in 0 until teeth) {
            val angle = Math.toRadians((i * (360.0 / teeth)))
            val p1 = Offset((center.x + (outerR - w * 0.05f) * Math.cos(angle)).toFloat(), (center.y + (outerR - w * 0.05f) * Math.sin(angle)).toFloat())
            val p2 = Offset((center.x + (outerR + w * 0.05f) * Math.cos(angle)).toFloat(), (center.y + (outerR + w * 0.05f) * Math.sin(angle)).toFloat())
            drawLine(color = color, start = p1, end = p2, strokeWidth = strokeW * 1.4f, cap = StrokeCap.Round)
        }
        drawCircle(color = color, radius = outerR - w * 0.04f, center = center, style = Stroke(width = strokeW))
        drawCircle(color = color, radius = w * 0.10f, center = center, style = Stroke(width = strokeW))
    }

    private fun DrawScope.drawClock(w: Float, h: Float, strokeW: Float, color: Color) {
        val center = Offset(w * 0.5f, h * 0.5f)
        drawCircle(color = color, radius = w * 0.35f, center = center, style = Stroke(width = strokeW))
        // 12, 3, 6, 9 ticks
        drawCircle(color = color, radius = w * 0.02f, center = Offset(w * 0.5f, h * 0.22f))
        drawCircle(color = color, radius = w * 0.02f, center = Offset(w * 0.78f, h * 0.5f))
        drawCircle(color = color, radius = w * 0.02f, center = Offset(w * 0.5f, h * 0.78f))
        drawCircle(color = color, radius = w * 0.02f, center = Offset(w * 0.22f, h * 0.5f))
        // Hands
        drawLine(color = color, start = center, end = Offset(w * 0.5f, h * 0.30f), strokeWidth = strokeW * 1.2f, cap = StrokeCap.Round)
        drawLine(color = color, start = center, end = Offset(w * 0.65f, h * 0.5f), strokeWidth = strokeW * 0.9f, cap = StrokeCap.Round)
        // Center red accent dot
        drawCircle(color = Color(0xFFD71920), radius = w * 0.035f, center = center)
    }

    private fun DrawScope.drawContacts(w: Float, h: Float, strokeW: Float, color: Color) {
        drawCircle(color = color, radius = w * 0.12f, center = Offset(w * 0.5f, h * 0.36f), style = Stroke(width = strokeW))
        val body = Path().apply {
            moveTo(w * 0.28f, h * 0.74f)
            cubicTo(w * 0.28f, h * 0.56f, w * 0.72f, h * 0.56f, w * 0.72f, h * 0.74f)
        }
        drawPath(body, color = color, style = Stroke(width = strokeW, cap = StrokeCap.Round))
    }

    private fun DrawScope.drawMusic(w: Float, h: Float, strokeW: Float, color: Color) {
        val note = Path().apply {
            moveTo(w * 0.38f, h * 0.64f)
            lineTo(w * 0.38f, h * 0.30f)
            lineTo(w * 0.68f, h * 0.22f)
            lineTo(w * 0.68f, h * 0.56f)
        }
        drawPath(note, color = color, style = Stroke(width = strokeW, cap = StrokeCap.Round))
        drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.32f, h * 0.64f), style = Fill)
        drawCircle(color = color, radius = w * 0.08f, center = Offset(w * 0.62f, h * 0.56f), style = Fill)
    }

    private fun DrawScope.drawWeather(w: Float, h: Float, strokeW: Float, color: Color) {
        // Sun
        drawCircle(color = color, radius = w * 0.16f, center = Offset(w * 0.60f, h * 0.36f), style = Stroke(width = strokeW))
        // Cloud
        val cloud = Path().apply {
            moveTo(w * 0.24f, h * 0.66f)
            lineTo(w * 0.66f, h * 0.66f)
            cubicTo(w * 0.74f, h * 0.66f, w * 0.76f, h * 0.54f, w * 0.70f, h * 0.50f)
            cubicTo(w * 0.72f, h * 0.40f, w * 0.60f, h * 0.38f, w * 0.54f, h * 0.44f)
            cubicTo(w * 0.48f, h * 0.38f, w * 0.34f, h * 0.40f, w * 0.32f, h * 0.50f)
            cubicTo(w * 0.22f, h * 0.52f, w * 0.20f, h * 0.66f, w * 0.24f, h * 0.66f)
            close()
        }
        drawPath(cloud, color = color, style = Stroke(width = strokeW))
    }

    private fun DrawScope.drawGenericApp(w: Float, h: Float, strokeW: Float, color: Color) {
        // Nothing OS generic geometric squircle with clean central dot grid
        drawRoundRect(
            color = color,
            topLeft = Offset(w * 0.22f, h * 0.22f),
            size = Size(w * 0.56f, h * 0.56f),
            cornerRadius = CornerRadius(w * 0.16f),
            style = Stroke(width = strokeW)
        )
        drawCircle(color = color, radius = w * 0.04f, center = Offset(w * 0.40f, h * 0.40f))
        drawCircle(color = color, radius = w * 0.04f, center = Offset(w * 0.60f, h * 0.40f))
        drawCircle(color = color, radius = w * 0.04f, center = Offset(w * 0.40f, h * 0.60f))
        drawCircle(color = Color(0xFFD71920), radius = w * 0.045f, center = Offset(w * 0.60f, h * 0.60f))
    }
}
