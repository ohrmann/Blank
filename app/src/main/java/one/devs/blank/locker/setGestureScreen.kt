// SetGestureScreen.kt
package one.devs.blank

import android.gesture.GestureOverlayView
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import one.devs.blank.locker.saveEncryptedGesture

@Composable
fun SetGestureScreen(
    onGestureSaved: () -> Unit,
    showDialog: Boolean,
    onDialogDismiss: () -> Unit
) {
    val context = LocalContext.current
    var gestureCount by remember { mutableStateOf(0) }
    val maxGestures = 3 // how many times user has to make secret painting

    // First dialog for user
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { /* do nothing so user can't close dialog without click by button */ },
            title = { Text("Welcome to Blank") },
            text = {
                Text(
                    "Please set a pattern lock to use the protected features. " +
                            "If you forget the pattern lock, delete the app, or clear the app data, " +
                            "you will lose access to the data stored within. Please be careful!"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDialogDismiss()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    AndroidView(
        factory = { ctx ->
            GestureOverlayView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                setBackgroundColor(android.graphics.Color.BLACK)
                gestureColor = android.graphics.Color.WHITE
                uncertainGestureColor = android.graphics.Color.LTGRAY

                setGestureVisible(true)
                gestureStrokeType = GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE
                isEventsInterceptionEnabled = true
                isFadeEnabled = false

                addOnGesturePerformedListener { overlay, gesture ->
                    Log.d("SetGestureScreen", "Gesture performed")
                    saveEncryptedGesture(context, gesture)
                    gestureCount++
                    if (gestureCount >= maxGestures) {
                        Toast.makeText(context, "Gesture saved", Toast.LENGTH_SHORT).show()
                        onGestureSaved()
                    } else {
                        Toast.makeText(
                            context,
                            "Repeat the gesture ${maxGestures - gestureCount} more time(s)",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}