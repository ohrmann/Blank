package one.devs.blank.locker

import android.gesture.GestureLibraries
import android.gesture.GestureOverlayView
import android.gesture.Prediction
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File

@Composable
fun UnlockScreen(onUnlocked: () -> Unit) {
    val context = LocalContext.current
    val activity = context as AppCompatActivity

    val gesture = loadEncryptedGesture(context)
    if (gesture == null) {
        Log.e("UnlockScreen", "Gesture not found or failed to decrypt")
        Toast.makeText(context, "Couldn't find key, sorry", Toast.LENGTH_LONG).show()
        return
    }

    val tempGestureFile = File(context.cacheDir, "tempGesture")
    val gestureLibrary = GestureLibraries.fromFile(tempGestureFile)
    gestureLibrary.addGesture("userGesture", gesture)
    gestureLibrary.save()
    gestureLibrary.load()

    //var attemptCount by remember { mutableStateOf(0) }
    val maxAttempts = 5

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

                addOnGesturePerformedListener { overlay, inputGesture ->
                    Log.d("UnlockScreen", "Gesture performed")
                    val predictions: List<Prediction> = gestureLibrary.recognize(inputGesture)
                    if (predictions.isNotEmpty()) {
                        val prediction = predictions[0]
                        Log.d("UnlockScreen", "Prediction score: ${prediction.score}")
                        if (prediction.score > 1.5) {
                            Log.d("UnlockScreen", "Gesture recognized")
                            // После успешного распознавания жеста запрашиваем биометрию
                            if (isBiometricAvailable(context)) {
                                showBiometricPrompt(
                                    activity = activity,
                                    onAuthenticated = {
                                        Toast.makeText(context, "Authenticated successfully", Toast.LENGTH_SHORT).show()
                                        onUnlocked()
                                    },
                                    onError = { errorMessage: String ->
                                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            } else {
                                // Если биометрия недоступна, просто переходим дальше
                                onUnlocked()
                            }
                        } else {
//                            Toast.makeText(
//                                context,
//                                "Wrong key, please try again.",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        }
                        Log.d("UnlockScreen", "No predictions")
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

// Функция для отображения биометрического диалога
fun showBiometricPrompt(
    activity: AppCompatActivity,
    onAuthenticated: () -> Unit,
    onError: (String) -> Unit
) {
    val executor = ContextCompat.getMainExecutor(activity)
    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError(errString.toString())
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAuthenticated()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onError("Please try again.")
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Authentication required")
        //.setSubtitle("Подтвердите свою личность")
        .setNegativeButtonText("Cancel")
        .build()

    biometricPrompt.authenticate(promptInfo)
}