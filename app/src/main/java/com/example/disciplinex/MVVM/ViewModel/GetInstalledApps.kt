package com.example.disciplinex.MVVM.ViewModel

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.example.disciplinex.DATA_CLass.AppInfo

// Converts a Drawable to a Painter (works for any drawable)
fun drawableToPainter(drawable: Drawable): Painter? {
    return try {
        val bitmap = when (drawable) {
            is BitmapDrawable -> drawable.bitmap
            else -> {
                // Fallback: draw the drawable onto a canvas
                val width = drawable.intrinsicWidth.takeIf { it > 0 } ?: 1
                val height = drawable.intrinsicHeight.takeIf { it > 0 } ?: 1
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                    val canvas = Canvas(this)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                }
            }
        }
        BitmapPainter(bitmap.asImageBitmap())
    } catch (e: Exception) {
        null
    }
}

@Suppress("DEPRECATION")
fun getInstalledApps(context: Context): List<AppInfo> {
    val pm = context.packageManager
    val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
    return packages.mapNotNull { pkg ->
        // Skip system apps if you want (optional)
        // if (pkg.flags and ApplicationInfo.FLAG_SYSTEM != 0) return@mapNotNull null
        val appName = pm.getApplicationLabel(pkg).toString()
        val icon = try {
            val drawable = pm.getApplicationIcon(pkg)
            drawableToPainter(drawable)
        } catch (e: Exception) {
            null
        }
        AppInfo(pkg.packageName, appName, icon)
    }.sortedBy { it.appName }
}