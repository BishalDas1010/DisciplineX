package com.example.disciplinex.SCREENS

// AddAppsScreen.kt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.disciplinex.DATA_CLass.AppInfo
import com.example.disciplinex.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAppsScreen(
    allApps: List<AppInfo>,          // all installed apps
    initiallyBlocked: Set<String> = emptySet(), // already blocked (disable selection)
    onAddSelected: (List<String>) -> Unit, // returns selected package names
    onDismiss: () -> Unit
) {
    // State for search query and selected packages
    var searchQuery by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<Set<String>>(emptySet()) }

    // Filter apps based on search query
    val filteredApps = remember(allApps, searchQuery) {
        allApps.filter {
            it.appName.contains(searchQuery, ignoreCase = true) ||
                    it.packageName.contains(searchQuery, ignoreCase = true)
        }
    }

    // Separate "Recommended" – in this example we take the first 5 as recommended
    // but you can define your own logic (e.g., most used apps)
    val recommendedApps = filteredApps.take(5)
    val otherApps = filteredApps.drop(5)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Apps to Block") },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(painter = painterResource(R.drawable.notification), contentDescription = "Close")
                    }
                }
            )
        },
        bottomBar = {
            // Sticky bottom button
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = {
                        onAddSelected(selected.toList())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    enabled = selected.isNotEmpty()
                ) {
                    Text("Add Selected (${selected.size})")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search apps") },
                leadingIcon = { Icon(painter = painterResource(R.drawable.notification), contentDescription = null) },
                singleLine = true,
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Recommended section
            Text(
                text = "Recommended",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // App list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Show recommended apps
                items(recommendedApps) { app ->
                    AppListItem(
                        app = app,
                        isBlocked = initiallyBlocked.contains(app.packageName),
                        isSelected = selected.contains(app.packageName),
                        onToggle = { checked ->
                            if (checked) {
                                selected = selected + app.packageName
                            } else {
                                selected = selected - app.packageName
                            }
                        }
                    )
                }

                // If there are other apps, show them under "Other" or just continue
                if (otherApps.isNotEmpty()) {
                    item {
                        Text(
                            text = "All Apps",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                    }
                    items(otherApps) { app ->
                        AppListItem(
                            app = app,
                            isBlocked = initiallyBlocked.contains(app.packageName),
                            isSelected = selected.contains(app.packageName),
                            onToggle = { checked ->
                                if (checked) {
                                    selected = selected + app.packageName
                                } else {
                                    selected = selected - app.packageName
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppListItem(
    app: AppInfo,
    isBlocked: Boolean,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App icon (placeholder if not available)
        if (app.icon != null) {
            Icon(
                painter = app.icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        } else {
            // Fallback icon
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_edit),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // App name
        Text(
            text = app.appName,
            modifier = Modifier.weight(1f),
            color = if (isBlocked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
        )

        // Checkbox
        Checkbox(
            checked = isSelected,
            onCheckedChange = { checked ->
                if (!isBlocked) {
                    onToggle(checked)
                }
            },
            enabled = !isBlocked
        )
    }
}