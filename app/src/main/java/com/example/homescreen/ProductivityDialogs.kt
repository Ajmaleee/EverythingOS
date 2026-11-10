package com.example.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.NothingBlack
import com.example.ui.theme.NothingRed
import com.example.ui.theme.NothingSurface
import com.example.ui.theme.NothingSurfaceBorder
import com.example.ui.theme.NothingWhite
import com.example.ui.theme.NothingWhiteMuted
import com.example.ui.theme.SilkscreenDotMatrix
import com.example.ui.theme.SpaceGroteskSans
import com.example.ui.theme.SpaceMonoTech

@Composable
fun QuickNoteEditDialog(
    initialText: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialText) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(NothingBlack)
                .border(1.5.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(NothingRed)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "EDIT QUICK NOTE",
                        fontFamily = SilkscreenDotMatrix,
                        fontSize = 15.sp,
                        color = NothingWhite
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(NothingSurface)
                        .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(14.dp))
                        .padding(12.dp)
                ) {
                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = SpaceGroteskSans,
                            fontSize = 13.sp,
                            color = NothingWhite
                        ),
                        cursorBrush = SolidColor(NothingRed),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(onClick = onDismiss)
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "CANCEL",
                            fontFamily = SpaceMonoTech,
                            fontSize = 11.sp,
                            color = NothingWhiteMuted
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(NothingWhite)
                            .clickable {
                                onSave(text)
                                onDismiss()
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "SAVE",
                            fontFamily = SpaceMonoTech,
                            fontSize = 11.sp,
                            color = NothingBlack
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddTodoDialog(
    onAdd: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(NothingBlack)
                .border(1.5.dp, NothingSurfaceBorder, RoundedCornerShape(24.dp))
                .padding(20.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "NEW TASK",
                    fontFamily = SilkscreenDotMatrix,
                    fontSize = 15.sp,
                    color = NothingWhite
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(NothingSurface)
                        .border(1.dp, NothingSurfaceBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontFamily = SpaceGroteskSans,
                            fontSize = 13.sp,
                            color = NothingWhite
                        ),
                        cursorBrush = SolidColor(NothingRed),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable(onClick = onDismiss)
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "CANCEL",
                            fontFamily = SpaceMonoTech,
                            fontSize = 11.sp,
                            color = NothingWhiteMuted
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(NothingWhite)
                            .clickable {
                                if (text.isNotBlank()) {
                                    onAdd(text)
                                }
                                onDismiss()
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "ADD TASK",
                            fontFamily = SpaceMonoTech,
                            fontSize = 11.sp,
                            color = NothingBlack
                        )
                    }
                }
            }
        }
    }
}
