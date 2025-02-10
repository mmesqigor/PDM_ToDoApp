package com.example.trab1_todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import com.example.trab1_todolist.ui.feature.list.ListScreen
import com.example.trab1_todolist.ui.feature.addedit.AddEditScreen

@Serializable
object ListRoute

@Serializable
data class AddEditRoute(val id: Long? = null)

@Composable
fun TaskNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ListRoute
    ) {
        composable<ListRoute> {
            ListScreen(
                navigateToAddEditScreen = { id ->
                    navController.navigate(AddEditRoute(id = id))
                }
            )
        }
        composable<AddEditRoute> { backStackEntry ->
            val addEditRoute = backStackEntry.toRoute<AddEditRoute>()
            AddEditScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}