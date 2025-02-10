package com.example.trab1_todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.trab1_todolist.navigation.TaskNavHost
import com.example.trab1_todolist.ui.theme.Trab1_ToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                modifier = Modifier
                    .safeDrawingPadding()
            ) {
                Trab1_ToDoListTheme {
                    TaskNavHost()
                }
            }
        }
    }
}