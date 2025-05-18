package com.mobile.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobile.todo.data.local.TodoDatabase
import com.mobile.todo.data.remote.TodoApi
import com.mobile.todo.data.repository.TodoRepository
import com.mobile.todo.ui.screens.TodoDetailScreen
import com.mobile.todo.ui.screens.TodoListScreen
import com.mobile.todo.ui.theme.TodoTheme
import com.mobile.todo.viewmodel.TodoViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = TodoDatabase.getDatabase(applicationContext)

        val api = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // You can replace this with your own API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)

        val repository = TodoRepository(api, db.todoDao())
        val viewModel = TodoViewModel(repository)

        setContent {
            TodoTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "list") {
                    composable("list") {
                        TodoListScreen(viewModel, navController)
                    }
                    composable("detail/{id}") { backStackEntry ->
                        val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                        id?.let {
                            TodoDetailScreen(it, viewModel, navController)
                        }
                    }
                }
            }
        }
    }
}
