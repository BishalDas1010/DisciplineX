// AddAppsToBlockScreen.kt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.disciplinex.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.disciplinex.MVVM.Repo.DATAclass.AppItem
import com.example.disciplinex.MVVM.ViewModel.FocusViewModel_blockedAPP

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppsToBlockScreen(
    viewModel: FocusViewModel_blockedAPP // pass from your navigation/DI
) {
    val allApps by viewModel.apps.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedIds by remember { mutableStateOf(setOf<Int>()) }

    // Filter apps based on search
    val filteredApps = remember(allApps, searchQuery) {
        if (searchQuery.isBlank()) allApps
        else allApps.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Apps to Block") },
                navigationIcon = {
                    IconButton(onClick = { /* navigate back */ }) {
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
                leadingIcon = { Icon(painter = painterResource(R.drawable.lock), contentDescription = "Search") },
                singleLine = true
            )

            // Header
            Text(
                text = "Recommended",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // App list
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

            // Bottom button
            Button(
                onClick = {
                    viewModel.blockSelectedApps(selectedIds)
                    selectedIds = emptySet() // clear selection
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = selectedIds.isNotEmpty()
            ) {
                Text("Add Selected (${selectedIds.size})")
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
        // Show the real app icon
        Icon(
            painter = app.iconPainter,
            contentDescription = app.name,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = app.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        // Show checkbox only if not already blocked (optional)
        if (app.isBlocked) {
            Text("Blocked", style = MaterialTheme.typography.bodySmall)
        } else {
            Checkbox(
                checked = isSelected,
                onCheckedChange = onToggle
            )
        }
    }
}