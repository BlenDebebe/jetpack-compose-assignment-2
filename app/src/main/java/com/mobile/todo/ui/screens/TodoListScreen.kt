package com.mobile.todo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mobile.todo.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(viewModel: TodoViewModel, navController: NavController) {
    val todos by viewModel.todos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadTodos()
    }

    Scaffold(
        containerColor = Color(0xFFFDD6FF), // Light purple background
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TODO List",
                        color = Color.DarkGray // Top bar title in black
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF9A5FD) // White top bar
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todos) { todo ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White // White card background
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.selectTodo(todo.id)
                            navController.navigate("detail/${todo.id}")
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = todo.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.DarkGray // Title text in black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (todo.completed) "Completed" else "Pending",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black // Status text in black
                        )
                    }
                }
            }
        }
    }
}
