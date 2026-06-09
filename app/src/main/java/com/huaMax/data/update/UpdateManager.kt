package com.huaMax.data.update

import com.huaMax.BuildConfig
import com.huaMax.data.GITHUB_LATEST_APK_URL
import com.huaMax.data.GITHUB_LATEST_RELEASE_URL
import com.huaMax.data.GITHUB_REPOSITORY_URL
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object UpdateManager {
    private const val CONNECT_TIMEOUT_MS = 8_000
    private const val READ_TIMEOUT_MS = 8_000

    sealed interface UpdateState {
        data class UpToDate(
            val currentVersionName: String,
            val latestVersionName: String,
            val releaseUrl: String
        ) : UpdateState

        data class UpdateAvailable(
            val currentVersionName: String,
            val latestVersionName: String,
            val releaseUrl: String
        ) : UpdateState

        data class Error(
            val currentVersionName: String,
            val releaseUrl: String,
            val message: String
        ) : UpdateState
    }

    private data class LatestRelease(
        val versionCode: Int,
        val versionName: String,
        val releaseUrl: String
    )

    suspend fun checkForUpdate(): UpdateState = withContext(Dispatchers.IO) {
        try {
            val latest = fetchLatestRelease()
            if (latest.versionCode > BuildConfig.VERSION_CODE) {
                UpdateState.UpdateAvailable(
                    currentVersionName = BuildConfig.VERSION_NAME,
                    latestVersionName = latest.versionName,
                    releaseUrl = latest.releaseUrl
                )
            } else {
                UpdateState.UpToDate(
                    currentVersionName = BuildConfig.VERSION_NAME,
                    latestVersionName = latest.versionName,
                    releaseUrl = latest.releaseUrl
                )
            }
        } catch (e: IOException) {
            UpdateState.Error(
                currentVersionName = BuildConfig.VERSION_NAME,
                releaseUrl = GITHUB_LATEST_RELEASE_URL,
                message = e.message.orEmpty()
            )
        } catch (e: RuntimeException) {
            UpdateState.Error(
                currentVersionName = BuildConfig.VERSION_NAME,
                releaseUrl = GITHUB_LATEST_RELEASE_URL,
                message = e.message.orEmpty()
            )
        }
    }

    private fun fetchLatestRelease(): LatestRelease {
        val connection = (URI.create(GITHUB_LATEST_APK_URL).toURL().openConnection() as HttpURLConnection).apply {
            requestMethod = "HEAD"
            connectTimeout = CONNECT_TIMEOUT_MS
            readTimeout = READ_TIMEOUT_MS
            useCaches = false
            instanceFollowRedirects = false
            setRequestProperty("User-Agent", "LocationMax/${BuildConfig.VERSION_NAME}")
        }

        return try {
            val responseCode = connection.responseCode
            if (responseCode !in 300..399) {
                throw IOException("GitHub redirect HTTP $responseCode")
            }

            val location = connection.getHeaderField("Location").orEmpty()
            parseLatestReleaseLocation(location)
        } finally {
            connection.disconnect()
        }
    }

    private fun parseLatestReleaseLocation(location: String): LatestRelease {
        if (location.isBlank()) {
            throw IllegalArgumentException("Missing latest release redirect")
        }

        val path = URI.create(location).path
        val tag = path
            .substringAfter("/releases/download/", "")
            .substringBefore("/")
        val releaseUrl = if (tag.isBlank()) {
            GITHUB_LATEST_RELEASE_URL
        } else {
            "$GITHUB_REPOSITORY_URL/releases/tag/$tag"
        }
        val versionName = versionNameFromTag(tag)
        val versionCode = versionCodeFromTag(tag, versionName)

        if (versionName.isBlank() || versionCode <= 0) {
            throw IllegalArgumentException("Invalid release tag: $tag")
        }

        return LatestRelease(
            versionCode = versionCode,
            versionName = versionName,
            releaseUrl = releaseUrl
        )
    }

    private fun versionNameFromTag(tag: String): String {
        val cleanTag = tag.removePrefix("v")
        return if (cleanTag.contains("-")) {
            cleanTag.substringAfter("-")
        } else {
            cleanTag
        }
    }

    private fun versionCodeFromTag(tag: String, versionName: String): Int {
        val cleanTag = tag.removePrefix("v")
        val explicitCode = cleanTag.substringBefore("-").toIntOrNull()
        if (explicitCode != null && cleanTag.contains("-")) return explicitCode

        val core = versionName.substringBefore("-")
        val parts = core.split(".")
        val major = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val minor = parts.getOrNull(1)?.toIntOrNull() ?: 0
        val patch = parts.getOrNull(2)?.toIntOrNull() ?: 0
        return major * 10000 + minor * 100 + patch
    }
}
