package com.noobexon.xposedfakelocation.manager.ui.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.noobexon.xposedfakelocation.BuildConfig
import com.noobexon.xposedfakelocation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = { AboutTopAppBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            AboutContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopAppBar(navController: NavController) {
    TopAppBar(
        title = { Text(stringResource(R.string.screen_about)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.cd_back))
            }
        }
    )
}

@Composable
fun AboutContent() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTitle()
        Spacer(modifier = Modifier.height(16.dp))
        AppDescription()
        Spacer(modifier = Modifier.height(32.dp))
        AppVersionSection()
        Spacer(modifier = Modifier.height(16.dp))
        AppDeveloperSection()
        Spacer(modifier = Modifier.height(16.dp))
        AppContributorsSection()
    }
}

@Composable
fun AppTitle() {
    Text(
        text = stringResource(R.string.app_name),
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center
    )
}

@Composable
fun AppDescription() {
    Text(
        text = stringResource(R.string.about_description),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
    )
}

@Composable
fun AppVersionSection() {
    AppVersionTitle()
    Spacer(modifier = Modifier.height(16.dp))
    AppVersionValue()
}

@Composable
fun AppVersionTitle() {
    Text(
        text = stringResource(R.string.about_version_label),
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 24.dp)
    )
}

@Composable
fun AppVersionValue() {
    Text(
        text = BuildConfig.VERSION_NAME,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 4.dp)
    )
}

@Composable
fun AppDeveloperSection() {
    AppDeveloperTitle()
    Spacer(modifier = Modifier.height(16.dp))
    AppDeveloperValue()
}

@Composable
fun AppDeveloperTitle() {
    Text(
        text = stringResource(R.string.about_developer_label),
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun AppDeveloperValue() {
    val context = LocalContext.current
    Text(
        text = "noobexon",
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(top = 4.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/noobexon1"))
                context.startActivity(intent)
            }
    )
}

@Composable
fun AppContributorsSection(aboutViewModel: AboutViewModel = viewModel()) {
    val state by aboutViewModel.contributorsState.collectAsState()

    Text(
        text = stringResource(R.string.about_contributors_label),
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 8.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))

    when (val current = state) {
        is ContributorsUiState.Loading -> ContributorsLoading()
        is ContributorsUiState.Error -> ContributorsError(
            onRetry = aboutViewModel::loadContributors
        )
        is ContributorsUiState.Success -> {
            if (current.contributors.isEmpty()) {
                Text(
                    text = stringResource(R.string.about_contributors_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            } else {
                current.contributors.forEach { contributor ->
                    ContributorRow(contributor)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun ContributorsLoading() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(R.string.about_contributors_loading),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ContributorsError(onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.about_contributors_error),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        TextButton(onClick = onRetry) {
            Text(text = stringResource(R.string.about_contributors_retry))
        }
    }
}

@Composable
private fun ContributorRow(contributor: Contributor) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(contributor.githubUrl))
                context.startActivity(intent)
            }
    ) {
        AsyncImage(
            model = contributor.avatarUrl,
            contentDescription = contributor.name,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = contributor.name,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}
