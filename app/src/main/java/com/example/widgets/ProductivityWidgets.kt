package com.example.widgets

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CountdownEventEntity
import com.example.data.QuickNoteEntity
import com.example.data.TodoItemEntity
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteFaint
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.NothingWhiteSecondary
import com.example.ui.theme.SilkscreenDotMatrix
import com.example.ui.theme.SpaceGroteskSans
import com.example.ui.theme.SpaceMonoTech
import java.util.concurrent.TimeUnit

@Composable
fun NothingQuickNotesWidget(
    note: QuickNoteEntity?,
    onOpenEdit: () -> Unit = {}
) {
    val contentText = note?.content?.ifBlank { "TAP TO WRITE NOTHING NOTE..." } ?: "TAP TO WRITE NOTHING NOTE..."

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .clickable(onClick = onOpenEdit)
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "QUICK NOTE",
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

            Text(
                text = contentText,
                fontFamily = SpaceGroteskSans,
                fontSize = 13.sp,
                color = if (note?.content.isNullOrBlank()) NothingWhiteMuted else NothingWhite,
                lineHeight = 17.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "EDIT [DOT-PAD]",
                fontFamily = SpaceMonoTech,
                fontSize = 9.sp,
                color = NothingWhiteMuted
            )
        }
    }
}

@Composable
fun NothingTodoListWidget(
    todos: List<TodoItemEntity>,
    onToggleTodo: (TodoItemEntity) -> Unit,
    onAddNewTodo: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .padding(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TASKS",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingWhiteMuted,
                    letterSpacing = 1.sp
                )
                // Add button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(NothingWhite)
                        .clickable(onClick = onAddNewTodo)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "+ ADD",
                        fontFamily = SpaceMonoTech,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = NothingBlack
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (todos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ALL TASKS COMPLETED",
                        fontFamily = SpaceMonoTech,
                        fontSize = 11.sp,
                        color = NothingWhiteMuted
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(todos.take(3), key = { it.id }) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onToggleTodo(item) }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Custom monochrome checkbox
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .border(
                                        1.dp,
                                        if (item.isCompleted) NothingRed else NothingWhite,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .background(if (item.isCompleted) NothingRed else NothingBlack),
                                contentAlignment = Alignment.Center
                            ) {
                                if (item.isCompleted) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .background(NothingWhite, CircleShape)
                                    )
                                }
                            }
                            Text(
                                text = item.text,
                                fontFamily = SpaceGroteskSans,
                                fontSize = 12.sp,
                                color = if (item.isCompleted) NothingWhiteMuted else NothingWhite,
                                textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NothingCountdownWidget(
    countdown: CountdownEventEntity?,
    onClick: () -> Unit = {}
) {
    val targetTime = countdown?.targetTimestamp ?: (System.currentTimeMillis() + 86400000L * 7)
    val diffMillis = (targetTime - System.currentTimeMillis()).coerceAtLeast(0L)
    val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
    val title = countdown?.title?.uppercase() ?: "EVENT COUNTDOWN"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(NothingSurface)
            .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "COUNTDOWN",
                fontFamily = SpaceMonoTech,
                fontSize = 10.sp,
                color = NothingWhiteMuted,
                letterSpacing = 1.sp
            )

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "$days",
                    fontFamily = SilkscreenDotMatrix,
                    fontSize = 36.sp,
                    color = NothingWhite
                )
                Text(
                    text = "DAYS LEFT",
                    fontFamily = SpaceMonoTech,
                    fontSize = 10.sp,
                    color = NothingRed,
                    modifier = Modifier.padding(start = 6.dp, bottom = 6.dp)
                )
            }

            Text(
                text = title,
                fontFamily = SpaceGroteskSans,
                fontSize = 11.sp,
                color = NothingWhiteSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
