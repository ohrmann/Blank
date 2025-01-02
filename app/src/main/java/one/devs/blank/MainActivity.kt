package one.devs.blank

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.jakewharton.threetenabp.AndroidThreeTen
import one.devs.blank.locker.UnlockScreen
import one.devs.blank.logic.CreateNoteScreen
import one.devs.blank.ui.theme.BlankTheme

class MainActivity : AppCompatActivity() {

    private val PREFS_NAME = "MyAppPrefs"
    private val IS_FIRST_RUN_KEY = "isFirstRun"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        enableEdgeToEdge()
        setContent {
            BlankTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                var isFirstRun by remember { mutableStateOf(true) }
                var showDialog by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    isFirstRun = sharedPreferences.getBoolean(IS_FIRST_RUN_KEY, true)
                    Log.d("MainActivity", "isFirstRun = $isFirstRun")
                    if (isFirstRun) {
                        showDialog = true
                        Log.d("MainActivity", "Navigating to set_gesture screen")
                        navController.navigate("set_gesture") {
                            popUpTo("loading") { inclusive = true }
                        }
                    } else {
                        Log.d("MainActivity", "Navigating to unlock screen")
                        navController.navigate("unlock") {
                            popUpTo("loading") { inclusive = true }
                        }
                    }
                }

                NavHost(navController = navController, startDestination = "loading") {
                    composable("loading") { /* empty screen */ }
                    composable("set_gesture") {
                        SetGestureScreen(
                            onGestureSaved = {
                                // save flag isFirstRun as false
                                val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                                sharedPreferences.edit().putBoolean(IS_FIRST_RUN_KEY, false).apply()
                                Log.d("MainActivity", "isFirstRun set to false")
                                // Open main screen
                                navController.navigate("main") {
                                    popUpTo("set_gesture") { inclusive = true }
                                }
                            },
                            showDialog = showDialog,
                            onDialogDismiss = {
                                showDialog = false
                            }
                        )
                    }
                    composable("unlock") {
                        UnlockScreen(
                            onUnlocked = {
                                navController.navigate("main") {
                                    popUpTo("unlock") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("main") {
                        MainScreen(
                            onCreateNewNote = {
                                navController.navigate("create_note")
                            }
                        )
                    }
                    composable("create_note") {
                        CreateNoteScreen()
                    }
                }
            }
        }
    }
}