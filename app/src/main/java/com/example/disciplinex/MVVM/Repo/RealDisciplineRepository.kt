// RealDisciplineRepository.kt
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import com.example.disciplinex.MVVM.Repo.DATAclass.AppItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class RealDisciplineRepository(private val context: Context) {

    // In‑memory store of blocked package names (you can replace with real DB later)
    private val _blockedPackages = MutableStateFlow<Set<String>>(emptySet())
    val blockedPackages = _blockedPackages.asStateFlow()

    // Cache for id ↔ packageName
    private val idToPackage = mutableMapOf<Int, String>()

    suspend fun getInstalledApps(): List<AppItem> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val packages = pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { pm.getLaunchIntentForPackage(it.packageName) != null } // only launchable apps

        packages.mapNotNull { appInfo ->
            try {
                val name = pm.getApplicationLabel(appInfo).toString()
                val drawable = appInfo.loadIcon(pm)
                val painter = drawableToPainter(drawable)
                val id = appInfo.packageName.hashCode()
                idToPackage[id] = appInfo.packageName
                AppItem(
                    id = id,
                    name = name,
                    iconPainter = painter,
                    isBlocked = _blockedPackages.value.contains(appInfo.packageName)
                )
            } catch (e: Exception) {
                null // skip apps with icon issues
            }
        }
    }

    private fun drawableToPainter(drawable: android.graphics.drawable.Drawable): Painter {
        return if (drawable is BitmapDrawable) {
            BitmapPainter(drawable.bitmap.asImageBitmap())
        } else {
            // fallback: convert any drawable to bitmap
            val bitmap = android.graphics.Bitmap.createBitmap(
                drawable.intrinsicWidth.takeIf { it > 0 } ?: 100,
                drawable.intrinsicHeight.takeIf { it > 0 } ?: 100,
                android.graphics.Bitmap.Config.ARGB_8888
            )
            val canvas = android.graphics.Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            BitmapPainter(bitmap.asImageBitmap())
        }
    }

    // Toggle block status using the app id (provided by the UI)
    suspend fun toggleBlockedApp(appId: Int, blocked: Boolean) {
        val packageName = idToPackage[appId] ?: return
        _blockedPackages.update { current ->
            if (blocked) current + packageName else current - packageName
        }
        // TODO: implement real blocking (e.g., using DevicePolicyManager)
    }
}