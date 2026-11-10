package com.example.icon

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.collection.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IconRegistry {
    private val packageMap = mapOf(
        "com.google.android.youtube" to "youtube",
        "com.whatsapp" to "whatsapp",
        "org.telegram.messenger" to "telegram",
        "org.telegram.plus" to "telegram",
        "com.discord" to "discord",
        "com.openai.chatgpt" to "chatgpt",
        "com.anthropic.claude" to "claude",
        "com.instagram.android" to "instagram",
        "com.reddit.frontpage" to "reddit",
        "com.spotify.music" to "spotify",
        "com.android.chrome" to "chrome",
        "com.google.android.gm" to "gmail",
        "com.github.android" to "github",
        "com.google.android.apps.maps" to "maps",
        "com.google.android.apps.photos" to "photos",
        "com.google.android.apps.docs" to "drive",
        "com.google.android.keep" to "keep",
        "com.google.android.calendar" to "calendar",
        "com.google.android.calculator" to "calculator",
        "com.android.calculator2" to "calculator",
        "com.android.camera" to "camera",
        "com.hmdglobal.camera2" to "nokia_camera",
        "com.google.android.GoogleCamera" to "camera",
        "org.videolan.vlc" to "vlc",
        "com.snapchat.android" to "snapchat",
        "com.roblox.client" to "roblox",
        "com.android.vending" to "play_store",
        "com.google.android.documentsui" to "files",
        "com.android.documentsui" to "files",
        "com.google.android.apps.nbu.files" to "files",
        "com.android.settings" to "settings",
        "com.google.android.dialer" to "phone",
        "com.android.dialer" to "phone",
        "com.google.android.apps.messaging" to "messages",
        "com.android.mms" to "messages",
        "com.google.android.deskclock" to "clock",
        "com.android.deskclock" to "clock",
        "com.google.android.contacts" to "contacts",
        "com.android.contacts" to "contacts",
        "com.google.android.apps.photosgo" to "photos"
    )

    fun getIconNameForPackage(packageName: String): String? {
        return packageMap[packageName]
    }

    fun hasCustomVector(packageName: String): Boolean {
        return packageMap.containsKey(packageName)
    }
}

class MonochromeIconEngine(private val context: Context) {
    // Memory cache for filtered monochromatic bitmaps on Nokia TA-1004 (Snapdragon 835)
    // 3.64GB RAM constraint: Keep cache light (max 10MB)
    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize = (maxMemory / 8).coerceAtMost(10 * 1024)

    private val iconCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String, bitmap: Bitmap): Int {
            return bitmap.byteCount / 1024
        }
    }

    suspend fun getMonochromeIcon(packageName: String): Bitmap? = withContext(Dispatchers.IO) {
        iconCache.get(packageName)?.let { return@withContext it }

        try {
            val pm = context.packageManager
            val appInfo = pm.getApplicationInfo(packageName, 0)
            val drawable = pm.getApplicationIcon(appInfo)
            val bmp = processToMonochrome(drawable)
            if (bmp != null) {
                iconCache.put(packageName, bmp)
            }
            bmp
        } catch (e: Exception) {
            null
        }
    }

    private fun processToMonochrome(drawable: Drawable): Bitmap {
        val size = 168 // xxxhdpi optimal size for TA-1004
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // 1. Draw into canvas
        drawable.setBounds(18, 18, size - 18, size - 18)
        drawable.draw(canvas)

        // 2. Apply high-contrast grayscale Nothing filter
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val outCanvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            val cm = ColorMatrix().apply {
                setSaturation(0f)
                // Boost contrast for Nothing stark monochrome look
                val scale = 1.35f
                val translate = (-255f * 0.15f)
                postConcat(ColorMatrix(floatArrayOf(
                    scale, 0f, 0f, 0f, translate,
                    0f, scale, 0f, 0f, translate,
                    0f, 0f, scale, 0f, translate,
                    0f, 0f, 0f, 1f, 0f
                )))
            }
            colorFilter = ColorMatrixColorFilter(cm)
        }
        outCanvas.drawBitmap(bitmap, 0f, 0f, paint)
        bitmap.recycle()
        return output
    }
}
