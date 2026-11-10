package com.example

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.homescreen.NothingHomeScreen
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingLauncherTheme
import com.example.viewmodel.LauncherViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settings by viewModel.settings.collectAsState()
            val launcherItems by viewModel.launcherItems.collectAsState()
            val folders by viewModel.folders.collectAsState()
            val allApps by viewModel.installedApps.collectAsState()
            val quickNote by viewModel.quickNote.collectAsState()
            val todos by viewModel.todos.collectAsState()
            val countdown by viewModel.countdown.collectAsState()

            NothingLauncherTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = NothingBlack
                ) {
                    NothingHomeScreen(
                        settings = settings,
                        launcherItems = launcherItems,
                        folders = folders,
                        allApps = allApps,
                        quickNote = quickNote,
                        todos = todos,
                        countdown = countdown,
                        onLaunchApp = { pkg, act -> launchApp(pkg, act) },
                        onToggleMaxIcon = { itm -> viewModel.toggleMaxIcon(itm) },
                        onToggleMaxFolder = { fld, itm -> viewModel.toggleMaxFolder(fld, itm) },
                        onRemoveItem = { itm -> viewModel.removeItem(itm) },
                        onRenameFolder = { id, name -> viewModel.renameFolder(id, name) },
                        onRemoveAppFromFolder = { itm -> viewModel.removeAppFromFolder(itm) },
                        onAddAppToHome = { appItem ->
                            viewModel.addAppToHomeScreen(appItem)
                            Toast.makeText(this@MainActivity, "Added to Home Screen", Toast.LENGTH_SHORT).show()
                        },
                        onAddWidgetToHome = { type ->
                            viewModel.addWidgetToHome(type)
                            Toast.makeText(this@MainActivity, "Widget added to Home Screen", Toast.LENGTH_SHORT).show()
                        },
                        onUpdateGrid = { cols, rows -> viewModel.updateGrid(cols, rows) },
                        onUpdateIconSize = { sz -> viewModel.updateIconSize(sz) },
                        onUpdateShowLabels = { sh -> viewModel.updateShowLabels(sh) },
                        onUpdateMonochromeIcons = { mono -> viewModel.updateMonochromeIcons(mono) },
                        onUpdateTempUnit = { unit -> viewModel.updateTempUnit(unit) },
                        onUpdate24H = { is24H -> viewModel.updateTime24H(is24H) },
                        onUpdateWallpaper = { idx -> viewModel.updateWallpaper(idx) },
                        onUpdateSwipeDown = { act -> viewModel.updateSwipeDown(act) },
                        onSaveQuickNote = { note -> viewModel.saveQuickNote(note) },
                        onToggleTodo = { todo -> viewModel.toggleTodo(todo) },
                        onAddTodo = { text -> viewModel.addTodo(text) },
                        onResetDefaults = {
                            viewModel.resetToDefaults()
                            Toast.makeText(this@MainActivity, "Home Screen reset to Nothing defaults", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }

    private fun launchApp(packageName: String, activityName: String?) {
        try {
            if (activityName != null) {
                val intent = Intent().apply {
                    component = ComponentName(packageName, activityName)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                }
                startActivity(intent)
            } else {
                val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
                if (launchIntent != null) {
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    startActivity(launchIntent)
                } else {
                    Toast.makeText(this, "Application not available", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to launch: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}
