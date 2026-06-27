import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.disciplinex.R // replace with your actual R

// Data class for an app (matches your BlockedApp if you add id)
data class AppItem(
    val id: Int,
    val name: String,
    val iconRes: Int, // drawable resource ID
    var isSelected: Boolean = false // we'll use mutable state
)
@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppsToBlockScreen(

) {
    // State for search query
    var searchQuery by remember { mutableStateOf("") }

    // Mock list of apps – in real app, get from repository
    val allApps = remember {
        listOf(
            AppItem(1, "Instagram", R.drawable.lock), // replace with your icons
            AppItem(2, "YouTube", R.drawable.lock),
            AppItem(3, "Facebook", R.drawable.lock),
            AppItem(4, "WhatsApp", R.drawable.lock),
            AppItem(5, "Chrome", R.drawable.lock),
            AppItem(6, "Twitter", R.drawable.lock),
            AppItem(7, "Snapchat", R.drawable.lock)
        )
    }

    // State for selected status – we'll maintain a separate set of IDs
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }

    // Filter apps based on search query
    val filteredApps = remember(allApps, searchQuery) {
        if (searchQuery.isBlank()) allApps
        else allApps.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    // Count selected
    val selectedCount = selectedIds.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Apps to Block") },
                navigationIcon = {
                    // optional back button
                    IconButton(onClick = { /* handle back */ }) {
                        Icon(painter = painterResource(R.drawable.lock), contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search apps") },
//                leadingIcon = { painterResource(R.drawable.lock), contentDescription = "Search") },
                singleLine = true
            )

            // Recommended header
            Text(
                text = "Recommended",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // List of apps
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(filteredApps, key = { it.id }) { app ->
                    AppListItem(
                        app = app,
                        isSelected = app.id in selectedIds,
                        onToggle = { selected ->
                            selectedIds = if (selected) {
                                selectedIds + app.id
                            } else {
                                selectedIds - app.id
                            }
                        }
                    )
                    Divider()
                }
            }

            // Bottom "Add Selected" button
            Button(
                onClick = {
                    // Handle adding selected apps
                    // You can call repository.toggleBlockedApp for each selected id
                    // or pass the selected list to a callback
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = selectedCount > 0
            ) {
                Text("Add Selected ($selectedCount)")
            }
        }
    }
}

@Composable
fun AppListItem(
    app: AppItem,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle(!isSelected) }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App icon – using placeholder; you can load from drawable
        Icon(
            painter = painterResource(id = app.iconRes),
            contentDescription = app.name,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = app.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = isSelected,
            onCheckedChange = onToggle
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddAppsToBlockScreen() {
    MaterialTheme {
        AddAppsToBlockScreen()
    }
}