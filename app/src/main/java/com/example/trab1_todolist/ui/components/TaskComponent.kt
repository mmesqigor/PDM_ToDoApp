package com.example.trab1_todolist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.trab1_todolist.R
import com.example.trab1_todolist.domain.Task
import com.example.trab1_todolist.ui.theme.LightBlue
import com.example.trab1_todolist.ui.theme.LightGreen
import com.example.trab1_todolist.ui.theme.LightPurple


@Composable
fun TaskComponent(
    task: Task,
    onCompletedChange: (Boolean) -> Unit,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val taskColor = listOf(LightPurple, LightBlue, LightGreen).random()
    Surface(
        onClick = onItemClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${task.startTime}",
                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                textAlign = TextAlign.Center
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(if (task.isCompleted) Color.Black else Color.Transparent)
                        .border(3.dp, Color.Black, CircleShape)
                        .clickable {
                            onCompletedChange(!task.isCompleted)
                        }
                )

                Divider(
                    modifier = Modifier.width(6.dp),
                    color = Color.Black
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(taskColor)
                            .weight(0.9f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = task.title,
                            fontFamily = FontFamily(Font(R.font.nunito_bold)),
                            modifier = Modifier
                                .padding(
                                    top = 12.dp,
                                    start = 12.dp
                                )
                        )

                        if(task.description != null){
                            Text(
                                text = task.description,
                                fontFamily = FontFamily(Font(R.font.nunito_bold)),
                                modifier = Modifier
                                    .padding(start = 12.dp),
                                color = Color.Gray
                            )
                        }

                        Text(
                            text = "${task.startTime} - ${task.endTime}",
                            fontFamily = FontFamily(Font(R.font.nunito_bold)),
                            modifier = Modifier
                                .padding(
                                    start = 12.dp,
                                    bottom = 12.dp
                                )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = onDeleteClick) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .width(6.dp)
                            .weight(0.1f),
                        color = Color.Black
                    )
                }
            }
        }
    }
}