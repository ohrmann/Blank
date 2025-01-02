package one.devs.blank.locker

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.DialogProperties

@Composable
fun WelcomeScreen(onOkClicked: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { /* Блокируем закрытие диалога */ },
            title = { Text(text = "Добро пожаловать в blank") },
            text = {
                Text("Придумайте свой графический ключ, который будет служить секретным паролем для доступа к вашим спискам.")
            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    onOkClicked()
                }) {
                    Text("OK")
                }
            },
            dismissButton = null,
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }
}