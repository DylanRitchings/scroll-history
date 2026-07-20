package uk.dylanrit.scrollhistory.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uk.dylanrit.scrollhistory.data.HistoryDetail
import uk.dylanrit.scrollhistory.data.repository.HistoryRepository

private sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Loaded(val detail: HistoryDetail) : DetailUiState
    data class Error(val message: String) : DetailUiState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String,
    fallbackSummary: String,
    fallbackImageUrl: String?,
    onBack: () -> Unit
) {
    val repository = HistoryRepository()

    val state by produceState<DetailUiState>(initialValue = DetailUiState.Loading, key1 = title) {
        value = try {
            DetailUiState.Loaded(repository.getDetail(title))
        } catch (t: Throwable) {
            DetailUiState.Error(t.message ?: "Couldn't load this article.")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (val s = state) {
            is DetailUiState.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            is DetailUiState.Error -> DetailBody(
                padding = padding,
                imageUrl = fallbackImageUrl,
                title = title,
                text = fallbackSummary,
                sourceUrl = "https://en.wikipedia.org/wiki/${title.replace(' ', '_')}",
                errorNote = s.message
            )

            is DetailUiState.Loaded -> DetailBody(
                padding = padding,
                imageUrl = s.detail.imageUrl ?: fallbackImageUrl,
                title = s.detail.title,
                text = s.detail.fullText,
                sourceUrl = s.detail.sourceUrl,
                errorNote = null
            )
        }
    }
}

@Composable
private fun DetailBody(
    padding: androidx.compose.foundation.layout.PaddingValues,
    imageUrl: String?,
    title: String,
    text: String,
    sourceUrl: String,
    errorNote: String?
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 10f)
        ) {
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Public,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(48.dp)
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall)

            if (errorNote != null) {
                Text(
                    text = errorNote,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp)
            )

            TextButton(
                onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(sourceUrl)))
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(Icons.Filled.OpenInNew, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text("Read full article on Wikipedia")
            }
        }
    }
}
