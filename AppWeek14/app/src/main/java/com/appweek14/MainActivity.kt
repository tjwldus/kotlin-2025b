package com.appweek14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.appweek14.data.RetrofitClient
import com.appweek14.data.User
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloRetrofitApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloRetrofitApp() {
    val scope = rememberCoroutineScope()
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello Retrofit") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isLoading = true
                        errorMessage = ""
                        try {
                            val result = RetrofitClient.api.getUsers()
                            //10명의 데이터가 들어가 있음.
                            users = result
                        } catch (e: Exception) {
                            errorMessage = "에러: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                }
            ) {
                Text("Fetch Users")
            }

            //빈 공간 여백 채울 때 사용
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                //로딩 중
                CircularProgressIndicator()
            } else if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn {
                    //items(데이터들) : for문 역할을 할 수 있는 것. (item의 개수가 10개면 10번 도는 것)
                    items(users) { user ->
                        UserItem(user)
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "${user.id}. ${user.name}")
        Text(text = user.email, style = MaterialTheme.typography.bodySmall)
    }
}