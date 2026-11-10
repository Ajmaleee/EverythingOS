package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "nothing_launcher_settings")

data class LauncherSettings(
    val gridColumns: Int = 4,
    val gridRows: Int = 6,
    val iconSizeDp: Int = 56,
    val showLabels: Boolean = true,
    val monochromeIcons: Boolean = true,
    val tempUnit: String = "C",
    val time24H: Boolean = true,
    val dotMatrixHeader: Boolean = true,
    val wallpaperIndex: Int = 0,
    val swipeDownAction: String = "NOTIFICATIONS",
    val isFirstLaunchCompleted: Boolean = false
)

class SettingsRepository(private val context: Context) {

    private object PreferencesKeys {
        val GRID_COLUMNS = intPreferencesKey("grid_columns")
        val GRID_ROWS = intPreferencesKey("grid_rows")
        val ICON_SIZE_DP = intPreferencesKey("icon_size_dp")
        val SHOW_LABELS = booleanPreferencesKey("show_labels")
        val MONOCHROME_ICONS = booleanPreferencesKey("monochrome_icons")
        val TEMP_UNIT = stringPreferencesKey("temp_unit")
        val TIME_24H = booleanPreferencesKey("time_24h")
        val DOT_MATRIX_HEADER = booleanPreferencesKey("dot_matrix_header")
        val WALLPAPER_INDEX = intPreferencesKey("wallpaper_index")
        val SWIPE_DOWN_ACTION = stringPreferencesKey("swipe_down_action")
        val FIRST_LAUNCH_COMPLETED = booleanPreferencesKey("first_launch_completed")
    }

    val settingsFlow: Flow<LauncherSettings> = context.dataStore.data.map { prefs ->
        LauncherSettings(
            gridColumns = prefs[PreferencesKeys.GRID_COLUMNS] ?: 4,
            gridRows = prefs[PreferencesKeys.GRID_ROWS] ?: 6,
            iconSizeDp = prefs[PreferencesKeys.ICON_SIZE_DP] ?: 56,
            showLabels = prefs[PreferencesKeys.SHOW_LABELS] ?: true,
            monochromeIcons = prefs[PreferencesKeys.MONOCHROME_ICONS] ?: true,
            tempUnit = prefs[PreferencesKeys.TEMP_UNIT] ?: "C",
            time24H = prefs[PreferencesKeys.TIME_24H] ?: true,
            dotMatrixHeader = prefs[PreferencesKeys.DOT_MATRIX_HEADER] ?: true,
            wallpaperIndex = prefs[PreferencesKeys.WALLPAPER_INDEX] ?: 0,
            swipeDownAction = prefs[PreferencesKeys.SWIPE_DOWN_ACTION] ?: "NOTIFICATIONS",
            isFirstLaunchCompleted = prefs[PreferencesKeys.FIRST_LAUNCH_COMPLETED] ?: false
        )
    }

    suspend fun updateGrid(columns: Int, rows: Int) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.GRID_COLUMNS] = columns.coerceIn(3, 6)
            prefs[PreferencesKeys.GRID_ROWS] = rows.coerceIn(4, 8)
        }
    }

    suspend fun updateIconSize(sizeDp: Int) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.ICON_SIZE_DP] = sizeDp.coerceIn(44, 72)
        }
    }

    suspend fun updateShowLabels(show: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.SHOW_LABELS] = show
        }
    }

    suspend fun updateMonochromeIcons(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.MONOCHROME_ICONS] = enabled
        }
    }

    suspend fun updateTempUnit(unit: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.TEMP_UNIT] = unit
        }
    }

    suspend fun updateTime24H(is24H: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.TIME_24H] = is24H
        }
    }

    suspend fun updateWallpaper(index: Int) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.WALLPAPER_INDEX] = index
        }
    }

    suspend fun updateSwipeDownAction(action: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.SWIPE_DOWN_ACTION] = action
        }
    }

    suspend fun setFirstLaunchCompleted(completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.FIRST_LAUNCH_COMPLETED] = completed
        }
    }
}
