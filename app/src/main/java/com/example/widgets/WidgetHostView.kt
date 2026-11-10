package com.example.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.data.CountdownEventEntity
import com.example.data.LauncherItemEntity
import com.example.data.QuickNoteEntity
import com.example.data.TodoItemEntity

@Composable
fun NothingWidgetHost(
    item: LauncherItemEntity,
    tempUnit: String,
    is24H: Boolean,
    note: QuickNoteEntity?,
    todos: List<TodoItemEntity>,
    countdown: CountdownEventEntity?,
    onOpenNoteEdit: () -> Unit,
    onToggleTodo: (TodoItemEntity) -> Unit,
    onAddTodo: () -> Unit,
    onOpenCountdownEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (item.widgetType) {
        "DIGITAL_CLOCK" -> NothingDigitalClockWidget(is24H = is24H)
        "ANALOG_CLOCK" -> NothingAnalogClockWidget()
        "WEATHER" -> NothingWeatherWidget(tempUnit = tempUnit)
        "BATTERY" -> NothingBatteryRingWidget()
        "HARDWARE_MONITOR", "RAM_STORAGE" -> NothingRamStorageWidget()
        "NOTES" -> NothingQuickNotesWidget(note = note, onOpenEdit = onOpenNoteEdit)
        "TODOS" -> NothingTodoListWidget(todos = todos, onToggleTodo = onToggleTodo, onAddNewTodo = onAddTodo)
        "COUNTDOWN" -> NothingCountdownWidget(countdown = countdown, onClick = onOpenCountdownEdit)
        "STOPWATCH" -> NothingTimerStopwatchWidget()
        "QUICK_TOGGLES" -> NothingQuickTogglesWidget()
        "GLYPH" -> NothingGlyphSimulationWidget()
        else -> NothingDigitalClockWidget(is24H = is24H)
    }
}
