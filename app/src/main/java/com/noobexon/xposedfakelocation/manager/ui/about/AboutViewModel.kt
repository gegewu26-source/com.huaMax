package com.noobexon.xposedfakelocation.manager.ui.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

data class Contributor(
    val name: String,
    val githubUrl: String,
    val avatarUrl: String,
    val contributions: Int
)

sealed interface ContributorsUiState {
    data object Loading : ContributorsUiState
    data class Success(val contributors: List<Contributor>) : ContributorsUiState
    data class Error(val message: String? = null) : ContributorsUiState
}

class AboutViewModel : ViewModel() {

    private val gson = Gson()

    private val _contributorsState = MutableStateFlow<ContributorsUiState>(ContributorsUiState.Loading)
    val contributorsState: StateFlow<ContributorsUiState> = _contributorsState.asStateFlow()

    init {
        loadContributors()
    }

    fun loadContributors() {
        _contributorsState.value = ContributorsUiState.Loading
        viewModelScope.launch {
            _contributorsState.value = runCatching { fetchContributors() }
                .fold(
                    onSuccess = { ContributorsUiState.Success(it) },
                    onFailure = { ContributorsUiState.Error(it.message) }
                )
        }
    }

    private suspend fun fetchContributors(): List<Contributor> = withContext(Dispatchers.IO) {
        val connection = (URL(CONTRIBUTORS_URL).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = TIMEOUT_MILLIS
            readTimeout = TIMEOUT_MILLIS
            // GitHub rejects requests without a User-Agent header.
            setRequestProperty("User-Agent", "XposedFakeLocation-App")
            setRequestProperty("Accept", "application/vnd.github+json")
        }

        try {
            val responseCode = connection.responseCode
            if (responseCode !in 200..299) {
                throw IllegalStateException("GitHub API responded with HTTP $responseCode")
            }

            val body = connection.inputStream.bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<GithubContributor>>() {}.type
            gson.fromJson<List<GithubContributor>>(body, type)
                .orEmpty()
                .filter { it.type != "Bot" && !it.login.isNullOrBlank() }
                .sortedByDescending { it.contributions }
                .map {
                    Contributor(
                        name = it.login.orEmpty(),
                        githubUrl = it.htmlUrl ?: "https://github.com/${it.login}",
                        avatarUrl = it.avatarUrl ?: "https://github.com/${it.login}.png",
                        contributions = it.contributions
                    )
                }
        } finally {
            connection.disconnect()
        }
    }

    private data class GithubContributor(
        @SerializedName("login") val login: String?,
        @SerializedName("avatar_url") val avatarUrl: String?,
        @SerializedName("html_url") val htmlUrl: String?,
        @SerializedName("type") val type: String?,
        @SerializedName("contributions") val contributions: Int = 0
    )

    private companion object {
        const val CONTRIBUTORS_URL =
            "https://api.github.com/repos/noobexon1/XposedFakeLocation/contributors?per_page=100"
        const val TIMEOUT_MILLIS = 10_000
    }
}
