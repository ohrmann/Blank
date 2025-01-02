// MainScreen.kt
package one.devs.blank

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(onCreateNewNote: () -> Unit) {
    val context = LocalContext.current

    // fake data for testing
    var notes by remember { mutableStateOf(sampleNotes) }

    // state for search
    var searchQuery by remember { mutableStateOf("") }

    // State for menu
    var showFilterMenu by remember { mutableStateOf(false) }

    // filter
    val filteredNotes = notes.filter { it.title.contains(searchQuery, ignoreCase = true) }

    Scaffold(
        topBar = {
            // search field
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                // Filter Button
                FloatingActionButton(
                    onClick = { showFilterMenu = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter")
                }

                // New button
                FloatingActionButton(
                    onClick = { onCreateNewNote() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "New Note")
                }
            }

            // Filtering menu
            DropdownMenu(
                expanded = showFilterMenu,
                onDismissRequest = { showFilterMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("By creation date") },
                    onClick = {
                        showFilterMenu = false
                        // open calendar for dates picking
                        // show toast temporary
                        Toast.makeText(context, "Filter by creation date", Toast.LENGTH_SHORT).show()
                    }
                )
                DropdownMenuItem(
                    text = { Text("By edit date") },
                    onClick = {
                        showFilterMenu = false
                        // open calendar for date picking
                        // show toast
                        Toast.makeText(context, "Filter by edit date", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        },
        content = { padding ->
            // List of the notes
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredNotes) { note ->
                    NoteItem(note = note)
                    Divider()
                }
            }
        }
    )
}

@Composable
fun NoteItem(note: Note) {
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = note.title, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Created: ${note.creationDate.format(formatter)}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Modified: ${note.modificationDate.format(formatter)}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// temporary list for testing
val sampleNotes = listOf(
    Note(
        id = 1,
        title = "First Note",
        content = "Content of the first note",
        creationDate = LocalDateTime.now().minusDays(2),
        modificationDate = LocalDateTime.now().minusDays(1)
    ),
    Note(
        id = 2,
        title = "Second Note",
        content = "Content of the second note",
        creationDate = LocalDateTime.now().minusDays(5),
        modificationDate = LocalDateTime.now().minusDays(3)
    ),

)